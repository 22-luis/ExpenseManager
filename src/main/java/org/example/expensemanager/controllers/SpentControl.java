package org.example.expensemanager.controllers;

import jakarta.validation.Valid;
import org.example.expensemanager.models.dto.Spent.SpentRequestDto;
import org.example.expensemanager.models.dto.Spent.SpentResponseDto;
import org.example.expensemanager.services.SpentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/spent")
public class SpentControl {

    private final SpentService spentService;

    public SpentControl(SpentService spentService){
        this.spentService = spentService;
    }

    @PostMapping("/{userId}/{categoryId}")
    public ResponseEntity<SpentResponseDto> create(
            @PathVariable UUID userId,
            @PathVariable UUID categoryId,
            @Valid @RequestBody SpentRequestDto dto){
        return new ResponseEntity<>(spentService.createSpent(userId, categoryId, dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpentResponseDto> getById(@PathVariable UUID id){
        SpentResponseDto spent = spentService.getById(id);
        return new ResponseEntity<>(spent, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<List<SpentResponseDto>> getAllByCategoryId(@PathVariable UUID categoryId){
        List<SpentResponseDto> spent = spentService.getAllByCategoryId(categoryId);
        return new ResponseEntity<>(spent, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<SpentResponseDto>> getAllByUserId(@PathVariable UUID userId){
        List<SpentResponseDto> spent = spentService.getAllByUserId(userId);
        return new ResponseEntity<>(spent, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SpentResponseDto> update(@PathVariable UUID id, @RequestBody SpentRequestDto dto){
        return new ResponseEntity<>(spentService.updateSpent(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SpentResponseDto> delete(@PathVariable UUID id){
        spentService.deleteSpent(id);
        return ResponseEntity.noContent().build();
    }

}
