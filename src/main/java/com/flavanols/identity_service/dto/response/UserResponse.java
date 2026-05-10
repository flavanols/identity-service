package com.flavanols.identity_service.dto.response;

import com.flavanols.identity_service.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserResponse {
    String id;
    String username;
    String firstname;
    String lastname;
    LocalDate dob;

    Set<RoleResponse> roles;
}
