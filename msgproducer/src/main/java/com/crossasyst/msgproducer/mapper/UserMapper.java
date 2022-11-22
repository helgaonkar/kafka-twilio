package com.crossasyst.msgproducer.mapper;

import com.crossasyst.msgproducer.entity.UserEntity;
import com.crossasyst.msgproducer.model.UserModel;
import org.mapstruct.Mapper;
import org.springframework.web.bind.annotation.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper{

    UserEntity modelToEntity (UserModel userModel);

    UserModel entityToModel (UserEntity userEntity);

}
