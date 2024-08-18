package com.zimesfield.delivery.adapter.rdbms.impl;

import com.zimesfield.delivery.adapter.mapper.UserMapper;
import com.zimesfield.delivery.adapter.rdbms.UserJpaRepository;
import com.zimesfield.delivery.adapter.rdbms.entity.UserEntity;
import com.zimesfield.delivery.domain.model.UserModel;
import com.zimesfield.delivery.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA rdbms for the {@link UserEntity} entity.
 */
@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    public Optional<UserModel> findOneByLogin(String login) {
        return userJpaRepository.findByLogin(login).map(userMapper::toModel);
    }

    public Optional<UserModel> findOneWithAuthoritiesByLogin(String login) {
        return userJpaRepository.findOneWithAuthoritiesByLogin(login).map(userMapper::toModel);
    }

    public Page<UserModel> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable) {
        return userJpaRepository.findAllByIdNotNullAndActivatedIsTrue(pageable).map(userMapper::toModel);
    }

    @Override
    public Optional<UserModel> findById(String id) {
        return userJpaRepository.findById(id).map(userMapper::toModel);
    }

    @Override
    public UserModel save(UserModel userModel) {
        return userMapper.toModel(userJpaRepository.save(userMapper.toEntity(userModel)));
    }

    @Override
    public Page<UserModel> findAll(Pageable pageable) {
        return userJpaRepository.findAll(pageable).map(userMapper::toModel);
    }

    @Override
    public void deleteAll() {
        userJpaRepository.deleteAll();
    }
}
