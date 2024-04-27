package com.offer.testjava.service;

import com.offer.testjava.dto.CreateUserDTO;
import com.offer.testjava.dto.ResponseUserDTO;
import com.offer.testjava.dto.UpdateUserDTO;
import com.offer.testjava.exception.ApiException;
import com.offer.testjava.exception.NotFoundException;
import com.offer.testjava.model.User;
import com.offer.testjava.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userService;


    @Mock
    private UserRepository userRepository;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private final static Integer TEST_ID = 1;

    private final static String TEST_EMAIL = "evgenijobedkov@gmail.com";

    private final static String TEST_FIRSTNAME = "Alex";

    private final static String TEST_LASTNAME = "Alexandro";

    private final static String TEST_PHONE_NUMBER = "+380999999999";

    private final static String TEST_ADDRESS = "address";

    private final static String TEST_DATE = "01-01-2004";

    @BeforeEach
    public void setUp() {
        Integer ageLimit = 18;
        userService = new UserService(ageLimit, userRepository, Clock.fixed(Instant.parse("2024-01-01T18:35:24.00Z"), ZoneId.systemDefault()));
    }


    @Test
    public void testAddUserUnderAgeLimit() throws Exception {

        User userUnderAge = new User();
        userUnderAge.setDate(dateFormat.parse("01-01-2008"));


        ApiException exception = assertThrows(ApiException.class, () -> {
            userService.addUser(userUnderAge);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(UserService.LESS_THAN_18_MESSAGE, exception.getMessage());

        verifyNoInteractions(userRepository);
    }


    @Test
    public void testAddUserAboveAgeLimit() throws Exception {

        User userAboveAge = new User();
        userAboveAge.setDate(dateFormat.parse("01-01-2006"));

        userService.addUser(userAboveAge);

        verify(userRepository).addUser(userAboveAge);
    }


    @Test
    public void testUpdateNames() {
        Integer id = 1;
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setLastName(TEST_LASTNAME);
        updateUserDTO.setFirstName(TEST_FIRSTNAME);

        when(userRepository.findById(id)).thenReturn(Optional.of(new User()));

        userService.updateNames(id, updateUserDTO);

        verify(userRepository).updateNames(id, updateUserDTO);
    }

    @Test
    public void testUpdateNames_whenNotFound() {
        Integer id = 1;
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setLastName(TEST_LASTNAME);
        updateUserDTO.setFirstName(TEST_FIRSTNAME);

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userService.updateNames(id, updateUserDTO);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals(UserService.NOT_FOUND_MESSAGE, exception.getMessage());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        CreateUserDTO userDTO = new CreateUserDTO();
        userDTO.setId(TEST_ID);
        userDTO.setEmail(TEST_EMAIL);
        userDTO.setAddress(TEST_ADDRESS);
        userDTO.setFirstName(TEST_FIRSTNAME);
        userDTO.setLastName(TEST_LASTNAME);
        userDTO.setPhoneNumber(TEST_PHONE_NUMBER);
        userDTO.setDate(dateFormat.parse(TEST_DATE));

        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setDate(userDTO.getDate());

        ResponseUserDTO responseUserDTO = new ResponseUserDTO();
        responseUserDTO.setId(userDTO.getId());
        responseUserDTO.setEmail(userDTO.getEmail());
        responseUserDTO.setAddress(userDTO.getAddress());
        responseUserDTO.setFirstName(userDTO.getFirstName());
        responseUserDTO.setLastName(userDTO.getLastName());
        responseUserDTO.setPhoneNumber(userDTO.getPhoneNumber());
        responseUserDTO.setDate(userDTO.getDate());

        List<User> list = List.of(user);
        List<ResponseUserDTO> actual = List.of(responseUserDTO);

        when(userRepository.getAllUsers()).thenReturn(list);

        List<ResponseUserDTO> expected = userService.getAllUsers();

        assertEquals(actual, expected);
        verify(userRepository).getAllUsers();
    }

    @Test
    public void testDeleteUser() {
        Integer id = 1;
        Optional<User> optionalUser = Optional.of(new User());
        when(userRepository.findById(id)).thenReturn(Optional.of(new User()));

        userService.deleteUser(id);

        verify(userRepository).deleteUser(optionalUser.get());
    }

    @Test
    public void testDeleteUser_whenNotFound() {
        Integer id = 1;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userService.deleteUser(id);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals(UserService.NOT_FOUND_MESSAGE, exception.getMessage());
    }

    @Test
    public void testGetUsersByBirthdateRange() throws Exception {
        Date dateFrom = dateFormat.parse("01-01-2000");
        Date dateTo = dateFormat.parse("01-01-2006");

        CreateUserDTO userDTO = new CreateUserDTO();
        userDTO.setId(TEST_ID);
        userDTO.setEmail(TEST_EMAIL);
        userDTO.setAddress(TEST_ADDRESS);
        userDTO.setFirstName(TEST_FIRSTNAME);
        userDTO.setLastName(TEST_LASTNAME);
        userDTO.setPhoneNumber(TEST_PHONE_NUMBER);
        userDTO.setDate(dateFormat.parse(TEST_DATE));

        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setDate(userDTO.getDate());

        ResponseUserDTO responseUserDTO = new ResponseUserDTO();
        responseUserDTO.setId(userDTO.getId());
        responseUserDTO.setEmail(userDTO.getEmail());
        responseUserDTO.setAddress(userDTO.getAddress());
        responseUserDTO.setFirstName(userDTO.getFirstName());
        responseUserDTO.setLastName(userDTO.getLastName());
        responseUserDTO.setPhoneNumber(userDTO.getPhoneNumber());
        responseUserDTO.setDate(userDTO.getDate());

        List<User> list = List.of(user);
        List<ResponseUserDTO> actual = List.of(responseUserDTO);

        when(userRepository.findAllUsersByBirthdateRange(dateFrom, dateTo)).thenReturn(list);

        List<ResponseUserDTO> expected = userService.getUsersByBirthdateRange(dateFrom, dateTo);


        assertEquals(actual, expected);
        verify(userRepository).findAllUsersByBirthdateRange(dateFrom, dateTo);
    }


}
