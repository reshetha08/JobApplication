package com.example.JobApp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppResponse {

    private int id;

    private int userId;

    private String userName;

    private int jobId;

    private String title;

    private Status status;

    private LocalDateTime appliedDate;
}
