package com.cs5500group6.BookingSystem.controller;

import com.cs5500group6.BookingSystem.model.CreateUserRequest;
import com.cs5500group6.BookingSystem.model.Instructor;
import com.cs5500group6.BookingSystem.model.Student;
import com.cs5500group6.BookingSystem.model.User;
import com.cs5500group6.BookingSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true", allowedHeaders = "*")
@RestController
public class UserController {

  @Autowired
  UserService userService;

  @PostMapping("users")
  public User createUser(@RequestBody CreateUserRequest user) {
    if (userService.getUserByUsername(user.getUsername()) != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username already existed.");
    }
    return userService.createUser(user.getUsername(), user.getPassword());
  }

  @GetMapping("users/{username}")
  public User getUser(@PathVariable("username") String username) {
    return userService.getUserByUsername(username);
  }

  @PostMapping("students/{username}")
  public Student createOrUpdateStudent(@PathVariable("username") String username, @RequestBody Student student) {
    User user = userService.getUserByUsername(username);
    if (user == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "username doesn't exist.");
    }
    Student updated = userService.updateStudentProfile(username, student);
    return updated;
  }

  @PostMapping("instructors/{username}")
  public Instructor createOrUpdateInstructor(@PathVariable("username") String username,
      @RequestBody Instructor instructor, @AuthenticationPrincipal User currentUser) {

    if (!currentUser.getUsername().equals(username)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "xxxx");
    }

    User user = userService.getUserByUsername(username);
    if (user == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "username doesn't exist.");
    }
    Instructor updated = userService.updateStudentProfile(username, instructor);
    return updated;
  }

  @GetMapping("login")
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public String login() {
    return "Please login";
  }

  @GetMapping("users/current")
  public String currentUser(@AuthenticationPrincipal User user) {
    return user.getUsername();
  }

}