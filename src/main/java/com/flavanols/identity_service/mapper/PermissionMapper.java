package com.flavanols.identity_service.mapper;

import com.flavanols.identity_service.dto.request.PermissionRequest;
import com.flavanols.identity_service.dto.request.UserCreationRequest;
import com.flavanols.identity_service.dto.request.UserUpdateRequest;
import com.flavanols.identity_service.dto.response.PermissionResponse;
import com.flavanols.identity_service.dto.response.UserResponse;
import com.flavanols.identity_service.entity.Permission;
import com.flavanols.identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission request);

}
