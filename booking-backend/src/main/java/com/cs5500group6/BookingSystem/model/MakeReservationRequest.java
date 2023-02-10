package com.cs5500group6.BookingSystem.model;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MakeReservationRequest {

  @NotNull
  @Min(0)
  Long availabilityId;

  @NotEmpty
  String description;

}
