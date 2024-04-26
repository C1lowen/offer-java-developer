package com.offer.testjava.service;


import com.offer.testjava.dto.CreateUserDTO;
import com.offer.testjava.dto.UpdateUserDTO;
import com.offer.testjava.exception.ApiException;
import com.offer.testjava.exception.NotFoundException;
import com.offer.testjava.model.User;
import com.offer.testjava.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${user.age}")
    private Integer ageLimit;

    private final UserRepository userRepository;

    public void addUser(User user) {
        Integer ageUser = getAgeUser(user.getDate());

        if(ageUser < ageLimit) {
            throw new ApiException("You are under 18 years old", HttpStatus.BAD_REQUEST);
        }

        userRepository.addUser(user);
    }

    public void updateNames(Integer id, UpdateUserDTO updateUserDTO) {
        if(userRepository.findById(id).isEmpty()) {
            throw new NotFoundException("No such user found");
        }

        userRepository.updateNames(id, updateUserDTO);

    }

    public List<CreateUserDTO> getAllUsers() {
        return userRepository.getAllUsers();
    }


    public void deleteUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new NotFoundException("No such user found");
        }

       userRepository.deleteUser(user.get());
    }

    public List<CreateUserDTO> getUsersByBirthdateRange(Date dateFrom, Date dateTo) {
        return userRepository.findAllUsersByBirthdateRange(dateFrom, dateTo);
    }

    private Integer getAgeUser(Date birthDate) {
        LocalDate birthLocalDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthLocalDate, currentDate);

        return period.getYears();
    }

}
