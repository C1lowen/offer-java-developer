package com.offer.testjava.service;


import com.offer.testjava.dto.CreateUserDTO;
import com.offer.testjava.dto.UpdateUserDTO;
import com.offer.testjava.exception.ApiException;
import com.offer.testjava.exception.EmptyDataException;
import com.offer.testjava.exception.NotFoundException;
import com.offer.testjava.model.User;
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

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${application.age}")
    private Integer ageLimit;

    private final UserRepository userRepository;

    public void addUser(User user) {
        Integer ageUser = getAgeUser(user.getDate());

        if(ageUser < ageLimit) {
            throw new ApiException("You are under 18 years old", HttpStatus.BAD_REQUEST);
        }

        userRepository.addUser(user);
    }

    public void updateAllName(Integer id, UpdateUserDTO updateUserDTO) {
        if((updateUserDTO.getFirstName() == null || updateUserDTO.getFirstName().isEmpty())
                && (updateUserDTO.getLastName() == null || updateUserDTO.getLastName().isEmpty())) {
            throw new EmptyDataException("The data you sent is empty", HttpStatus.BAD_REQUEST);
        }

        Boolean updateUser = userRepository.updateAllName(id, updateUserDTO);

        if(!updateUser) {
            throw new NotFoundException("No such user found", HttpStatus.NOT_FOUND);
        }
    }

    public List<CreateUserDTO> getAllUsers() {
        return userRepository.getAllUsers();
    }


    public void deleteUser(Integer id) {
        Boolean deleteUser = userRepository.deleteUser(id);

        if(!deleteUser) {
            throw new NotFoundException("No such user found", HttpStatus.NOT_FOUND);
        }
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
