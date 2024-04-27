package com.offer.testjava.service;

import com.offer.testjava.dto.CreateUserDTO;
import com.offer.testjava.dto.UpdateUserDTO;
import com.offer.testjava.exception.ApiException;
import com.offer.testjava.exception.NotFoundException;
import com.offer.testjava.model.User;
import com.offer.testjava.repository.UserRepository;
import net.bytebuddy.description.field.FieldDescription;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;


    @Mock
    private UserRepository userRepository;


    @Test
    public void testAddUserUnderAgeLimit() {
        User userUnderAge = new User();
        userUnderAge.setDate(new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 365 * 17));


        ApiException exception = assertThrows(ApiException.class, () -> {
            userService.addUser(userUnderAge);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("You are under 18 years old", exception.getMessage());
    }


    @Test
    public void testAddUserAboveAgeLimit() {

        User userAboveAge = new User();
        userAboveAge.setDate(new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 365 * 20));

        userService.addUser(userAboveAge);

        verify(userRepository).addUser(userAboveAge);
    }


    @Test
    public void testUpdateNames() {
        Integer id = 1;
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setLastName("testLastName");
        updateUserDTO.setFirstName("testFirstName");

        when(userRepository.findById(id)).thenReturn(Optional.of(new User()));

        userService.updateNames(id, updateUserDTO);

        verify(userRepository).updateNames(id, updateUserDTO);
        verify(userRepository, times(1)).updateNames(id, updateUserDTO);
    }

    @Test
    public void testUpdateNames_whenNotFound() {
        Integer id = 1;
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setLastName("testLastName");
        updateUserDTO.setFirstName("testFirstName");

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userService.updateNames(id, updateUserDTO);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("No such user found", exception.getMessage());
    }

    @Test
    public void testGetAllUsers() {
        CreateUserDTO userDTO = new CreateUserDTO();
        userDTO.setId(1);
        userDTO.setEmail("evgenijobedkov@gmail.com");
        userDTO.setAddress("testAddress");
        userDTO.setFirstName("testFirstName");
        userDTO.setLastName("testLastName");
        userDTO.setPhoneNumber("testPhoneNumber");
        userDTO.setDate(new Date(2000000L));

        List<CreateUserDTO> actual = List.of(userDTO);

        when(userRepository.getAllUsers()).thenReturn(actual);

        List<CreateUserDTO> expected = userService.getAllUsers();

        assertEquals(actual, expected);
        verify(userRepository, times(1)).getAllUsers();
    }

    @Test
    public void testDeleteUser() {
        Integer id = 1;
        Optional<User> optionalUser = Optional.of(new User());
        when(userRepository.findById(id)).thenReturn(Optional.of(new User()));

        userService.deleteUser(id);

        verify(userRepository).deleteUser(optionalUser.get());
        verify(userRepository, times(1)).deleteUser(optionalUser.get());
    }

    @Test
    public void testDeleteUser_whenNotFound() {
        Integer id = 1;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userService.deleteUser(id);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("No such user found", exception.getMessage());
    }

    @Test
    public void testGetUsersByBirthdateRange() throws Exception{
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Date dateFrom = dateFormat.parse("01-01-2000");
        Date dateTo = dateFormat.parse("01-01-2006");

        CreateUserDTO userDTO = new CreateUserDTO();
        userDTO.setId(1);
        userDTO.setEmail("evgenijobedkov@gmail.com");
        userDTO.setAddress("testAddress");
        userDTO.setFirstName("testFirstName");
        userDTO.setLastName("testLastName");
        userDTO.setPhoneNumber("testPhoneNumber");
        userDTO.setDate(dateFormat.parse("01-01-2004"));

        List<CreateUserDTO> actual = List.of(new CreateUserDTO(), userDTO);

        when(userRepository.findAllUsersByBirthdateRange(dateFrom, dateTo)).thenReturn(actual);

        List<CreateUserDTO> expected = userService.getUsersByBirthdateRange(dateFrom, dateTo);


        assertEquals(actual, expected);
        verify(userRepository, times(1)).findAllUsersByBirthdateRange(dateFrom, dateTo);

    }


}
