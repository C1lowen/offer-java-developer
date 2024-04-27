package com.offer.testjava.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.offer.testjava.dto.CreateUserDTO;
import com.offer.testjava.dto.ResponseUserDTO;
import com.offer.testjava.dto.UpdateUserDTO;
import com.offer.testjava.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private final static Integer TEST_ID = 1;

    private final static String TEST_EMAIL = "evgenijobedkov@gmail.com";

    private final static String TEST_FIRSTNAME = "Alex";

    private final static String TEST_LASTNAME = "Alexandro";

    private final static String TEST_PHONE_NUMBER = "+380999999999";

    private final static String TEST_ADDRESS = "address";

    private final static String TEST_DATE = "01-01-2004";


    @Test
    public void testAddUser() throws Exception {
        CreateUserDTO userDTO = new CreateUserDTO();
        userDTO.setId(TEST_ID);
        userDTO.setEmail(TEST_EMAIL);
        userDTO.setAddress(TEST_ADDRESS);
        userDTO.setFirstName(TEST_FIRSTNAME);
        userDTO.setLastName(TEST_LASTNAME);
        userDTO.setPhoneNumber(TEST_PHONE_NUMBER);
        userDTO.setDate(dateFormat.parse(TEST_DATE));


        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddUser_whenNotAllFields() throws Exception {
        CreateUserDTO userDTO = new CreateUserDTO();
        userDTO.setId(TEST_ID);
        userDTO.setEmail(TEST_EMAIL);

        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        ResponseUserDTO userDTO = new ResponseUserDTO();
        userDTO.setId(TEST_ID);
        userDTO.setEmail(TEST_EMAIL);
        userDTO.setAddress(TEST_ADDRESS);
        userDTO.setFirstName(TEST_FIRSTNAME);
        userDTO.setLastName(TEST_LASTNAME);
        userDTO.setPhoneNumber(TEST_PHONE_NUMBER);
        userDTO.setDate(dateFormat.parse(TEST_DATE));

        when(userService.getAllUsers()).thenReturn(List.of(userDTO));

        mockMvc.perform(get("/users")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(TEST_ID)))
                .andExpect(jsonPath("$[0].email", is(TEST_EMAIL)))
                .andExpect(jsonPath("$[0].address", is(TEST_ADDRESS)))
                .andExpect(jsonPath("$[0].firstName", is(TEST_FIRSTNAME)))
                .andExpect(jsonPath("$[0].lastName", is(TEST_LASTNAME)))
                .andExpect(jsonPath("$[0].phoneNumber", is(TEST_PHONE_NUMBER)));
    }

    @Test
    public void testUpdateNames() throws Exception {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setFirstName(TEST_FIRSTNAME);
        updateUserDTO.setLastName(TEST_LASTNAME);

        Integer id = 1;

        mockMvc.perform(put("/users/{id}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updateUserDTO)))
                .andExpect(status().isOk());

        verify(userService).updateNames(id, updateUserDTO);
    }


    @Test
    public void testUpdateNames_whenFirstNameNullAndLastNameEmpty() throws Exception {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setFirstName(null);
        updateUserDTO.setLastName("");

        Integer id = 1;

        mockMvc.perform(put("/users/{id}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updateUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateNames_whenDataNull() throws Exception {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setFirstName(null);
        updateUserDTO.setLastName(null);

        Integer id = 1;


        mockMvc.perform(put("/users/{id}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updateUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateNames_whenDataEmpty() throws Exception {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setFirstName("");
        updateUserDTO.setLastName("");

        Integer id = 1;


        mockMvc.perform(put("/users/{id}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updateUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteUser() throws Exception {
        Integer id = 1;

        mockMvc.perform(delete("/users/{id}", id)
                        .contentType("application/json"))
                .andExpect(status().isOk());

        verify(userService).deleteUser(id);
    }

    @Test
    public void testSearchByDate() throws Exception {
        ResponseUserDTO userDTO = new ResponseUserDTO();
        userDTO.setId(TEST_ID);
        userDTO.setEmail(TEST_EMAIL);
        userDTO.setAddress(TEST_ADDRESS);
        userDTO.setFirstName(TEST_FIRSTNAME);
        userDTO.setLastName(TEST_LASTNAME);
        userDTO.setPhoneNumber(TEST_PHONE_NUMBER);
        userDTO.setDate(dateFormat.parse(TEST_DATE));

        String from = "01-01-1990";
        String to = "01-01-2000";


        when(userService.getUsersByBirthdateRange(dateFormat.parse(from), dateFormat.parse(to))).thenReturn(List.of(userDTO));

        mockMvc.perform(get("/users/search")
                        .param("from", from)
                        .param("to", to)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(TEST_ID)))
                .andExpect(jsonPath("$[0].email", is(TEST_EMAIL)))
                .andExpect(jsonPath("$[0].address", is(TEST_ADDRESS)))
                .andExpect(jsonPath("$[0].firstName", is(TEST_FIRSTNAME)))
                .andExpect(jsonPath("$[0].lastName", is(TEST_LASTNAME)))
                .andExpect(jsonPath("$[0].phoneNumber", is(TEST_PHONE_NUMBER)));
    }

    @Test
    public void testSearchByDate_whenDateFromAfterDateTo() throws Exception {
        String from = "01-01-2001";
        String to = "01-01-2000";


        MockHttpServletResponse response = mockMvc.perform(get("/users/search")
                        .param("from", from)
                        .param("to", to)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        String responseBody = response.getContentAsString();
        assertTrue(responseBody.contains(UserController.OUT_OF_RANGE_MESSAGE));
    }


}
