package com.example.courseproject.service;

import com.example.courseproject.entity.User;
import com.example.courseproject.enums.Role;
import com.example.courseproject.model.UserCreateRequest;
import com.example.courseproject.model.UserListResponse;
import com.example.courseproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteAll(List<User> users) {
        userRepository.deleteAll(users);
    }

    @Override
    public User registerNewUser(UserCreateRequest userCreateRequest) {
        User user = new User();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setUsername(userCreateRequest.getUsername());
        user.setPassword(encoder.encode(userCreateRequest.getPassword()));
        user.setRole(Role.USER.toString());
        user.setEmail(userCreateRequest.getEmail());
        return save(user);
    }

    @Override
    public UserListResponse getAllUsers() {
        List<User> userList = userRepository.findAll();
        UserListResponse userListResponse = new UserListResponse();
        userListResponse.setUsers(userList);
        return userListResponse;
    }

    @Override
    public Boolean userAlreadyRegistered(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public void makeAdmins(List<User> users) {
        setRoleToUsers(users, Role.ADMIN);
    }

    @Override
    public void removeAdmins(List<User> users) {
        setRoleToUsers(users, Role.USER);
    }

    private void setRoleToUsers(List<User> users, Role role) {
        users.forEach(user -> {
            User dbUser = userRepository.findById(user.getUserId()).get();
            dbUser.setRole(role.toString());
            userRepository.save(dbUser);
        });
    }

    @Override
    public void blockAll(List<User> users) {
        enableOrDisableUsers(users, false);
    }

    @Override
    public void unblockAll(List<User> users) {
        enableOrDisableUsers(users, true);
    }

    private void enableOrDisableUsers(List<User> users, Boolean enable) {
        users.forEach(user -> {
            User dbUser = userRepository.findById(user.getUserId()).get();
            dbUser.setEnabled(enable);
            userRepository.save(dbUser);
        });
    }



}
