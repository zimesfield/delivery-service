package com.zimesfield.delivery.repository;

import com.zimesfield.delivery.adapter.rdbms.entity.UserEntity;
import com.zimesfield.delivery.domain.model.UserModel;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Spring Data JPA rdbms for the {@link UserEntity} entity.
 */
public interface UserRepository {
    Optional<UserModel> findOneByLogin(String login);

    Optional<UserModel> findOneWithAuthoritiesByLogin(String login);

    Page<UserModel> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);

    Optional<UserModel> findById(String id);

    UserModel save(UserModel userModel);

    Page<UserModel> findAll(Pageable pageable);

    void deleteAll();
}
