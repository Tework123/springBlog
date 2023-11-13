package com.example.springBlog.controllers;


import com.example.springBlog.dtos.User.LoginDto;
import com.example.springBlog.dtos.User.SignUpDto;
import com.example.springBlog.dtos.UserDto;
import com.example.springBlog.entities.User;
import com.example.springBlog.exceptions.ExampleExeption;
import com.example.springBlog.repositories.UserRepository;
import com.example.springBlog.services.UserService;
import com.example.springBlog.settings.security.JwtRequest;
import com.example.springBlog.settings.security.JwtResponse;
import com.example.springBlog.settings.security.JwtTokenUtil;
import com.example.springBlog.settings.security.JwtUserDetailsService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
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


//    @PostMapping("/signin")
//    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                loginDto.getEmail(), loginDto.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        System.out.println("login");
//        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
//    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        userService.createUser(signUpDto);
        System.out.println("registrer");
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }


    @GetMapping("/user")
    public List<User> getUsers() {
        System.out.println(userRepository.findAll());
        return userRepository.findAll();
    }

    @GetMapping("/admin")
    public ResponseEntity<?> admin() {
        System.out.println("admin here");
        return new ResponseEntity<>("admin here", HttpStatus.OK);
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



