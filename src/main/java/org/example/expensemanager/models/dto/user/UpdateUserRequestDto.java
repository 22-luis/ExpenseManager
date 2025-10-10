package org.example.expensemanager.models.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.expensemanager.models.Role;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestDto {
    private String username;
    private String email;
    private String password;
    private Role role;
}
