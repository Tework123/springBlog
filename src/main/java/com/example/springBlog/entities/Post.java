package com.example.springBlog.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "posts")
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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


//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "post")
//    private List<Photo> photos = new ArrayList<>();

//    public void addPhotoToPost(Photo photo) {
//        photo.setPost(this);
//        photos.add(photo);
//    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;


//    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true,
//            fetch = FetchType.EAGER, mappedBy = "post")
//    @EqualsAndHashCode.Exclude
//    Set<UserPost> postStatus;

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