package com.offer.testjava.controller;


import com.offer.testjava.dto.UpdateUserDTO;
import com.offer.testjava.model.Users;
import com.offer.testjava.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
@RequestMapping("/user")
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@Valid @RequestBody Users user) {
        return userService.addUser(user);
    }

    @GetMapping("/all")
    public List<Users> getAllUser() {
        return userService.getAllUser();
    }

    @PutMapping("/update/allname")
    public ResponseEntity<String> updateAllName(@Valid @RequestBody UpdateUserDTO updateUser) {
        return userService.updateAllName(updateUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam("email") String email) {
        return userService.deleteUser(email);
    }

    @GetMapping("/by-birthdate-range")
    public ResponseEntity<List<Users>> deleteUser(
            @RequestParam("from") @DateTimeFormat(pattern = "dd-MM-yyyy") Date dateFrom,
            @RequestParam("to") @DateTimeFormat(pattern = "dd-MM-yyyy") Date dateTo
    ) {
        if (dateFrom.after(dateTo)) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }

        List<Users> usersInRange = userService.getUsersByBirthdateRange(dateFrom, dateTo);
        return ResponseEntity.ok(usersInRange);
    }
}
