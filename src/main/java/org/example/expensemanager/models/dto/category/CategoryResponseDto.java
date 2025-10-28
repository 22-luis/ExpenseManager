package org.example.expensemanager.models.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto {

    private UUID id;

    private String name;

    private String description;

    private UUID userId;

}
