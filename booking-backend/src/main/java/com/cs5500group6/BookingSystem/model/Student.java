package com.cs5500group6.BookingSystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Entity(name="Student")
@Data
@ToString(exclude = {"user"})
@EqualsAndHashCode(exclude = {"user"})
public class Student implements Serializable {

      @Id
      @Setter(AccessLevel.NONE)
      Long id;

      @OneToOne(cascade = {CascadeType.ALL})
      @JoinColumn(name="user_id")
      @MapsId
      @JsonBackReference("user-student")
      User user;

      String grade;
}
