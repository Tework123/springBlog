package com.example.springBlog.controllers;


import com.example.springBlog.dtos.User.LoginDto;
import com.example.springBlog.dtos.User.SignUpDto;
import com.example.springBlog.dtos.UserDto;
import com.example.springBlog.entities.User;
import com.example.springBlog.exceptions.ExampleExeption;
import com.example.springBlog.repositories.UserRepository;
import com.example.springBlog.services.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;


    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {

        // add check for email exists in DB
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        User user = new User();
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

//        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
//        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }


    @PostMapping("/registration")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        System.out.println(user);
        userService.createUser(user);
        return ResponseEntity.ok("Save user ok");
    }


    @GetMapping("")
    public ResponseEntity getUsers() {
        System.out.println(userRepository.findAll());
        return ResponseEntity.ok("Cerver worked norm");
    }

    @PostMapping("")
    public ResponseEntity registration(@RequestBody User user) {
//        userService.registration(user);
        return ResponseEntity.ok("Save user ok");
    }


    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) throws ExampleExeption {
        System.out.println(userRepository.findById(id));
        User user = userRepository.findById(id).orElse(null);

        return UserDto.toDto(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity geleteUser(@PathVariable Long id) throws ExampleExeption {
        userRepository.deleteById(id);

        return ResponseEntity.ok("User delete success");
    }


}



