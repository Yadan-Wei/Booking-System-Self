package com.cs5500group6.BookingSystem.service;


import com.cs5500group6.BookingSystem.model.Instructor;
import com.cs5500group6.BookingSystem.model.Student;
import com.cs5500group6.BookingSystem.model.User;
import com.cs5500group6.BookingSystem.repository.InstructorRepository;
import com.cs5500group6.BookingSystem.repository.StudentRepository;
import com.cs5500group6.BookingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  StudentRepository studentRepository;

  @Autowired
  InstructorRepository instructorRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  public User createUser(String username, String password) {
    User user = new User();
    user.setUsername(username);
    String encodedPassword = passwordEncoder.encode(password);
    user.setPassword(encodedPassword);
    userRepository.save(user);
    return user;
  }

  public User getUserByUsername(String username) {
    return userRepository.getByUsername(username);
  }

  public Student updateStudentProfile(String username, Student student) {
    User user = getUserByUsername(username);
    if (user.getStudent() == null) {
      student.setUser(user);
      studentRepository.save(student);
      return student;
    }
    Student existing = user.getStudent();
    existing.setGrade(student.getGrade());
    userRepository.save(user);
    return existing;
  }

  public Instructor updateStudentProfile(String username, Instructor instructor) {
    User user = getUserByUsername(username);
    if (user.getInstructor() == null) {
      instructor.setUser(user);
      instructorRepository.save(instructor);
      return instructor;
    }
    Instructor existing = user.getInstructor();
    existing.setIntroduction(instructor.getIntroduction());
    userRepository.save(user);
    return existing;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = this.getUserByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return user;
  }

}
