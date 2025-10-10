package org.example.expensemanager.services.impl;

import org.apache.coyote.BadRequestException;
import org.example.expensemanager.models.User;
import org.example.expensemanager.models.dto.user.CreateUserRequestDto;
import org.example.expensemanager.models.dto.user.UpdateUserRequestDto;
import org.example.expensemanager.models.dto.user.UserDto;
import org.example.expensemanager.exceptions.ResourceNotFoundException;
import org.example.expensemanager.repositories.UserRepository;
import org.example.expensemanager.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getName());
        userDto.setRole(user.getRole());
        return userDto;
    }

    private User findById(UUID id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    private User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    @Override
    public UserDto createUser(CreateUserRequestDto dto) throws BadRequestException {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        User savedUser = userRepository.save(user);

        return convertToDto(savedUser);

    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(UUID id){
        User user = findById(id);

        return convertToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = findByEmail(email);

        return convertToDto(user);
    }

    @Override
    public UserDto updateUser(UUID id, UpdateUserRequestDto dto) {
        User user = findById(id);

        if (dto.getUsername() != null) {
            user.setName(dto.getUsername());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }
        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }

        User updatedUser = userRepository.save(user);

        return convertToDto(updatedUser);
    }

    @Override
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("user", "id", id);
        }
        userRepository.deleteById(id);
    }


}
