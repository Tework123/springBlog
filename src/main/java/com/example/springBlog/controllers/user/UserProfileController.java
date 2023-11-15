package com.example.springBlog.controllers.user;

import com.example.springBlog.dtos.UserDto;
import com.example.springBlog.dtos.user.FollowerDto;
import com.example.springBlog.entities.Follower;
import com.example.springBlog.entities.User;
import com.example.springBlog.exceptions.customExceptions.CustomException;
import com.example.springBlog.repositories.UserRepository;
import com.example.springBlog.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserProfileController {

    private final UserRepository userRepository;
    private final UserService userService;


    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        System.out.println(userRepository.findById(id));
        User user = userRepository.findById(id).orElseThrow(()
                -> new CustomException(HttpStatus.NOT_FOUND, "User not found"));
        return UserDto.toDto(user);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok("User delete success");
    }

    @PostMapping("/follow/{id}")
    public ResponseEntity<String> followToUser(@PathVariable("id") Long id,
                                               Authentication auth) {
        User currentUser = userRepository.findByUsername(auth.getName());
        String response = userService.follow(id, currentUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/authors")
    public String getAuthors(@PathVariable("id") Long id) {
        List<Follower> authors = userService.getAuthors(id);
        return "user/authorsTemplate";
    }

    @GetMapping("/{id}/followers")
    public List<FollowerDto> getFollowers(@PathVariable("id") Long id) {
        List<Follower> followers = userService.getFollowers(id);
        return FollowerDto.toListDto(followers);
    }
}




