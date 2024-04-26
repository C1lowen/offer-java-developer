package com.offer.testjava.repository;

import com.offer.testjava.dto.UpdateUserDTO;
import com.offer.testjava.model.Users;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {

    private final List<Users> users = Collections.synchronizedList(new ArrayList<>());


    public void addUser(Users user) {
        Users updatedUser = findByEmail(user.getEmail())
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

    public Optional<Users> findByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    public Boolean updateAllName(UpdateUserDTO updateUser) {
        return findByEmail(updateUser.getEmail())
                .map(user -> {
                    user.setFirstName(updateUser.getFirstName());
                    user.setLastName(updateUser.getLastName());
                    return true;
                })
                .orElse(false);
    }

    public Boolean deleteUser(String email) {
        return findByEmail(email)
                .map(user -> {
                    users.remove(user);
                    return true;
                })
                .orElse(false);
    }

    public List<Users> findAllUsersByBirthdateRange(Date dateFrom, Date dateTo) {
        return users.stream()
                .filter(user -> user.getDate().after(dateFrom) && dateTo.after(user.getDate()))
                .toList();
    }


    public List<Users> getAllUser() {
        return users;
    }

}
