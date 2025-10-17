package org.example.expensemanager.controllers;

import jakarta.validation.Valid;
import org.example.expensemanager.models.dto.category.CategoryDto;
import org.example.expensemanager.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryControl {

    private final CategoryService categoryService;

    public CategoryControl(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<CategoryDto> create(UUID userId, @Valid @RequestBody CategoryDto dto){
        return new ResponseEntity<>(categoryService.createCategory(userId, dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDto> delete(UUID id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
