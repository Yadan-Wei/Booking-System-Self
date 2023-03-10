package com.cs5500group6.BookingSystem.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@ToString(exclude = { "password" })
@Entity(name = "User")
@Table(name = "\"user\"", indexes = { @Index(name = "username_index", columnList = "username", unique = true) })
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  @NotEmpty
  @Column(nullable = false)
  String username;

  @JsonIgnore()
  String password;

  @OneToOne(mappedBy = "user")
  @JsonManagedReference("user-instructor")
  Instructor instructor;

  @OneToOne(mappedBy = "user")
  @JsonManagedReference("user-student")
  Student student;

  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    if (this.instructor != null) {
      authorities.add(new SimpleGrantedAuthority("ROLE_Instructor"));
    }
    if (this.student != null) {
      authorities.add(new SimpleGrantedAuthority("ROLE_Student"));
    }
    return authorities;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isEnabled() {
    return true;
  }

}


