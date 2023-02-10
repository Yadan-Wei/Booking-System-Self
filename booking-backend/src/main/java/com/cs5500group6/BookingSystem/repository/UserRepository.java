package com.cs5500group6.BookingSystem.repository;

import com.cs5500group6.BookingSystem.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User getByUsername(String username);
}
