package com.cs5500group6.BookingSystem.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

// A class which should be persisted in a database it must be annotated with
// javax.persistence.Entity. Such a class is called Entity
// All entity classes must define a primary key, must have a
// non-arg constructor and or not allowed to be final.
// Keys can be a single field or a combination of fields.
// By default, the table name corresponds to the class name. You can change this with
// the addition to the annotation @Table(name="NEWTABLENAME").
@Entity(name="Availability")
//Specifies the primary table for the annotated entity.
@Table(indexes = {
    //Used in schema generation to specify creation of an index.
    @Index(
        // (Optional) The name of the index; defaults to a provider-generated name.
        name="index_instructor_start_end",
        //(Required) The names of the columns to be included in the index, in order.
        columnList = "utcStartTime, utcEndTime, instructor_id",
        //(Optional) Whether the index is unique.
        unique = true
    )
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Availability {

  //Specifies the primary key of an entity.
  @Id
  //GenerationType: AUTO, IDENTITY, SEQUENCE, TABLE
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Setter(AccessLevel.NONE)
  Long id;

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  LocalDateTime utcStartTime;

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  LocalDateTime utcEndTime;

  //JPA allows to define relationships between classes
  @ManyToOne
  //Specifies a column for joining an entity association or element collection.
  @JoinColumn(name = "instructor_id")
  @NotNull
  Instructor instructor;


  @ManyToOne
  @JoinColumn(name = "reservation_id")
  @JsonBackReference
  Reservation reservation;

  @Version
  @Setter(AccessLevel.NONE)
  Integer version;

}
