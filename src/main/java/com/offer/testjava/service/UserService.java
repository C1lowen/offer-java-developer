package com.offer.testjava.service;


import com.offer.testjava.dto.ResponseUserDTO;
import com.offer.testjava.dto.UpdateUserDTO;
import com.offer.testjava.exception.ApiException;
import com.offer.testjava.exception.NotFoundException;
import com.offer.testjava.mapper.UserMapper;
import com.offer.testjava.model.User;
import com.offer.testjava.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Clock;
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
    private final Integer ageLimit;

    private final UserRepository userRepository;

    private final Clock clock;

    public final static String NOT_FOUND_MESSAGE = "No such user found";

    public final static String LESS_THAN_18_MESSAGE = "You are under 18 years old";

    public void addUser(User user) {
        Integer ageUser = getAgeUser(user.getDate());

        if (ageUser < ageLimit) {
            throw new ApiException(LESS_THAN_18_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        userRepository.addUser(user);
    }

    public void updateNames(Integer id, UpdateUserDTO updateUserDTO) {
        if (userRepository.findById(id).isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }

        userRepository.updateNames(id, updateUserDTO);

    }

    public List<ResponseUserDTO> getAllUsers() {
        return userRepository.getAllUsers().stream()
                .map(UserMapper::mapToResponseDto)
                .toList();
    }


    public void deleteUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }

        userRepository.deleteUser(user.get());
    }

    public List<ResponseUserDTO> getUsersByBirthdateRange(Date dateFrom, Date dateTo) {
        return userRepository.findAllUsersByBirthdateRange(dateFrom, dateTo).stream()
                .map(UserMapper::mapToResponseDto)
                .toList();
    }

    private Integer getAgeUser(Date birthDate) {
        LocalDate birthLocalDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate currentDate = LocalDate.now(clock);
        Period period = Period.between(birthLocalDate, currentDate);

        return period.getYears();
    }

}
