package org.example.expensemanager.services.impl;

import org.apache.coyote.BadRequestException;
import org.example.expensemanager.models.User;
import org.example.expensemanager.models.dto.user.UserRequestDto;
import org.example.expensemanager.models.dto.user.UserResponseDto;
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
    public UserRequestDto convertRequestToDto(User user) {
        UserRequestDto dto = new UserRequestDto();
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getName());
        dto.setRole(user.getRole());
        return dto;
    }

    @Override
    public UserResponseDto convertResponseToDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getName());
        dto.setRole(user.getRole());
        return dto;
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
    public UserRequestDto createUser(UserRequestDto dto) throws BadRequestException {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        User savedUser = userRepository.save(user);

        return convertRequestToDto(savedUser);

    }

    @Override
    public List<UserResponseDto> getAll() {
        return userRepository.findAll().stream()
                .map(this::convertResponseToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getUserById(UUID id){
        User user = findById(id);

        return convertResponseToDto(user);
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        User user = findByEmail(email);

        return convertResponseToDto(user);
    }

    @Override
    public UserRequestDto updateUser(UUID id, UserRequestDto dto) {
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

        return convertRequestToDto(updatedUser);
    }

    @Override
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("user", "id", id);
        }
        userRepository.deleteById(id);
    }


}
