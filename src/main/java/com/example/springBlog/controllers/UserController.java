package com.example.springBlog.controllers;


import com.example.springBlog.dtos.User.SignUpDto;
import com.example.springBlog.dtos.UserDto;
import com.example.springBlog.entities.User;
import com.example.springBlog.entities.enums.Role;
import com.example.springBlog.exceptions.ExampleExeption;
import com.example.springBlog.repositories.UserRepository;
import com.example.springBlog.services.UserService;
import com.example.springBlog.settings.security.jwt.JwtRequest;
import com.example.springBlog.settings.security.jwt.JwtResponse;
import com.example.springBlog.settings.security.jwt.JwtTokenUtil;
import com.example.springBlog.settings.security.JwtUserDetailsService;

import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService jwtUserDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        userService.createUser(signUpDto);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }


    @GetMapping("/user")
    public List<User> getUsers() {
        System.out.println(userRepository.findAll());
        return userRepository.findAll();
    }

    @RolesAllowed("USER")
    @GetMapping("/admin")
    public ResponseEntity<?> admin(Authentication auth) {

        System.out.println(auth.getName());
        System.out.println(auth);
        User user = userRepository.findByUsername(auth.getName());


        System.out.println("admin here");
        return new ResponseEntity<>("admin here", HttpStatus.OK);
    }

    @GetMapping("/error/forbidden")
    public ResponseEntity<?> forbidden() {
        return new ResponseEntity<>("sorry forbidden", HttpStatus.FORBIDDEN);
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



