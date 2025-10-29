package org.example.expensemanager.services.impl;

import org.example.expensemanager.exceptions.ResourceNotFoundException;
import org.example.expensemanager.models.Category;
import org.example.expensemanager.models.Spent;
import org.example.expensemanager.models.User;
import org.example.expensemanager.models.dto.Spent.SpentRequestDto;
import org.example.expensemanager.models.dto.Spent.SpentResponseDto;
import org.example.expensemanager.repositories.CategoryRepository;
import org.example.expensemanager.repositories.SpentRepository;
import org.example.expensemanager.repositories.UserRepository;
import org.example.expensemanager.services.SpentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class SpentServiceImpl implements SpentService {

    private final SpentRepository spentRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public SpentServiceImpl( SpentRepository spentRepository,  CategoryRepository categoryRepository, UserRepository userRepository) {
        this.spentRepository = spentRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Spent findById(UUID id){
        return spentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Spent", "id", id));
    }

    @Override
    public SpentResponseDto convertToResponseDto(Spent spent){
        SpentResponseDto dto = new SpentResponseDto();
        dto.setId(spent.getId());
        dto.setMount(spent.getMount());
        dto.setDate(spent.getDate());
        dto.setDescription(spent.getDescription());
        dto.setArchive(spent.getArchive());
        dto.setUserId(spent.getUser().getId());
        dto.setCategoryId(spent.getCategory().getId());
        return dto;
    }

    @Override
    public SpentResponseDto createSpent(UUID userId, UUID categoryId, SpentRequestDto dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        Spent spent = new Spent();
        spent.setMount(dto.getMount());
        spent.setDate(dto.getDate());
        spent.setDescription(dto.getDescription());
        spent.setArchive(dto.getArchive());
        spent.setUser(user);
        spent.setCategory(category);

        Spent savedSpent = spentRepository.save(spent);

        return convertToResponseDto(savedSpent);
    }

    @Override
    public SpentResponseDto getById(@PathVariable UUID id) {
        Spent spent = findById(id);

        return convertToResponseDto(spent);
    }

    @Override
    public List<SpentResponseDto> getAllByCategoryId(@PathVariable UUID categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        return spentRepository.findByCategoryId(categoryId).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SpentResponseDto> getAllByUserId(@PathVariable UUID userId) {
        categoryRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return spentRepository.findByUserId(userId).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public SpentResponseDto updateSpent(UUID id, SpentRequestDto dto) {
        Spent spent = findById(id);

        if (dto.getMount() != null) {
            spent.setMount(dto.getMount());
        }

            spent.setDate(dto.getDate());

        if (dto.getDescription() != null) {
            spent.setDescription(dto.getDescription());
        }

            spent.setArchive(dto.getArchive());

        spentRepository.save(spent);

        return convertToResponseDto(spent);
    }

    @Override
    public void deleteSpent(UUID id) {
        if (!spentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Spent", "id", id);
        }
        spentRepository.deleteById(id);
    }

}
