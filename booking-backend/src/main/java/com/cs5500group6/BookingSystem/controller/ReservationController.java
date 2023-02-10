package com.cs5500group6.BookingSystem.controller;

import com.cs5500group6.BookingSystem.model.Availability;
import com.cs5500group6.BookingSystem.model.MakeReservationRequest;
import com.cs5500group6.BookingSystem.model.Reservation;
import com.cs5500group6.BookingSystem.model.User;
import com.cs5500group6.BookingSystem.service.BookingService;
import com.cs5500group6.BookingSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("{username}/reservations")
public class ReservationController {
    final static int DEFAULT_PAGE_SIZE = 50;
    final BookingService bookingService;
    final UserService userService;

    @Autowired
    public ReservationController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @GetMapping("")
    @PreAuthorize("#username == authentication.principal.username")
    public List<Reservation> list(@PathVariable("username") final String username,
                                  @RequestParam(name = "from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime from,
                                  @RequestParam(name = "to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")LocalDateTime to) {
        final User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username does not exist.");
        }
        if (user.getStudent() != null) {
            return bookingService.getStudentReservationsBetween(user.getStudent(), from, to);
        }
        if (user.getInstructor() != null) {
            return bookingService.getInstructorReservationsBetween(user.getInstructor(), from, to);
        }
        return new ArrayList<>();
    }

    @GetMapping("/{id}")
    public Reservation getById(@PathVariable("id") long id) {
        Reservation reservation = this.bookingService.getReservationById(id);
        if (reservation == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return reservation;
    }

    @PostMapping("")
    @PreAuthorize("#username == authentication.principal.username")
    public Reservation create(@PathVariable("username") final String username,
                              @Valid @RequestBody final MakeReservationRequest request) {
        final User user = userService.getUserByUsername(username);
        if (user == null || user.getStudent() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "student doesn't exist");
        }
        final Availability availability = bookingService.getAvailabilityById(request.getAvailabilityId());
        if (availability == null || availability.getReservation() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not available");
        }
        Reservation saved = this.bookingService.createReservation(user.getStudent(), availability,
                request.getDescription());
        return saved;
    }
}
