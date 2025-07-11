package com.example.JobApp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminQueryResponse {

    private int employerId;

    private String employerName;

    private List<JobApplicationCountResponse> responseList;
}
