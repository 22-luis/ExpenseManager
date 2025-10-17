package org.example.expensemanager.services;

import org.example.expensemanager.models.Category;
import org.example.expensemanager.models.dto.category.CategoryDto;

import java.util.UUID;

public interface CategoryService {
    CategoryDto convertToDto(Category category);

    CategoryDto createCategory(UUID userId, CategoryDto dto);

    void deleteCategory(UUID id);
}
