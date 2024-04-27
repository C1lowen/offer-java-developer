package com.offer.testjava.mapper;

import com.offer.testjava.dto.CreateUserDTO;
import com.offer.testjava.dto.ResponseUserDTO;
import com.offer.testjava.model.User;
import org.modelmapper.ModelMapper;

public class UserMapper {
    private static final ModelMapper mapper = new ModelMapper();

    public static User mapFromDto(CreateUserDTO dto) {
        return mapper.map(dto, User.class);
    }

    public static CreateUserDTO mapToDto(User user) {
        return mapper.map(user, CreateUserDTO.class);
    }

    public static ResponseUserDTO mapToResponseDto(User user) {
        return  mapper.map(user, ResponseUserDTO.class);
    }
}
