package com.flavanols.identity_service.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserCreationRequest {
    @Size(min = 3,message = "USERNAME_INVALID")
    private String username;

    @Size(min = 8,message = "INVALID_PASSWORD")
    private String password;
    private String firstname;
    private String lastname;
    private LocalDate dob;

}
