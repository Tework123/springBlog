package com.example.springBlog.controllers;

import com.example.springBlog.dtos.admin.AdminGetUsersDto;
import com.example.springBlog.entities.User;
import com.example.springBlog.entities.enums.Role;
import com.example.springBlog.repositories.UserRepository;
import com.example.springBlog.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private UserRepository userRepository;
    private AdminService adminService;

    @GetMapping("/users")
    public List<AdminGetUsersDto> getUsers() {
        List<User> users = userRepository.findAll();
        return AdminGetUsersDto.toListDto(users);
    }

    @PatchMapping("/user/{id}/activate")
    public ResponseEntity<String> userActivate(@PathVariable("id") Long id) {
        boolean isActive = adminService.userActivate(id);
        return ResponseEntity.ok("User is: " + isActive);
    }

    @PatchMapping("/user/{id}/change_role")
    public ResponseEntity<String> userChangeRole(@PathVariable("id") Long id) {
        Set<Role> role = adminService.userChangeRole(id);
        return ResponseEntity.ok("Role user: " + role);
    }
}





