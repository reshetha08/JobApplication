package com.example.JobApp;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    @NotNull(message = "please fill the name")
    @NotBlank(message = "Please fill in the name")
    private String name;

    @Email(message = "Email should be Valid")
    @NotBlank(message = "please fill in the email")
    private String email;

    @NotBlank(message = "please fill in the role")
    @NotNull(message = "please fill the role")
    @EnumValidator(enumClass = Role.class, message = "Role must be Custom/Employe/Admin")
    private String role;

    @NotBlank(message = "please fill the password")
    private String password;
}
