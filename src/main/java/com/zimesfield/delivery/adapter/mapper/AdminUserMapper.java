package com.zimesfield.delivery.adapter.mapper; //package com.zimesfield.booking.adapter.service.mapper;

import com.zimesfield.delivery.adapter.rdbms.entity.Authority;
import com.zimesfield.delivery.adapter.rdbms.entity.DeliveryEntity;
import com.zimesfield.delivery.adapter.rdbms.entity.UserEntity;
import com.zimesfield.delivery.domain.model.AdminUserModel;
import com.zimesfield.delivery.domain.model.DeliveryModel;
import com.zimesfield.delivery.domain.model.UserModel;
import com.zimesfield.delivery.model.UserInfo;
import java.util.Set;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link DeliveryEntity} and its Model {@link DeliveryModel}.
 */
@Mapper(componentModel = "spring")
public interface AdminUserMapper extends EntityMapper<AdminUserModel, UserEntity> {
    AdminUserModel toModel(UserModel userModel);

    String map(Authority value);

    Authority map(String value);

    Set<String> toAuthority(Set<Authority> value);
    Set<Authority> toAuthorities(Set<String> value);

    UserInfo toResponse(UserModel userModel);
}
