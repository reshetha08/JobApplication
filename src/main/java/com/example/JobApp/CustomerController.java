package com.example.JobApp;

import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer")
    public ResponseEntity<ApiResponse<UserResponse>> getLoggedUser(){

        UserResponse res = customerService.getCustomer();

        ApiResponse<UserResponse> result = new ApiResponse<>("Success", "Customer fetched successfully", res);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/jobs")
    public ResponseEntity<ApiResponse<Page<JobResponse>>> getAllJobs(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "5") @Min(1) int size,
            @RequestParam(defaultValue = "id") String sortby
    ){

        Page<JobResponse> res = customerService.getAllJobs(page,size,sortby);

        ApiResponse<Page<JobResponse>> result = new ApiResponse<>("Success", "fetched all jobs list successfully", res);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/applied")
    public ResponseEntity<ApiResponse<Page<AppResponse>>> getAppliedJobs(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "5") @Min(1) int size,
            @RequestParam(defaultValue = "id") String sortby
    ){
        Page<AppResponse> res = customerService.getAppliedJobs(page, size, sortby);

        ApiResponse<Page<AppResponse>> result = new ApiResponse<>("success", "successfully fetched applied jobs", res);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/apply/{id}")
    public ResponseEntity<ApiResponse<AppResponse>> applyApplication(@PathVariable int id){

        AppResponse res = customerService.applyForJob(id);

        ApiResponse<AppResponse> result = new ApiResponse<>("Success", "Application applied successfully", res);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<JobResponse>>> searchJobs(
            @RequestParam(required = false) String domain,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer experience,
            @RequestParam(required = false) String employerName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Page<JobResponse> jobs = customerService.searchJobs(domain, location, experience, employerName, page, size, sortBy);
        ApiResponse<Page<JobResponse>> res = new ApiResponse<>("Success", "results fetched", jobs);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/application/{id}")
    public ResponseEntity<ApiResponse<String>> deleteApplication(@PathVariable int id){

        String res = customerService.deleteApplication(id);

        ApiResponse<String> result = new ApiResponse<>("success", res, null);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
