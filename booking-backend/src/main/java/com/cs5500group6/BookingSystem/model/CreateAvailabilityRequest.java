package com.cs5500group6.BookingSystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
//The controller will first take a domain object, then it will validate it
// with Hibernate Validator, and finally it will persist it into an in-memory H2 database.
public class CreateAvailabilityRequest {
  @NotNull
  //validate the time must be a future time
  @Future
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  LocalDateTime fromUtc;

  @NotNull
  @Future
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  LocalDateTime toUtc;

  @NotNull
  // validate at most two hours created
  @Min(0)
  @Max(120)
  Integer durationMinutes;

}
