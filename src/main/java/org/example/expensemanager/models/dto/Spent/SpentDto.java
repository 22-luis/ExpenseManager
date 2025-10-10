package org.example.expensemanager.models.dto.Spent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public class SpentDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Spent {

        private Float mount;

        private Date date;

        private String description;

        private String archive;

    }
}
