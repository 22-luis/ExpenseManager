package org.example.expensemanager.services;

import org.apache.coyote.BadRequestException;
import org.example.expensemanager.models.User;
import org.example.expensemanager.models.dto.user.UserRequestDto;
import org.example.expensemanager.models.dto.user.UserResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponseDto convertResponseToDto(User user);

    UserResponseDto createUser(UserRequestDto userDto) throws BadRequestException;

    List<UserResponseDto> getAll();

    UserResponseDto updateUser(UUID id, UserRequestDto userDto);

    void deleteUser(UUID id);

}
