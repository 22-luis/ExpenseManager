package org.example.expensemanager.services.impl;

import org.apache.coyote.BadRequestException;
import org.example.expensemanager.entities.User;
import org.example.expensemanager.entities.dto.user.UserDto;
import org.example.expensemanager.exceptions.ResourceNotFoundException;
import org.example.expensemanager.repositories.UserRepository;
import org.example.expensemanager.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) throws BadRequestException {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setRol(userDto.getRole());

        User savedUser = userRepository.save(user);

        return convertToDto(savedUser);

    }

    @Override
    public UserDto getUserById(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
        return convertToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user", "email", email));
        return convertToDto(user);
    }

    @Override
    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setUsername(user.getName());
        userDto.setRole(user.getRol());
        return userDto;
    }

    @Override
    public UserDto updateUser(UUID id, UserDto userDto) {
        return null;
    }

    @Override
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("user", "id", id);
        }
        userRepository.deleteById(id);
    }


}
