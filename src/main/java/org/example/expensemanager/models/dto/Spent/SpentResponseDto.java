package org.example.expensemanager.models.dto.Spent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.expensemanager.models.User;

import java.util.Date;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpentResponseDto {

        private UUID id;

        private Float mount;

        private Date date;

        private String description;

        private String archive;

        private UUID userId;
}
