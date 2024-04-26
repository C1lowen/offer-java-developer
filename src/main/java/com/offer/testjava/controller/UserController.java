package com.offer.testjava.controller;


import com.offer.testjava.dto.CreateUserDTO;
import com.offer.testjava.dto.UpdateUserDTO;
import com.offer.testjava.mapper.UserMapper;
import com.offer.testjava.model.User;
import com.offer.testjava.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody CreateUserDTO user) {
        User userMap = UserMapper.mapFromDto(user);
        userService.addUser(userMap);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<CreateUserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAllName(
            @PathVariable("id") Integer id,
            @Valid @RequestBody UpdateUserDTO updateUser) {

        userService.updateAllName(id, updateUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable("id") Integer id
    ) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<CreateUserDTO>> deleteUser(
            @RequestParam("from") @DateTimeFormat(pattern = "dd-MM-yyyy") Date dateFrom,
            @RequestParam("to") @DateTimeFormat(pattern = "dd-MM-yyyy") Date dateTo
    ) {
        if (dateFrom.after(dateTo)) {
            return ResponseEntity.badRequest().build();
        }

        List<CreateUserDTO> usersInRange = userService.getUsersByBirthdateRange(dateFrom, dateTo);
        return ResponseEntity.ok(usersInRange);
    }
}
