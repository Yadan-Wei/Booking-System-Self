package com.cs5500group6.BookingSystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@Entity(name="Instructor")
@Data
@ToString(exclude = {"user"})
@EqualsAndHashCode(exclude = {"user"})
public class Instructor implements Serializable {

  @Id
  @Setter(AccessLevel.NONE)
  Long id;

  @OneToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name="user_id")
  @MapsId
  @JsonBackReference("user-instructor")
  User user;

  String introduction;
}
