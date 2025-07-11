package com.example.JobApp;

import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<UserResponse>> getAdminLogged(){

        UserResponse res = adminService.getLoggedAdmin();

        ApiResponse<UserResponse> result = new ApiResponse<>("Success", "fetched the logged in Admin", res);

        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getAdminById(@PathVariable int id){

        UserResponse res = adminService.getAdmin(id);

        ApiResponse<UserResponse> result = new ApiResponse<>("Success", "fetched the Admin with the given Id", res);

        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getAllAdmins(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "5") @Min(5) int size,
            @RequestParam(defaultValue = "id") String sortby

    ){
        Page<UserResponse> res = adminService.getAllAdmin(page, size, sortby);

        ApiResponse<Page<UserResponse>> result = new ApiResponse<>("Success", "Fetched All Admins Successfully", res);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<String>> getAllUsersCount(){

        String res = adminService.countOfRegisteredUsers();

        ApiResponse<String> result = new ApiResponse<>("Success", res, null);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/customers")
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getAllCustomers(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "5") @Min(1) int size,
            @RequestParam(defaultValue = "id") String sortby
    ){

        Page<UserResponse> res = adminService.getAllCustomers(page, size, sortby);

        ApiResponse<Page<UserResponse>> result = new ApiResponse<>("Success", "Fetchted all Customers", res);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getCustomerById(@PathVariable int id){

        UserResponse res = adminService.getCustomer(id);

        ApiResponse<UserResponse> result = new ApiResponse<>("Success", "Fetched the customer", res);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/employers")
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getAllEmployers(
            @RequestParam(defaultValue = "0")@Min(0) int page,
            @RequestParam(defaultValue = "5")@Min(1) int size,
            @RequestParam(defaultValue = "id") String sortby
    ){
        Page<UserResponse> res = adminService.getAllEmployers(page,size,sortby);

        ApiResponse<Page<UserResponse>> result = new ApiResponse<>("Success", "All Employers Successfully", res);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/employers/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getEmployer(@PathVariable int id){

        UserResponse res = adminService.getEmployer(id);

        ApiResponse<UserResponse> result = new ApiResponse<>("Success", "fetched Employer successfully", res);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/jobs")
    public ResponseEntity<ApiResponse<Page<JobResponse>>> getAllJobs(
            @RequestParam(defaultValue = "0")@Min(0) int page,
            @RequestParam(defaultValue = "5")@Min(1) int size,
            @RequestParam(defaultValue = "id") String sortby
    ){
        Page<JobResponse> res = adminService.getAllJobs(page, size, sortby);

        ApiResponse<Page<JobResponse>> result = new ApiResponse<>("Success", "fetched all jobs Successfully", res);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/applications")
    public ResponseEntity<ApiResponse<Page<AdminQueryResponse>>> getApplicatins(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "5") @Min(1) int size,
            @RequestParam(defaultValue = "id") String sortby
    ){
        Page<AdminQueryResponse> res = adminService.getAlljobsAppliedCount(page, size, sortby);

        ApiResponse<Page<AdminQueryResponse>> result = new ApiResponse<>("success", "fetched details", res);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
