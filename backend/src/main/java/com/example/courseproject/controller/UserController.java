package com.example.courseproject.controller;

import com.example.courseproject.model.UserListResponse;
import com.example.courseproject.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/all-users")
    public ResponseEntity<UserListResponse> getAllUsers() {
        UserListResponse userListResponse = userService.getAllUsers();
        return new ResponseEntity<>(userListResponse, HttpStatus.OK);
    }

    @PostMapping("/admin/delete-users")
    public ResponseEntity<UserListResponse> deleteUsers(@RequestBody UserListResponse userListResponse) {
        userService.deleteAll(userListResponse.getUsers());
        return new ResponseEntity<>(userListResponse, HttpStatus.OK);
    }

    @PostMapping("/admin/block-users")
    public ResponseEntity<UserListResponse> blockUsers(@RequestBody UserListResponse userListResponse) {
        userService.blockAll(userListResponse.getUsers());
        return new ResponseEntity<>(userListResponse, HttpStatus.OK);
    }

    @PostMapping("/admin/unblock-users")
    public ResponseEntity<UserListResponse> unBlockUsers(@RequestBody UserListResponse userListResponse) {
        userService.unblockAll(userListResponse.getUsers());
        return new ResponseEntity<>(userListResponse, HttpStatus.OK);
    }

    @PostMapping("/admin/make-admins")
    public ResponseEntity<UserListResponse> makeUsersAdmins(@RequestBody UserListResponse userListResponse) {
        userService.makeAdmins(userListResponse.getUsers());
        return new ResponseEntity<>(userListResponse, HttpStatus.OK);
    }

    @PostMapping("/admin/remove-admins")
    public ResponseEntity<UserListResponse> removeAdmins(@RequestBody UserListResponse userListResponse) {
        userService.removeAdmins(userListResponse.getUsers());
        return new ResponseEntity<>(userListResponse, HttpStatus.OK);
    }

}
