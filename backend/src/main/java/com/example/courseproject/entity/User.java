package com.example.courseproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username")
    private String username;

    @org.springframework.data.annotation.Transient
    @JsonIgnore
    @Column(name = "password")
    private String password;

    @org.springframework.data.annotation.Transient
    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    @org.springframework.data.annotation.Transient
    @Column(name = "enabled")
    private Boolean enabled = true;

    @org.springframework.data.annotation.Transient
    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<ItemCollection> collections;

    @org.springframework.data.annotation.Transient
    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments;

}
