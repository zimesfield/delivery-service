package com.zimesfield.delivery.adapter.mapper;

import com.zimesfield.delivery.adapter.rdbms.entity.UserEntity;
import com.zimesfield.delivery.domain.model.UserModel;
import com.zimesfield.delivery.model.UserInfo;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserModel, UserEntity> {
    UserInfo toResponse(UserModel userModel);
}
