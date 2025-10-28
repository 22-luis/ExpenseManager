package org.example.expensemanager.services.impl;

import org.example.expensemanager.exceptions.ResourceNotFoundException;
import org.example.expensemanager.models.Category;
import org.example.expensemanager.models.User;
import org.example.expensemanager.models.dto.category.CategoryRequestDto;
import org.example.expensemanager.models.dto.category.CategoryResponseDto;
import org.example.expensemanager.repositories.CategoryRepository;
import org.example.expensemanager.repositories.UserRepository;
import org.example.expensemanager.services.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final  CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository){
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CategoryRequestDto convertToRequestDto(Category category) {
        CategoryRequestDto dto = new CategoryRequestDto();
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }

    @Override
    public CategoryResponseDto convertToResponseDto(Category category) {
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setUserId(category.getUser().getId());
        return dto;
    }

    @Override
    public CategoryResponseDto createCategory(UUID userId, CategoryRequestDto dto){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setUser(user);

        Category savedCategory = categoryRepository.save(category);

        return convertToResponseDto(savedCategory);
    }

    @Override
    public List<CategoryResponseDto> getAllByUserId(@PathVariable UUID userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return categoryRepository.findByUserId(userId).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(UUID id){
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("category", "id", id);
        }
         categoryRepository.deleteById(id);
    }

}
