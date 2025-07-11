package com.example.JobApp;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employers")
public class EmployerController {

    @Autowired
    private EmployerService employerService;

    @GetMapping("/jobs")
    public ResponseEntity<ApiResponse<Page<JobResponse>>> getAllJobs(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "5") @Min(1) int size,
            @RequestParam(defaultValue = "id") String sortby
    ){

        Page<JobResponse> res = employerService.getAllJobsByEmployer(page,size, sortby);

        ApiResponse<Page<JobResponse>> result = new ApiResponse<>("success", "Successfully got all jobs", res);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*@GetMapping()
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getAllEmployers(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "5") @Min(1) int size,
            @RequestParam(defaultValue = "id") String sortby
    ){
        Page<UserResponse> res = employerService.getAllEmployers(page, size, sortby);

        ApiResponse<Page<UserResponse>> result = new ApiResponse<>("success", "fetched all employers", res);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }*/

    @GetMapping("/employer")
    public ResponseEntity<ApiResponse<UserResponse>> getEmployer(){
        UserResponse res = employerService.getEmployerLogged();

        ApiResponse<UserResponse> result = new ApiResponse<>("success", "fetched employer successfully", res);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<JobResponse>> createJob(@RequestBody @Valid JobRequest request){

        JobResponse res = employerService.createJob(request);

        ApiResponse<JobResponse> result = new ApiResponse<>("Success", "created a job successfully", res);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<AppResponse>> updateStatus(@RequestBody @Valid AppRequest request){

        AppResponse res = employerService.updateStatus(request);

        ApiResponse<AppResponse> result = new ApiResponse<>("success", "Status updated successfully", res);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/countjob")
    public ResponseEntity<ApiResponse<Page<JobApplicationCountResponse>>> getApplicationCountPerJob(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "5") @Min(1) int size,
            @RequestParam(defaultValue = "id") String sortby

    ){
        Page<JobApplicationCountResponse> res = employerService.getApplicationCountPerJobPosted(page, size, sortby);

        ApiResponse<Page<JobApplicationCountResponse>> result = new ApiResponse<>("success", "fetched the job counts", res);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/job/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteJob (@PathVariable int id){

        String res = employerService.deleteJob(id);

        ApiResponse<String> result = new ApiResponse<>("success",res, null);

        return ResponseEntity.ok(result);
    }
}
