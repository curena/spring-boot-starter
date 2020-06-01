package org.cecil.start.service.mapping;

import org.cecil.start.api.auth.jwt.model.UserModel;
import org.cecil.start.db.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(UserModel user);

    UserModel toModel(UserEntity userEntity);
}
