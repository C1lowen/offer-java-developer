package com.offer.testjava.controller;


import com.offer.testjava.dto.CreateUserDTO;
import com.offer.testjava.dto.UpdateUserDTO;
import com.offer.testjava.exception.ValidationException;
import com.offer.testjava.mapper.UserMapper;
import com.offer.testjava.model.User;
import com.offer.testjava.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping
    public void addUser(@Valid @RequestBody CreateUserDTO user) {
        User userMap = UserMapper.mapFromDto(user);
        userService.addUser(userMap);
    }

    @GetMapping
    public List<CreateUserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public void updateNames(
            @PathVariable("id") Integer id,
            @RequestBody UpdateUserDTO updateUser) {

        if(!StringUtils.hasText(updateUser.getFirstName()) && !StringUtils.hasText(updateUser.getLastName())) {
            throw new ValidationException("The data you sent is empty");
        }

        userService.updateNames(id, updateUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(
            @PathVariable("id") Integer id
    ) {
        userService.deleteUser(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CreateUserDTO>> searchByDate(
            @RequestParam("from") @DateTimeFormat(pattern = "dd-MM-yyyy") Date dateFrom,
            @RequestParam("to") @DateTimeFormat(pattern = "dd-MM-yyyy") Date dateTo
    ) {
        if (dateFrom.after(dateTo)) {
            throw new ValidationException("Date 'from' is greater than date 'to'");
        }

        List<CreateUserDTO> usersInRange = userService.getUsersByBirthdateRange(dateFrom, dateTo);
        return ResponseEntity.ok(usersInRange);
    }
}
