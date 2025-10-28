package org.example.expensemanager.services;

import org.example.expensemanager.models.Category;
import org.example.expensemanager.models.dto.category.CategoryRequestDto;
import org.example.expensemanager.models.dto.category.CategoryResponseDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    CategoryResponseDto convertToResponseDto(Category category);

    CategoryResponseDto createCategory(UUID userId, CategoryRequestDto dto);

    List<CategoryResponseDto> getAllByUserId(UUID userId);

    CategoryResponseDto getById(UUID id);

    CategoryResponseDto updateCategory(UUID id, CategoryRequestDto dto);

    void deleteCategory(UUID id);

}
