package com.offer.testjava.service;


import com.offer.testjava.dto.UpdateUserDTO;
import com.offer.testjava.model.Users;
import com.offer.testjava.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${application.age}")
    private Integer ageLimit;

    private final UserRepository userRepository;

    public ResponseEntity<String> addUser(Users user) {
        Integer ageUser = getAgeUser(user.getDate());

        if(ageUser < ageLimit) {
            return ResponseEntity.badRequest().body("You are under 18 years old!");
        }

        userRepository.addUser(user);

        return ResponseEntity.ok("Ok");
    }

    public ResponseEntity<String> updateAllName(UpdateUserDTO updateUserDTO) {
        Boolean updateUser = userRepository.updateAllName(updateUserDTO);

        return updateUser ? ResponseEntity.ok("User update") : ResponseEntity.badRequest().body("No such user found");
    }

    public List<Users> getAllUser() {
        return userRepository.getAllUser();
    }

    public ResponseEntity<String> deleteUser(String email) {
        Boolean deleteUser = userRepository.deleteUser(email);

        return deleteUser ? ResponseEntity.ok("User delete") : ResponseEntity.badRequest().body("No such user found");
    }

    public List<Users> getUsersByBirthdateRange(Date dateFrom, Date dateTo) {
        return userRepository.findAllUsersByBirthdateRange(dateFrom, dateTo);
    }

    private Integer getAgeUser(Date birthDate) {
        LocalDate birthLocalDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthLocalDate, currentDate);

        return period.getYears();
    }

}
