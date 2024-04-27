package com.offer.testjava.repository;

import com.offer.testjava.dto.CreateUserDTO;
import com.offer.testjava.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class UserRepositoryTest {

    private UserRepository userRepository;

    private List<User> users;

    @BeforeEach
    public void setUp() {
        users = Collections.synchronizedList(new ArrayList<>());

        User user1 = new User();
        user1.setId(1);
        user1.setEmail("evgenijobedkov@gmail.com");
        user1.setAddress("testAddress");
        user1.setFirstName("testFirstName");
        user1.setLastName("testLastName");
        user1.setPhoneNumber("testPhoneNumber");
        user1.setDate(new Date(2000000L));

        User user2 = new User();
        user2.setId(2);
        user2.setEmail("evgenijobedkov@gmail.com");
        user2.setAddress("testAddress");
        user2.setFirstName("testFirstName");
        user2.setLastName("testLastName");
        user2.setPhoneNumber("testPhoneNumber");
        user2.setDate(new Date(2000000L));

        userRepository.addUser(user1);
        userRepository.addUser(user2);
    }

//    @Test
//    public void testAddUser() {
//        User user3 = new User();
//        user3.setId(3);
//        user3.setEmail("evgenijobedkov@gmail.com");
//        user3.setAddress("testAddress");
//        user3.setFirstName("testFirstName");
//        user3.setLastName("testLastName");
//        user3.setPhoneNumber("testPhoneNumber");
//        user3.setDate(new Date(2000000L));
//
//        userRepository.addUser(user3);
//
//        List<User> users = userRepository.getAllUsers();
//        assertEquals(3, users.size());
//        assertEquals(user3, users.get(2));
//    }
}
