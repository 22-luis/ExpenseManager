package org.example.expensemanager.services;

import org.apache.coyote.BadRequestException;
import org.example.expensemanager.entities.User;
import org.example.expensemanager.entities.dto.user.UserDto;

import java.util.UUID;

public interface UserService {

    UserDto createUser(UserDto userDto) throws BadRequestException;
    UserDto getUserById(UUID id);
    UserDto getUserByEmail(String email);
    UserDto convertToDto(User user);
    UserDto updateUser(UUID id, UserDto userDto);
    void deleteUser(UUID id);

}
