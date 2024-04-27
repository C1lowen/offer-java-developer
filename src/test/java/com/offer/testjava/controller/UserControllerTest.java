package com.offer.testjava.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.offer.testjava.dto.CreateUserDTO;
import com.offer.testjava.dto.UpdateUserDTO;
import com.offer.testjava.exception.NotFoundException;
import com.offer.testjava.exception.ValidationException;
import com.offer.testjava.model.User;
import com.offer.testjava.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.hamcrest.Matchers.is;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.mock.web.MockHttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;


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


    @Test
    public void testAddUser() throws Exception {
        CreateUserDTO userDTO = new CreateUserDTO();
        userDTO.setId(1);
        userDTO.setEmail("evgenijobedkov@gmail.com");
        userDTO.setAddress("testAddress");
        userDTO.setFirstName("testFirstName");
        userDTO.setLastName("testLastName");
        userDTO.setPhoneNumber("testPhoneNumber");
        userDTO.setDate(new Date(2000000L));


        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddUser_whenNotAllFields() throws Exception {
        CreateUserDTO userDTO = new CreateUserDTO();
        userDTO.setId(1);
        userDTO.setEmail("evgenijobedkov@gmail.com");

        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllUsers() throws Exception{
        CreateUserDTO userDTO = new CreateUserDTO();
        userDTO.setId(1);
        userDTO.setEmail("evgenijobedkov@gmail.com");
        userDTO.setAddress("testAddress");
        userDTO.setFirstName("testFirstName");
        userDTO.setLastName("testLastName");
        userDTO.setPhoneNumber("testPhoneNumber");
        userDTO.setDate(new Date(2000000L));

        when(userService.getAllUsers()).thenReturn(List.of(userDTO));

        mockMvc.perform(get("/users")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].email", is("evgenijobedkov@gmail.com")))
                .andExpect(jsonPath("$[0].address", is("testAddress")))
                .andExpect(jsonPath("$[0].firstName", is("testFirstName")))
                .andExpect(jsonPath("$[0].lastName", is("testLastName")))
                .andExpect(jsonPath("$[0].phoneNumber", is("testPhoneNumber")));
    }

    @Test
    public void testUpdateNames() throws Exception{
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setFirstName("testFirstName");
        updateUserDTO.setLastName("testLastName");

        Integer id = 1;

        mockMvc.perform(put("/users/{id}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updateUserDTO)))
                .andExpect(status().isOk());
    }


    @Test
    public void testUpdateNames_whenDataNull() throws Exception{
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
    public void testDeleteUser() throws Exception{
        Integer id = 1;

        mockMvc.perform(delete("/users/{id}", id)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchByDate() throws Exception {
        CreateUserDTO userDTO = new CreateUserDTO();
        userDTO.setId(1);
        userDTO.setEmail("evgenijobedkov@gmail.com");
        userDTO.setAddress("testAddress");
        userDTO.setFirstName("testFirstName");
        userDTO.setLastName("testLastName");
        userDTO.setPhoneNumber("testPhoneNumber");
        userDTO.setDate(new Date(2000000L));

        String from = "01-01-1990";
        String to = "01-01-2000";


        when(userService.getUsersByBirthdateRange(any(Date.class), any(Date.class))).thenReturn(List.of(userDTO));

        mockMvc.perform(get("/users/search")
                        .param("from", from)
                        .param("to", to)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].email", is("evgenijobedkov@gmail.com")))
                .andExpect(jsonPath("$[0].address", is("testAddress")))
                .andExpect(jsonPath("$[0].firstName", is("testFirstName")))
                .andExpect(jsonPath("$[0].lastName", is("testLastName")))
                .andExpect(jsonPath("$[0].phoneNumber", is("testPhoneNumber")));
    }

    @Test
    public void testSearchByDate_whenDateFromAfterDateTo() throws Exception {
        CreateUserDTO userDTO = new CreateUserDTO();
        userDTO.setId(1);
        userDTO.setEmail("evgenijobedkov@gmail.com");
        userDTO.setAddress("testAddress");
        userDTO.setFirstName("testFirstName");
        userDTO.setLastName("testLastName");
        userDTO.setPhoneNumber("testPhoneNumber");
        userDTO.setDate(new Date(2000000L));

        String from = "01-01-2001";
        String to = "01-01-2000";


        when(userService.getUsersByBirthdateRange(any(Date.class), any(Date.class))).thenReturn(List.of(userDTO));

        MockHttpServletResponse response = mockMvc.perform(get("/users/search")
                        .param("from", from)
                        .param("to", to)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        String responseBody = response.getContentAsString();
        assertTrue(responseBody.contains("Date 'from' is greater than date 'to'"));
    }




}
