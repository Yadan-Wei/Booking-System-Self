package com.cs5500group6.BookingSystem.controller;

import com.cs5500group6.BookingSystem.model.Availability;
import com.cs5500group6.BookingSystem.model.CreateAvailabilityRequest;
import com.cs5500group6.BookingSystem.model.User;
import com.cs5500group6.BookingSystem.service.BookingService;
import com.cs5500group6.BookingSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.time.Duration;
import java.time.LocalDateTime;


@RestController
@RequestMapping("{username}/availabilities")
public class AvailabilityController {

    @Autowired
    BookingService bookingService;

    @Autowired
    UserService userService;

    @GetMapping("")
    public Iterable<Availability> list(@PathVariable("username") String username,
                                       @RequestParam(name = "from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")LocalDateTime from,
                                       @RequestParam(name = "to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")LocalDateTime to) {
        User user = userService.getUserByUsername(username);
        if (user == null || user.getInstructor() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The instructor does not exist.");
        }
        return bookingService.findAvailabilitiesBetween(user.getInstructor(), from, to);
    }

    @PostMapping("")
    @RolesAllowed({"Instructor"})
    public Iterable<Availability> create(@PathVariable("username") String username,
                                         @Valid @RequestBody CreateAvailabilityRequest request) {
        User user = userService.getUserByUsername(username);
        if (user == null || user.getInstructor() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The instructor does not exist.");
        }
        LocalDateTime from = request.getFromUtc();
        LocalDateTime to = request.getToUtc();
        if (from.isAfter(to)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The from time much be earlier than to time.");
        }
        Duration duration = Duration.ofMinutes(request.getDurationMinutes());
        return bookingService.createAvailability(user.getInstructor(), from, to, duration);
    }

}
