package com.example.CRUD.controller;

import com.example.CRUD.model.Users;
import com.example.CRUD.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UsersController {

    @Autowired
    private UsersRepo usersRepo;

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<Users>> getAllUsers() {
        try {
            List<Users> users = new ArrayList<>();
            usersRepo.findAll().forEach(users::add);
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable int id) {
        try {
            Optional<Users> user = usersRepo.findById(id);
            if (user.isPresent()) {
                return new ResponseEntity<>(user.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<Users> addUser(@RequestBody Users user) {
        Users usersObj = usersRepo.save(user);
        return new ResponseEntity<>(usersObj, HttpStatus.OK);
    }

    @PostMapping("/updateUserById/{id}")
    public ResponseEntity<Users> updateUserById(@PathVariable int id, @RequestBody Users userData) {
        try {
            Optional<Users> user = usersRepo.findById(id);
            if (user.isPresent()) {
                Users newUserData = user.get();
                newUserData.setUsername(userData.getUsername());
                newUserData.setPassword(userData.getPassword());
                Users updateUser = usersRepo.save(newUserData);
                return new ResponseEntity<>(updateUser, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteUserById/{id}")
    public ResponseEntity<Users> deleteUserById(@PathVariable int id) {
        usersRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
