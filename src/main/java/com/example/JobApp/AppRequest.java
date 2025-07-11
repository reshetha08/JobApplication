package com.example.JobApp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppRequest {

    @NotNull
    private int appId;

    @NotNull(message = "job id is required")
    private int jobId;

    @NotNull(message = "user id is required")
    private int userId;

    @NotNull(message = "please specify the status")
    private Status status;

}
