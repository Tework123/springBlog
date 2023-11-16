package com.example.springBlog.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "posts")
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 3, max = 30, message = "3 to 30")
    private String name;

    @Column(name = "text", nullable = false, columnDefinition = "text")
    private String text;

    private LocalDateTime dateCreate;

    @PrePersist
    private void init() {
        dateCreate = LocalDateTime.now();
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true,
            fetch = FetchType.EAGER, mappedBy = "post")
    @EqualsAndHashCode.Exclude
    Set<UserPostStatus> postStatus;


    public Integer likes = 0;

    public Integer dislikes = 0;


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", dateCreate=" + dateCreate +
                '}';
    }


}