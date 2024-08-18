package com.zimesfield.delivery.service;

import static com.zimesfield.delivery.repository.Util.USERS_BY_EMAIL_CACHE;
import static com.zimesfield.delivery.repository.Util.USERS_BY_LOGIN_CACHE;

import com.zimesfield.delivery.adapter.mapper.AdminUserMapper;
import com.zimesfield.delivery.adapter.rdbms.AuthorityRepository;
import com.zimesfield.delivery.adapter.rdbms.entity.Authority;
import com.zimesfield.delivery.adapter.search.UserSearchRepository;
import com.zimesfield.delivery.adapter.util.Constants;
import com.zimesfield.delivery.domain.model.AdminUserModel;
import com.zimesfield.delivery.domain.model.UserModel;
import com.zimesfield.delivery.repository.UserRepository;
import com.zimesfield.delivery.security.SecurityUtils;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing users.
 */
@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final UserSearchRepository userSearchRepository;

    private final AuthorityRepository authorityRepository;

    private final CacheManager cacheManager;

    private final AdminUserMapper adminUserMapper;

    private static final String UPDATED_AT = "updated_at";

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user.
     * @param lastName  last name of user.
     * @param email     email id of user.
     * @param langKey   language key.
     * @param imageUrl  image URL of user.
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                if (email != null) {
                    user.setEmail(email.toLowerCase());
                }
                user.setLangKey(langKey);
                user.setImageUrl(imageUrl);
                userRepository.save(user);
                userSearchRepository.index(user);
                this.clearUserCaches(user);
                log.debug("Changed Information for UserModel: {}", user);
            });
    }

    @Transactional(readOnly = true)
    public Page<AdminUserModel> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(adminUserMapper::toModel);
    }

    @Transactional(readOnly = true)
    public Page<UserModel> getAllPublicUsers(Pageable pageable) {
        return userRepository.findAllByIdNotNullAndActivatedIsTrue(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<UserModel> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    /**
     * Gets a list of all the authorities.
     * @return a list of all the authorities.
     */
    @Transactional(readOnly = true)
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).toList();
    }

    private UserModel syncUserWithIdP(Map<String, Object> details, UserModel userModel) {
        // save authorities in to sync userModel roles/groups between IdP and JHipster's local database
        Collection<String> dbAuthorities = getAuthorities();
        Collection<String> userAuthorities = userModel.getAuthorities().stream().map(Authority::getName).toList();
        for (String authority : userAuthorities) {
            if (!dbAuthorities.contains(authority)) {
                log.debug("Saving authority '{}' in local database", authority);
                Authority authorityToSave = new Authority();
                authorityToSave.setName(authority);
                authorityRepository.save(authorityToSave);
            }
        }
        // save account in to sync users between IdP and JHipster's local database
        Optional<UserModel> existingUser = userRepository.findOneByLogin(userModel.getLogin());
        if (existingUser.isPresent()) {
            // if IdP sends last updated information, use it to determine if an update should happen
            if (details.get(UPDATED_AT) != null) {
                Instant dbModifiedDate = existingUser.orElseThrow().getLastModifiedDate();
                Instant idpModifiedDate;
                if (details.get(UPDATED_AT) instanceof Instant) {
                    idpModifiedDate = (Instant) details.get(UPDATED_AT);
                } else {
                    idpModifiedDate = Instant.ofEpochSecond((Integer) details.get(UPDATED_AT));
                }
                if (idpModifiedDate.isAfter(dbModifiedDate)) {
                    log.debug("Updating userModel '{}' in local database", userModel.getLogin());
                    updateUser(
                        userModel.getFirstName(),
                        userModel.getLastName(),
                        userModel.getEmail(),
                        userModel.getLangKey(),
                        userModel.getImageUrl()
                    );
                }
                // no last updated info, blindly update
            } else {
                log.debug("Updating userModel '{}' in local database", userModel.getLogin());
                updateUser(
                    userModel.getFirstName(),
                    userModel.getLastName(),
                    userModel.getEmail(),
                    userModel.getLangKey(),
                    userModel.getImageUrl()
                );
            }
        } else {
            log.debug("Saving userModel '{}' in local database", userModel.getLogin());
            userRepository.save(userModel);
            this.clearUserCaches(userModel);
        }
        return userModel;
    }

    /**
     * Returns the user from an OAuth 2.0 login or resource server with JWT.
     * Synchronizes the user in the local rdbms.
     *
     * @param authToken the authentication token.
     * @return the user from the authentication.
     */
    @Transactional
    public AdminUserModel getUserFromAuthentication(AbstractAuthenticationToken authToken) {
        Map<String, Object> attributes;
        if (authToken instanceof OAuth2AuthenticationToken) {
            attributes = ((OAuth2AuthenticationToken) authToken).getPrincipal().getAttributes();
        } else if (authToken instanceof JwtAuthenticationToken) {
            attributes = ((JwtAuthenticationToken) authToken).getTokenAttributes();
        } else {
            throw new IllegalArgumentException("AuthenticationToken is not OAuth2 or JWT!");
        }
        UserModel userModel = getUser(attributes);
        userModel.setAuthorities(
            authToken
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(authority -> {
                    Authority auth = new Authority();
                    auth.setName(authority);
                    return auth;
                })
                .collect(Collectors.toSet())
        );

        return adminUserMapper.toModel(syncUserWithIdP(attributes, userModel));
    }

    private static UserModel getUser(Map<String, Object> details) {
        UserModel userModel = UserModel.builder().build();
        Boolean activated = Boolean.TRUE;
        String sub = String.valueOf(details.get("sub"));
        String username = null;
        if (details.get("preferred_username") != null) {
            username = ((String) details.get("preferred_username")).toLowerCase();
        }
        // handle resource server JWT, where sub claim is email and uid is ID
        if (details.get("uid") != null) {
            userModel.setId((String) details.get("uid"));
            userModel.setLogin(sub);
        } else {
            userModel.setId(sub);
        }
        if (username != null) {
            userModel.setLogin(username);
        } else if (userModel.getLogin() == null) {
            userModel.setLogin(userModel.getId());
        }
        if (details.get("given_name") != null) {
            userModel.setFirstName((String) details.get("given_name"));
        } else if (details.get("name") != null) {
            userModel.setFirstName((String) details.get("name"));
        }
        if (details.get("family_name") != null) {
            userModel.setLastName((String) details.get("family_name"));
        }
        if (details.get("email_verified") != null) {
            activated = (Boolean) details.get("email_verified");
        }
        if (details.get("email") != null) {
            userModel.setEmail(((String) details.get("email")).toLowerCase());
        } else if (sub.contains("|") && (username != null && username.contains("@"))) {
            // special handling for Auth0
            userModel.setEmail(username);
        } else {
            userModel.setEmail(sub);
        }
        if (details.get("langKey") != null) {
            userModel.setLangKey((String) details.get("langKey"));
        } else if (details.get("locale") != null) {
            // trim off country code if it exists
            String locale = (String) details.get("locale");
            if (locale.contains("_")) {
                locale = locale.substring(0, locale.indexOf('_'));
            } else if (locale.contains("-")) {
                locale = locale.substring(0, locale.indexOf('-'));
            }
            userModel.setLangKey(locale.toLowerCase());
        } else {
            // set langKey to default if not specified by IdP
            userModel.setLangKey(Constants.DEFAULT_LANGUAGE);
        }
        if (details.get("picture") != null) {
            userModel.setImageUrl((String) details.get("picture"));
        }
        userModel.setActivated(activated);
        return userModel;
    }

    private void clearUserCaches(UserModel userModel) {
        Objects.requireNonNull(cacheManager.getCache(USERS_BY_LOGIN_CACHE)).evict(userModel.getLogin());
        if (userModel.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(USERS_BY_EMAIL_CACHE)).evict(userModel.getEmail());
        }
    }
}
