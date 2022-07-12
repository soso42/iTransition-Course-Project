package com.example.courseproject.controller;

import com.example.courseproject.entity.User;
import com.example.courseproject.model.UserCreateRequest;
import com.example.courseproject.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*")
public class RegistrationController {

    private final UserServiceImpl userService;

    @Autowired
    public RegistrationController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> registerUser(@RequestBody UserCreateRequest userCreateRequest) {
        User user = new User();
        if (userService.userAlreadyRegistered(userCreateRequest.getUsername())) {
            return new ResponseEntity<>(user, HttpStatus.FORBIDDEN);
        }
        user = userService.registerNewUser(userCreateRequest);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
