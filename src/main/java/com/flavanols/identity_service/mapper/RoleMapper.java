package com.flavanols.identity_service.mapper;

import com.flavanols.identity_service.dto.request.RoleRequest;
import com.flavanols.identity_service.dto.response.RoleResponse;
import com.flavanols.identity_service.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest role);

    RoleResponse toRoleResponse(Role role);
}
