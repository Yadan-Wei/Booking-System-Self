package com.cs5500group6.BookingSystem.repository;

import com.cs5500group6.BookingSystem.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
}
