package com.example.springBlog.controllers.user;

import com.example.springBlog.dtos.ResponseDto;
import com.example.springBlog.dtos.user.FollowerDto;
import com.example.springBlog.dtos.user.UserEditDto;
import com.example.springBlog.dtos.user.UserProfilePageDto;
import com.example.springBlog.entities.Follower;
import com.example.springBlog.entities.User;
import com.example.springBlog.exceptions.customExceptions.CustomException;
import com.example.springBlog.repositories.UserRepository;
import com.example.springBlog.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserProfileController {

    private final UserRepository userRepository;
    private final UserService userService;


    @GetMapping("/{id}")
    public UserProfilePageDto getUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new CustomException(HttpStatus.NOT_FOUND, "User not found"));
        return UserProfilePageDto.toDto(user);
    }

    @PatchMapping("/edit")
    public ResponseEntity<ResponseDto> editUser(@Validated @RequestBody
                                                UserEditDto userEditDto,
                                                Authentication auth) {
        User currentUser = userRepository.findByUsername(auth.getName());
        userService.editUser(userEditDto, currentUser);
        return ResponseEntity.ok(ResponseDto.toDto("User edit success"));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable Long id,
                                                  Authentication auth) {
        User currentUser = userRepository.findByUsername(auth.getName());

        userService.deleteUser(id, currentUser);
        return ResponseEntity.ok(ResponseDto.toDto("User delete success"));
    }

    @PostMapping(path = "/follow/{id}")
    public ResponseEntity<ResponseDto> followToUser(@PathVariable("id") Long id,
                                                    Authentication auth) {
        User currentUser = userRepository.findByUsername(auth.getName());
        String response = userService.follow(id, currentUser);
        return ResponseEntity.ok(ResponseDto.toDto(response));
    }

    @GetMapping("/{id}/authors")
    public String getAuthors(@PathVariable("id") Long id) {
        List<Follower> authors = userService.getAuthors(id);
//       don't work yet
        return "user/authorsTemplate";
    }

    @GetMapping("/{id}/followers")
    public List<FollowerDto> getFollowers(@PathVariable("id") Long id) {
        List<Follower> followers = userService.getFollowers(id);
        return FollowerDto.toListDto(followers);
    }
}




