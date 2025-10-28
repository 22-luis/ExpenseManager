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
import org.springframework.web.bind.annotation.PathVariable;

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

    @Override
    public UserResponseDto createUser(UserRequestDto dto) throws BadRequestException {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        User savedUser = userRepository.save(user);

        return convertResponseToDto(savedUser);

    }

    @Override
    public List<UserResponseDto> getAll() {
        return userRepository.findAll().stream()
                .map(this::convertResponseToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto updateUser(@PathVariable UUID id, UserRequestDto dto) {
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

        return convertResponseToDto(updatedUser);
    }

    @Override
    public void deleteUser(@PathVariable UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("user", "id", id);
        }
        userRepository.deleteById(id);
    }


}
