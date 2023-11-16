package com.example.springBlog.entities;

import com.example.springBlog.entities.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Size(min = 3, max = 50, message = "email length should 3 to 50")
    private String email;

    @Column(unique = true)
    @Size(min = 3, max = 50, message = "username length should 3 to 50")
    private String username;

    @Column(length = 1000)
    @Size(min = 4, max = 1000, message = "password length should 4 to 50")
    private String password;

    private boolean active;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Post> posts = new ArrayList<>();


    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true,
            fetch = FetchType.LAZY, mappedBy = "userFollower")
    @EqualsAndHashCode.Exclude
    private Set<Follower> follows;


    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true,
            fetch = FetchType.LAZY, mappedBy = "userAuthor")
    @EqualsAndHashCode.Exclude
    private Set<Follower> authors;

    private LocalDate dateJoined;

    @PrePersist
    private void init() {
        dateJoined = LocalDate.now();
    }


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();


    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true,
            fetch = FetchType.EAGER, mappedBy = "user")
    @EqualsAndHashCode.Exclude
    Set<UserPostStatus> postStatus = new HashSet<>();
//
//    public boolean isAdmin() {
//        return roles.contains(Role.ROLE_ADMIN);
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", active=" + active +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
