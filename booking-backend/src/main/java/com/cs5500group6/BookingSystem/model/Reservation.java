package com.cs5500group6.BookingSystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "Reservation")
@Table(indexes = {
    @Index(
        name="index_instructor_start_end",
        columnList = "utcStartTime, utcEndTime, student_id",
        unique = true
    )
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Setter(AccessLevel.NONE)
  Long id;

  String description;

  @OneToMany(mappedBy = "reservation")
  @JsonManagedReference()
  List<Availability> availabilities;

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  LocalDateTime utcStartTime;

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  LocalDateTime utcEndTime;

  @ManyToOne
  @JoinColumn(name="student_id")
  @NotNull
  Student student;

  @JsonGetter("studentUsername")
  public String getStudentUsername() {
    return this.student.user.username;
  }

  @JsonGetter("instructorUsername")
  public String getInstructorUsername() {
    return this.availabilities.stream().findFirst().get().instructor.user.username;
  }

}
