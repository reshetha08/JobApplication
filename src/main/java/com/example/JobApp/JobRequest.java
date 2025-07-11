package com.example.JobApp;

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
public class JobRequest {

    @NotBlank(message = "Please fill the title")
    private String title;

    @NotBlank(message = "Please fill the discription")
    private String description;

    @NotBlank(message = "location is required")
    private String location;

    @NotBlank(message = "Please specify the domain")
    private String domain;

    @NotNull(message = "experience is required")
    private int experience;
}
