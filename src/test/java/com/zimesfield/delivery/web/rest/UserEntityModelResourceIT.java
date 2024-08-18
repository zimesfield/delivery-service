package com.zimesfield.delivery.web.rest;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zimesfield.delivery.IntegrationTest;
import com.zimesfield.delivery.adapter.mapper.UserMapper;
import com.zimesfield.delivery.adapter.rdbms.UserJpaRepository;
import com.zimesfield.delivery.adapter.rdbms.entity.UserEntity;
import com.zimesfield.delivery.adapter.search.UserSearchRepository;
import com.zimesfield.delivery.domain.model.UserModel;
import com.zimesfield.delivery.security.AuthoritiesConstants;
import jakarta.persistence.EntityManager;
import java.util.*;
import java.util.Objects;
import java.util.function.Consumer;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
@IntegrationTest
class UserEntityModelResourceIT {

    private static final String DEFAULT_LOGIN = "johndoe";

    private static final String DEFAULT_ID = "id1";

    private static final String DEFAULT_EMAIL = "johndoe@localhost";

    private static final String DEFAULT_FIRSTNAME = "john";

    private static final String DEFAULT_LASTNAME = "doe";

    private static final String DEFAULT_IMAGEURL = "http://placehold.it/50x50";

    private static final String DEFAULT_LANGKEY = "en";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserJpaRepository userRepository;

    @Autowired
    private UserSearchRepository userSearchRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private MockMvc restUserMockMvc;

    private UserModel userModel;

    /**
     * Create a UserModel.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which has a required relationship to the UserModel entity.
     */
    public static UserModel createEntity(EntityManager em) {
        UserModel persistUserEntity = UserModel.builder().build();
        persistUserEntity.setId(DEFAULT_ID);
        persistUserEntity.setLogin(DEFAULT_LOGIN + RandomStringUtils.randomAlphabetic(5));
        persistUserEntity.setActivated(true);
        persistUserEntity.setEmail(RandomStringUtils.randomAlphabetic(5) + DEFAULT_EMAIL);
        persistUserEntity.setFirstName(DEFAULT_FIRSTNAME);
        persistUserEntity.setLastName(DEFAULT_LASTNAME);
        persistUserEntity.setImageUrl(DEFAULT_IMAGEURL);
        persistUserEntity.setLangKey(DEFAULT_LANGKEY);
        return persistUserEntity;
    }

    /**
     * Setups the database with one userEntity.
     */
    public static UserModel initTestUser(EntityManager em) {
        UserModel persistUserEntity = createEntity(em);
        persistUserEntity.setLogin(DEFAULT_LOGIN);
        persistUserEntity.setEmail(DEFAULT_EMAIL);
        return persistUserEntity;
    }

    @BeforeEach
    public void initTest() {
        userModel = initTestUser(em);
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
    void testUserEquals() throws Exception {
        TestUtil.equalsVerifier(UserEntity.class);
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setId(DEFAULT_ID);
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(userEntity1.getId());
        assertThat(userEntity1).isEqualTo(userEntity2);
        userEntity2.setId("id2");
        assertThat(userEntity1).isNotEqualTo(userEntity2);
        userEntity1.setId(null);
        assertThat(userEntity1).isNotEqualTo(userEntity2);
    }

    private void assertPersistedUsers(Consumer<List<UserEntity>> userAssertion) {
        userAssertion.accept(userRepository.findAll());
    }
}
