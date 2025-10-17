package org.example.expensemanager.services.impl;

import org.example.expensemanager.exceptions.ResourceNotFoundException;
import org.example.expensemanager.models.Category;
import org.example.expensemanager.models.User;
import org.example.expensemanager.models.dto.category.CategoryDto;
import org.example.expensemanager.repositories.CategoryRepository;
import org.example.expensemanager.repositories.UserRepository;
import org.example.expensemanager.services.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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
    public CategoryDto convertToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        return categoryDto;
    }

    @Override
    public CategoryDto createCategory(UUID userId, CategoryDto dto){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setUser(user);

        Category savedCategory = categoryRepository.save(category);

        return convertToDto(savedCategory);
    }

    @Override
    public void deleteCategory(UUID id){
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("category", "id", id);
        }
         categoryRepository.deleteById(id);
    }
}
