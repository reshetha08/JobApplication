package com.example.JobApp;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /*@GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(
                  @RequestParam(defaultValue = "0") int page,
                  @RequestParam(defaultValue = "5") int size,
                  @RequestParam(defaultValue = "id") String sortby
    ){

        Page<UserResponse> res = userService.getAllUsers(page, size, sortby);

        return ResponseEntity.ok(res);
    }*/

    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getAllUsers(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "5") @Min(1)  int size,
            @RequestParam(defaultValue = "id") String sortby
    ){

        Page<UserResponse> res = userService.getAllUsers(page, size, sortby);
        ApiResponse<Page<UserResponse>> result = new ApiResponse<>("sucess", "Successfully fetched all Users", res);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable int id){

        UserResponse res = userService.getUser(id);
        ApiResponse<UserResponse> result= new ApiResponse<>("success", "get fetched successfully", res);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(@RequestBody @Valid User user){

        UserResponse res = userService.register(user.getName(), user.getEmail(),user.getPassword(), user.getRole());

        ApiResponse<UserResponse> result = new ApiResponse<>("Success", "User Registered Successfully", res);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> loginUser(@RequestBody @Valid User user){

        AuthResponse res = userService.login(user.getEmail(), user.getPassword());

        ApiResponse<AuthResponse> result = new ApiResponse<>("Success", "User logged in Successfully", res);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteUser(@PathVariable int id){

        boolean res = userService.deleteUser(id);

        if(res) {
            return new ApiResponse<>("Success", "User deleted", null);
        }

        return new ApiResponse<>("Error", "User not deleted", null);
    }
}
