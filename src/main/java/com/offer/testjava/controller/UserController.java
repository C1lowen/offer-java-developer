package com.offer.testjava.controller;


import com.offer.testjava.dto.CreateUserDTO;
import com.offer.testjava.dto.ResponseUserDTO;
import com.offer.testjava.dto.UpdateUserDTO;
import com.offer.testjava.exception.ValidationException;
import com.offer.testjava.mapper.UserMapper;
import com.offer.testjava.model.User;
import com.offer.testjava.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;

    public final static String DATA_EMPTY_MESSAGE = "The data you sent is empty";

    public final static String OUT_OF_RANGE_MESSAGE = "Date 'from' is greater than date 'to'";

    @PostMapping
    public void addUser(@Valid @RequestBody CreateUserDTO user) {
        User userMap = UserMapper.mapFromDto(user);
        userService.addUser(userMap);
    }

    @GetMapping
    public List<ResponseUserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public void updateNames(
            @PathVariable("id") Integer id,
            @RequestBody UpdateUserDTO updateUser) {

        if (!hasText(updateUser.getFirstName()) && !hasText(updateUser.getLastName())) {
            throw new ValidationException(DATA_EMPTY_MESSAGE);
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
    public ResponseEntity<List<ResponseUserDTO>> searchByDate(
            @RequestParam("from") @DateTimeFormat(pattern = "dd-MM-yyyy") Date dateFrom,
            @RequestParam("to") @DateTimeFormat(pattern = "dd-MM-yyyy") Date dateTo
    ) {
        if (dateFrom.after(dateTo)) {
            throw new ValidationException(OUT_OF_RANGE_MESSAGE);
        }

        List<ResponseUserDTO> usersInRange = userService.getUsersByBirthdateRange(dateFrom, dateTo);
        return ResponseEntity.ok(usersInRange);
    }
}
