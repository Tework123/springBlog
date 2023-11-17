package com.example.springBlog.services;

import com.example.springBlog.dtos.user.SignUpDto;
import com.example.springBlog.dtos.user.UserEditDto;
import com.example.springBlog.entities.Follower;
import com.example.springBlog.entities.User;
import com.example.springBlog.entities.enums.FollowStatus;
import com.example.springBlog.entities.enums.Role;
import com.example.springBlog.exceptions.customExceptions.CustomException;
import com.example.springBlog.repositories.FollowerRepository;
import com.example.springBlog.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private PostService postService;
    private PasswordEncoder passwordEncoder;
    private FollowerRepository followerRepository;


    public void createUser(SignUpDto signUpDto) {
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "User already exist");
        }

        User user = new User();
        user.setUsername(signUpDto.getUsername());
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        user.getRoles().add(Role.ROLE_USER);

        userRepository.save(user);
    }

    public void editUser(UserEditDto userEditDto, User currentUser) {
        currentUser.setEmail(userEditDto.getEmail());
        userRepository.save(currentUser);
    }

    //  при удалении юзера его посты не удаляются
    public void deleteUser(Long id, User currentUser) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new CustomException(HttpStatus.NOT_FOUND, "User not found"));

        if (user.getId() != currentUser.getId()) {
            throw new CustomException(HttpStatus.FORBIDDEN, "You don't can delete this account");
        }

//      устанавливаем постам null в поле user
        postService.getUserPosts(user);

        userRepository.deleteById(id);
    }


    public String follow(Long id, User currentUser) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new CustomException(HttpStatus.NOT_FOUND, "Author not found"));

        Follower oldFollow = followerRepository.findByUserFollowerIdAndUserAuthorId(currentUser.getId(), id);
        if (oldFollow != null) {

            followerRepository.deleteById(oldFollow.getId());
            return "Unfollow from " + oldFollow.getUserAuthor().getUsername();

        } else {

            Follower follower = new Follower();

            //      установка подписчика
            follower.setUserFollower(currentUser);
            follower.setStatusFollow(FollowStatus.FREE_FOLLOW);

            //      установка автора
            follower.setUserAuthor(user);
            followerRepository.save(follower);
            return "Follow to " + follower.getUserAuthor().getUsername();
        }
    }

    public List<Follower> getAuthors(Long id) {
        return followerRepository.findByUserFollowerId(id);
    }

    public List<Follower> getFollowers(Long id) {
        return followerRepository.findByUserAuthorId(id);
    }
}



