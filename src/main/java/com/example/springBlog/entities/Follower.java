package com.example.springBlog.entities;


import com.example.springBlog.entities.enums.FollowStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "followers")
@Data
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_follower")
    @EqualsAndHashCode.Exclude
    User userFollower;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_author")
    @EqualsAndHashCode.Exclude
    User userAuthor;

    private LocalDate dateFollow;

    @PrePersist
    private void init() {
        dateFollow = LocalDate.now();
    }


//    @ElementCollection(targetClass = FollowStatus.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "status_follow", joinColumns = @JoinColumn(name = "follower_id"))
    @Enumerated(EnumType.STRING)
    private FollowStatus statusFollow;


}
