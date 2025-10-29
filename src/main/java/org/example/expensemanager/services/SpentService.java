package org.example.expensemanager.services;

import org.example.expensemanager.models.Spent;
import org.example.expensemanager.models.dto.Spent.SpentRequestDto;
import org.example.expensemanager.models.dto.Spent.SpentResponseDto;

import java.util.List;
import java.util.UUID;

public interface SpentService {

    SpentResponseDto convertToResponseDto(Spent spent);

    SpentResponseDto createSpent(UUID userId, UUID categoryId, SpentRequestDto dto);

    SpentResponseDto getById(UUID id);

    List<SpentResponseDto> getAllByCategoryId(UUID categoryId);

    List<SpentResponseDto> getAllByUserId(UUID userId);

    SpentResponseDto updateSpent(UUID id, SpentRequestDto dto);

    void deleteSpent(UUID id);
}
