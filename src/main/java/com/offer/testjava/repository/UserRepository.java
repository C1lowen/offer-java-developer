package com.offer.testjava.repository;

import com.offer.testjava.dto.UpdateUserDTO;
import com.offer.testjava.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final List<User> users;

    public void addUser(User user) {
        User updatedUser = findById(user.getId())
                .map(currentUser -> {
                    currentUser.setDate(user.getDate());
                    currentUser.setFirstName(user.getFirstName());
                    currentUser.setEmail(user.getEmail());
                    currentUser.setLastName(user.getLastName());
                    currentUser.setAddress(user.getAddress());
                    currentUser.setPhoneNumber(user.getPhoneNumber());
                    return currentUser;
                })
                .orElse(null);

        if (updatedUser == null) {
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
                    if (hasText(updateUser.getFirstName())) {
                        user.setFirstName(updateUser.getFirstName());
                    }
                    if (hasText(updateUser.getLastName())) {
                        user.setLastName(updateUser.getLastName());
                    }
                    return true;
                });
    }

    public void deleteUser(User user) {
        users.remove(user);
    }

    public List<User> findAllUsersByBirthdateRange(Date dateFrom, Date dateTo) {
        return users.stream()
                .filter(user -> user.getDate().after(dateFrom) && dateTo.after(user.getDate()))
                .toList();
    }


    public List<User> getAllUsers() {
        return users.stream()
                .toList();
    }
}
