package org.example.expensemanager.models.dto.Spent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSpentRequestDto {

        private Float mount;

        private Date date;

        private String description;

        private String archive;

}
