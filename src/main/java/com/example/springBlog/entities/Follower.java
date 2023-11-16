package com.example.springBlog.entities;


import com.example.springBlog.entities.enums.FollowStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@Table(name = "followers")
@Data
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private LocalDate dateFollow;

    @PrePersist
    private void init() {
        dateFollow = LocalDate.now();
    }

    @Enumerated(EnumType.STRING)
    private FollowStatus statusFollow;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_follower")
    @EqualsAndHashCode.Exclude
    User userFollower;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_author")
    @EqualsAndHashCode.Exclude
    User userAuthor;

}
