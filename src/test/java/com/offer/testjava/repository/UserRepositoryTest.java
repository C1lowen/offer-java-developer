package com.offer.testjava.repository;

import com.offer.testjava.dto.UpdateUserDTO;
import com.offer.testjava.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRepositoryTest {

    private UserRepository userRepository;

    private List<User> users;

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
        users = new ArrayList<>();
        userRepository = new UserRepository(users);
    }

    @Test
    public void testAddUser() throws Exception {
        users.clear();
        User user = new User();
        user.setId(TEST_ID);
        user.setEmail(TEST_EMAIL);
        user.setAddress(TEST_ADDRESS);
        user.setFirstName(TEST_FIRSTNAME);
        user.setLastName(TEST_LASTNAME);
        user.setPhoneNumber(TEST_PHONE_NUMBER);
        user.setDate(dateFormat.parse(TEST_DATE));

        userRepository.addUser(user);

        assertEquals(1, users.size());
        assertEquals(user, users.get(0));
    }

    @Test
    public void testAddUser_whenUpdateAllField() throws Exception {
        users.clear();

        User user = new User();
        user.setId(TEST_ID);
        user.setEmail(TEST_EMAIL);
        user.setAddress(TEST_ADDRESS);
        user.setFirstName(TEST_FIRSTNAME);
        user.setLastName(TEST_LASTNAME);
        user.setPhoneNumber(TEST_PHONE_NUMBER);
        user.setDate(dateFormat.parse(TEST_DATE));

        userRepository.addUser(user);

        User user2 = new User();
        user2.setId(TEST_ID);
        user2.setEmail("evgenijobedkov@gmail.com2");
        user2.setAddress("testAddress2");
        user2.setFirstName("testFirstName2");
        user2.setLastName("testLastName2");
        user2.setPhoneNumber("testPhoneNumber2");
        user2.setDate(new Date(2000001L));

        userRepository.addUser(user2);

        assertEquals(1, users.size());
        assertEquals(user2, users.get(0));
    }

    @Test
    public void testFindById() throws Exception {
        users.clear();

        Integer id = 1;

        User expected = new User();
        expected.setId(id);
        expected.setEmail(TEST_EMAIL);
        expected.setAddress(TEST_ADDRESS);
        expected.setFirstName(TEST_FIRSTNAME);
        expected.setLastName(TEST_LASTNAME);
        expected.setPhoneNumber(TEST_PHONE_NUMBER);
        expected.setDate(dateFormat.parse(TEST_DATE));

        users.add(expected);

        Optional<User> actualOptional = userRepository.findById(id);

        assertTrue(actualOptional.isPresent());
        User actual = actualOptional.get();
        assertEquals(expected, actual);
        assertTrue(users.contains(actual));
    }

    @Test
    public void testUpdateNames() throws Exception {
        users.clear();

        Integer id = 1;

        User user = new User();
        user.setId(id);
        user.setEmail(TEST_EMAIL);
        user.setAddress(TEST_ADDRESS);
        user.setFirstName(TEST_FIRSTNAME);
        user.setLastName(TEST_LASTNAME);
        user.setPhoneNumber(TEST_PHONE_NUMBER);
        user.setDate(dateFormat.parse(TEST_DATE));

        users.add(user);

        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setFirstName("testUpdateName");
        updateUserDTO.setLastName("testUpdateLastName");

        userRepository.updateNames(id, updateUserDTO);

        User expected = new User();
        expected.setId(user.getId());
        expected.setEmail(user.getEmail());
        expected.setAddress(user.getAddress());
        expected.setFirstName(updateUserDTO.getFirstName());
        expected.setLastName(updateUserDTO.getLastName());
        expected.setPhoneNumber(user.getPhoneNumber());
        expected.setDate(user.getDate());


        assertEquals(1, users.size());
        assertEquals(expected, users.get(0));
    }

    @Test
    public void testUpdateNames_whenFirstNameNullAndLastNameEmpty() throws Exception {
        users.clear();

        Integer id = 1;

        User user = new User();
        user.setId(id);
        user.setEmail(TEST_EMAIL);
        user.setAddress(TEST_ADDRESS);
        user.setFirstName(TEST_FIRSTNAME);
        user.setLastName(TEST_LASTNAME);
        user.setPhoneNumber(TEST_PHONE_NUMBER);
        user.setDate(dateFormat.parse(TEST_DATE));

        userRepository.addUser(user);

        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setFirstName(null);
        updateUserDTO.setLastName("");

        userRepository.updateNames(id, updateUserDTO);


        assertEquals(1, users.size());
        assertEquals(user, users.get(0));
    }


    @Test
    public void testDeleteUser() throws Exception {
        users.clear();

        User user1 = new User();
        user1.setId(1);
        user1.setEmail(TEST_EMAIL);
        user1.setAddress(TEST_ADDRESS);
        user1.setFirstName(TEST_FIRSTNAME);
        user1.setLastName(TEST_LASTNAME);
        user1.setPhoneNumber(TEST_PHONE_NUMBER);
        user1.setDate(dateFormat.parse(TEST_DATE));
        users.add(user1);

        User user2 = new User();
        user2.setId(2);
        user2.setEmail(TEST_EMAIL);
        user2.setAddress(TEST_ADDRESS);
        user2.setFirstName(TEST_FIRSTNAME);
        user2.setLastName(TEST_LASTNAME);
        user2.setPhoneNumber(TEST_PHONE_NUMBER);
        user2.setDate(dateFormat.parse(TEST_DATE));

        users.add(user2);

        userRepository.deleteUser(user1);

        assertEquals(1, users.size());
        assertEquals(user2, users.get(0));
    }

    @Test
    public void testFindAllUsersByBirthdateRange() throws Exception {
        users.clear();

        User user1 = new User();
        user1.setId(1);
        user1.setEmail(TEST_EMAIL);
        user1.setAddress(TEST_ADDRESS);
        user1.setFirstName(TEST_FIRSTNAME);
        user1.setLastName(TEST_LASTNAME);
        user1.setPhoneNumber(TEST_PHONE_NUMBER);
        user1.setDate(dateFormat.parse("01-01-1993"));

        users.add(user1);

        User user2 = new User();
        user2.setId(2);
        user2.setEmail(TEST_EMAIL);
        user2.setAddress(TEST_ADDRESS);
        user2.setFirstName(TEST_FIRSTNAME);
        user2.setLastName(TEST_LASTNAME);
        user2.setPhoneNumber(TEST_PHONE_NUMBER);
        user2.setDate(dateFormat.parse("01-01-2004"));

        users.add(user2);

        Date dateFrom = dateFormat.parse("01-01-2000");
        Date dateTo = dateFormat.parse("01-01-2005");

        List<User> expected = userRepository.findAllUsersByBirthdateRange(dateFrom, dateTo);

        assertEquals(1, expected.size());
        assertEquals(user2, expected.get(0));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        users.clear();

        User user1 = new User();
        user1.setId(1);
        user1.setEmail(TEST_EMAIL);
        user1.setAddress(TEST_ADDRESS);
        user1.setFirstName(TEST_FIRSTNAME);
        user1.setLastName(TEST_LASTNAME);
        user1.setPhoneNumber(TEST_PHONE_NUMBER);
        user1.setDate(dateFormat.parse("01-01-1993"));

        users.add(user1);

        User user2 = new User();
        user2.setId(2);
        user2.setEmail(TEST_EMAIL);
        user2.setAddress(TEST_ADDRESS);
        user2.setFirstName(TEST_FIRSTNAME);
        user2.setLastName(TEST_LASTNAME);
        user2.setPhoneNumber(TEST_PHONE_NUMBER);
        user2.setDate(dateFormat.parse("01-01-2004"));

        users.add(user2);


        assertEquals(2, users.size());
        assertEquals(List.of(user1, user2), users);
    }
}
