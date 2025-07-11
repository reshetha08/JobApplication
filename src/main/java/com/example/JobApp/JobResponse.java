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
public class JobResponse {

    private int id;

    private String title;

    private String description;

    private String location;

    private String domain;

    private int experience;

    private String createdByName;

    private int createdById;

    private LocalDateTime postedDate;
}
