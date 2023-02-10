package com.cs5500group6.BookingSystem.repository;

import com.cs5500group6.BookingSystem.model.Availability;
import com.cs5500group6.BookingSystem.model.Instructor;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AvailabilityRepo extends CrudRepository<Availability, Long> {

  @Query(value = "SELECT a from Availability a " + "WHERE a.instructor = :instructor AND "
      + "a.utcStartTime >= :from AND a.utcEndTime < :to")
  List<Availability> findBetween(@Param("instructor") Instructor instructor, @Param("from") LocalDateTime from,
      @Param("to") LocalDateTime to);

  @Query(value = "SELECT a from Availability a " + "WHERE a.instructor = :instructor AND "
      + "a.utcStartTime >= :from AND a.utcEndTime < :to AND " + "a.reservation is NULL")
  List<Availability> findAvailableBetween(@Param("instructor") Instructor instructor,
      @Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

}
