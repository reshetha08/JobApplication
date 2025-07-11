package com.example.JobApp;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusRequest {

    @NotNull(message = "user id is required")
    private int userId;

    @EnumValidator(enumClass = Status.class, message = "status must be applied/shorlisted/selected/rejected")
    private String status;
}


