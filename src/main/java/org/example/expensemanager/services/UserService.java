package org.example.expensemanager.services;

import org.apache.coyote.BadRequestException;
import org.example.expensemanager.models.User;
import org.example.expensemanager.models.dto.user.CreateUserRequestDto;
import org.example.expensemanager.models.dto.user.UpdateUserRequestDto;
import org.example.expensemanager.models.dto.user.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDto convertToDto(User user);

    UserDto createUser(CreateUserRequestDto userDto) throws BadRequestException;

    List<UserDto> getAll();
    UserDto getUserById(UUID id);
    UserDto getUserByEmail(String email);

    UserDto updateUser(UUID id, UpdateUserRequestDto userDto);

    void deleteUser(UUID id);

}
