package com.offer.testjava.repository;

import com.offer.testjava.dto.CreateUserDTO;
import com.offer.testjava.dto.UpdateUserDTO;
import com.offer.testjava.mapper.UserMapper;
import com.offer.testjava.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {

    private final List<User> users = Collections.synchronizedList(new ArrayList<>());


    public void addUser(User user) {
        User updatedUser = findById(user.getId())
                .map(currentUser -> {
                    currentUser.setDate(user.getDate());
                    currentUser.setFirstName(user.getFirstName());
                    currentUser.setLastName(user.getLastName());
                    currentUser.setAddress(user.getAddress());
                    currentUser.setPhoneNumber(user.getPhoneNumber());
                    return currentUser;
                })
                .orElse(null);

        if(updatedUser == null) {
            users.add(user);
        }
    }

    public Optional<User> findById(Integer id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public void updateNames(Integer id, UpdateUserDTO updateUser) {
        findById(id)
                .map(user -> {
                    if (updateUser.getFirstName() != null && !updateUser.getFirstName().isEmpty()) {
                        user.setFirstName(updateUser.getFirstName());
                    }
                    if (updateUser.getLastName() != null && !updateUser.getLastName().isEmpty()) {
                        user.setLastName(updateUser.getLastName());
                    }
                    return true;
                });
    }

    public void deleteUser(User user) {
        users.remove(user);
    }

    public List<CreateUserDTO> findAllUsersByBirthdateRange(Date dateFrom, Date dateTo) {
        return users.stream()
                .filter(user -> user.getDate().after(dateFrom) && dateTo.after(user.getDate()))
                .map(UserMapper::mapToDto)
                .toList();
    }


    public List<CreateUserDTO> getAllUsers() {
        return users.stream()
                .map(UserMapper::mapToDto)
                .toList();
    }

}
