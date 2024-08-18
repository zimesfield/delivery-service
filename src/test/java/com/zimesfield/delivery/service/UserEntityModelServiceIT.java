package com.zimesfield.delivery.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.zimesfield.delivery.IntegrationTest;
import com.zimesfield.delivery.adapter.rdbms.UserJpaRepository;
import com.zimesfield.delivery.adapter.rdbms.entity.UserEntity;
import com.zimesfield.delivery.adapter.search.UserSearchRepository;
import com.zimesfield.delivery.adapter.util.Constants;
import com.zimesfield.delivery.domain.model.AdminUserModel;
import com.zimesfield.delivery.security.AuthoritiesConstants;
import java.util.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for {@link UserService}.
 */
@IntegrationTest
@Transactional
class UserEntityModelServiceIT {

    private static final String DEFAULT_LOGIN = "johndoe_service";

    private static final String DEFAULT_EMAIL = "johndoe_service@localhost";

    private static final String DEFAULT_FIRSTNAME = "john";

    private static final String DEFAULT_LASTNAME = "doe";

    @Autowired
    private CacheManager cacheManager;

    private static final String DEFAULT_IMAGEURL = "http://placehold.it/50x50";

    private static final String DEFAULT_LANGKEY = "dummy";

    @Autowired
    private UserJpaRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * This rdbms is mocked in the com.zimesfield.delivery.rdbms.search test package.
     *
     * @see //com.zimesfield.delivery.repository.search.UserSearchRepositoryMockConfiguration
     */
    @SpyBean
    private UserSearchRepository spiedUserSearchRepository;

    private UserEntity userEntity;

    private Map<String, Object> userDetails;

    @BeforeEach
    public void init() {
        userEntity = new UserEntity();
        userEntity.setLogin(DEFAULT_LOGIN);
        userEntity.setActivated(true);
        userEntity.setEmail(DEFAULT_EMAIL);
        userEntity.setFirstName(DEFAULT_FIRSTNAME);
        userEntity.setLastName(DEFAULT_LASTNAME);
        userEntity.setImageUrl(DEFAULT_IMAGEURL);
        userEntity.setLangKey(DEFAULT_LANGKEY);

        userDetails = new HashMap<>();
        userDetails.put("sub", DEFAULT_LOGIN);
        userDetails.put("email", DEFAULT_EMAIL);
        userDetails.put("given_name", DEFAULT_FIRSTNAME);
        userDetails.put("family_name", DEFAULT_LASTNAME);
        userDetails.put("picture", DEFAULT_IMAGEURL);
    }

    @AfterEach
    public void cleanupAndCheck() {
        cacheManager
            .getCacheNames()
            .stream()
            .map(cacheName -> this.cacheManager.getCache(cacheName))
            .filter(Objects::nonNull)
            .forEach(Cache::clear);
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void testDefaultUserDetails() {
        OAuth2AuthenticationToken authentication = createMockOAuth2AuthenticationToken(userDetails);
        AdminUserModel adminUserModel = userService.getUserFromAuthentication(authentication);

        assertThat(adminUserModel.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(adminUserModel.getFirstName()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(adminUserModel.getLastName()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(adminUserModel.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(adminUserModel.isActivated()).isTrue();
        assertThat(adminUserModel.getLangKey()).isEqualTo(Constants.DEFAULT_LANGUAGE);
        assertThat(adminUserModel.getImageUrl()).isEqualTo(DEFAULT_IMAGEURL);
        //        assertThat(adminUserModel.getAuthorities()).contains(AuthoritiesConstants.ANONYMOUS);
    }

    @Test
    @Transactional
    void testUserDetailsWithUsername() {
        userDetails.put("preferred_username", "TEST");

        OAuth2AuthenticationToken authentication = createMockOAuth2AuthenticationToken(userDetails);
        AdminUserModel adminUserModel = userService.getUserFromAuthentication(authentication);

        assertThat(adminUserModel.getLogin()).isEqualTo("test");
    }

    @Test
    @Transactional
    void testUserDetailsWithLangKey() {
        userDetails.put("langKey", DEFAULT_LANGKEY);
        userDetails.put("locale", "en-US");

        OAuth2AuthenticationToken authentication = createMockOAuth2AuthenticationToken(userDetails);
        AdminUserModel adminUserModel = userService.getUserFromAuthentication(authentication);

        assertThat(adminUserModel.getLangKey()).isEqualTo(DEFAULT_LANGKEY);
    }

    @Test
    @Transactional
    void testUserDetailsWithLocale() {
        userDetails.put("locale", "it-IT");

        OAuth2AuthenticationToken authentication = createMockOAuth2AuthenticationToken(userDetails);
        AdminUserModel adminUserModel = userService.getUserFromAuthentication(authentication);

        assertThat(adminUserModel.getLangKey()).isEqualTo("it");
    }

    @Test
    @Transactional
    void testUserDetaikjklsWithUSLocaleUnderscore() {
        userDetails.put("locale", "en_US");

        OAuth2AuthenticationToken authentication = createMockOAuth2AuthenticationToken(userDetails);
        AdminUserModel adminUserModel = userService.getUserFromAuthentication(authentication);

        assertThat(adminUserModel.getLangKey()).isEqualTo("en");
    }

    @Test
    @Transactional
    void testUserDetailsWithUSLocaleDash() {
        userDetails.put("locale", "en-US");

        OAuth2AuthenticationToken authentication = createMockOAuth2AuthenticationToken(userDetails);
        AdminUserModel adminUserModel = userService.getUserFromAuthentication(authentication);

        assertThat(adminUserModel.getLangKey()).isEqualTo("en");
    }

    private OAuth2AuthenticationToken createMockOAuth2AuthenticationToken(Map<String, Object> userDetails) {
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            "anonymous",
            "anonymous",
            authorities
        );
        usernamePasswordAuthenticationToken.setDetails(userDetails);
        OAuth2User user = new DefaultOAuth2User(authorities, userDetails, "sub");

        return new OAuth2AuthenticationToken(user, authorities, "oidc");
    }
}
