package org.example.expensemanager.controllers;

import jakarta.validation.Valid;
import org.example.expensemanager.models.dto.category.CategoryRequestDto;
import org.example.expensemanager.models.dto.category.CategoryResponseDto;
import org.example.expensemanager.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryControl {

    private final CategoryService categoryService;

    public CategoryControl(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<CategoryResponseDto> create(@PathVariable UUID userId, @Valid @RequestBody CategoryRequestDto dto){
        return new ResponseEntity<>(categoryService.createCategory(userId, dto), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CategoryResponseDto>> getByUserId(@PathVariable UUID userId){
        List<CategoryResponseDto> categories = categoryService.getAllByUserId(userId);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getById(@PathVariable UUID id){
        CategoryResponseDto category = categoryService.getById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> update(@PathVariable UUID id, @RequestBody CategoryRequestDto dto){
        return new ResponseEntity<>(categoryService.updateCategory(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> delete(@PathVariable UUID id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
