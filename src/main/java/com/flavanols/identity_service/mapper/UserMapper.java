package com.flavanols.identity_service.mapper;

import com.flavanols.identity_service.dto.request.UserCreationRequest;
import com.flavanols.identity_service.dto.request.UserUpdateRequest;
import com.flavanols.identity_service.dto.response.UserResponse;
import com.flavanols.identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
