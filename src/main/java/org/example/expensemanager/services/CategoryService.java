package org.example.expensemanager.services;

import org.example.expensemanager.models.Category;
import org.example.expensemanager.models.dto.category.CategoryRequestDto;
import org.example.expensemanager.models.dto.category.CategoryResponseDto;
import org.example.expensemanager.models.dto.user.UserResponseDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    CategoryResponseDto convertToResponseDto(Category category);

    CategoryRequestDto convertToRequestDto(Category category);

    CategoryResponseDto createCategory(UUID userId, CategoryRequestDto dto);

    List<CategoryResponseDto> getAllByUserId(UUID userId);

    void deleteCategory(UUID id);

}
