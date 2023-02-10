package com.cs5500group6.BookingSystem.service;

import com.cs5500group6.BookingSystem.model.Availability;
import com.cs5500group6.BookingSystem.model.Instructor;
import com.cs5500group6.BookingSystem.model.Reservation;
import com.cs5500group6.BookingSystem.model.Student;
import com.cs5500group6.BookingSystem.repository.AvailabilityRepo;
import com.cs5500group6.BookingSystem.repository.ReservationRepo;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

  @Autowired
  ReservationRepo reservationRepo;

  @Autowired
  AvailabilityRepo availabilityRepo;

  @Transactional
  public Reservation createReservation(Student student, Availability availability, String description) {
    Reservation toBeCreated = Reservation.builder().description(description).student(student)
        .utcStartTime(availability.getUtcStartTime()).utcEndTime(availability.getUtcEndTime())
        .availabilities(List.of(availability)).build();
    Reservation saved = reservationRepo.save(toBeCreated);
    availability.setReservation(saved);
    availabilityRepo.save(availability);
    return saved;
  }

  public List<Reservation> getStudentReservationsBetween(Student student, LocalDateTime from, LocalDateTime to) {
    List<Reservation> results = reservationRepo.findByUsernameAndBetween(student.getId(), from, to);
    return results;
  }

  public List<Reservation> getInstructorReservationsBetween(Instructor instructor, LocalDateTime from,
      LocalDateTime to) {
    List<Reservation> results = reservationRepo.findByInstructorAndBetween(instructor.getId(), from, to);
    return results;
  }

  public Reservation getReservationById(Long id) {
    Optional<Reservation> resv = reservationRepo.findById(id);
    return resv.orElse(null);
  }

  public List<Availability> findAvailabilitiesBetween(Instructor instructor, LocalDateTime from, LocalDateTime to) {
    return availabilityRepo.findAvailableBetween(instructor, from, to);
  }

  public Availability getAvailabilityById(Long id) {
    return availabilityRepo.findById(id).orElse(null);
  }

  public Iterable<Availability> createAvailability(Instructor instructor, LocalDateTime fromUtc,
      LocalDateTime toUtc, Duration duration) {
    List<Availability> toBeCreated = new ArrayList<>();
    LocalDateTime start = fromUtc, end = fromUtc.plus(duration);
    while (end.isBefore(toUtc) ||  end.isEqual(toUtc)) {
      toBeCreated.add(Availability.builder().instructor(instructor).utcStartTime(start).utcEndTime(end).build());
      start = end;
      end = start.plus(duration);
    }
    Iterable<Availability> saved = availabilityRepo.saveAll(toBeCreated);
    return saved;
  }
}
