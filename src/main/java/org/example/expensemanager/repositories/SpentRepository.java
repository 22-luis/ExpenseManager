package org.example.expensemanager.repositories;

import org.example.expensemanager.models.Spent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpentRepository extends JpaRepository<Spent,UUID> {
    Optional<Spent> findByUserId(UUID userId);
    Optional<Spent> findByCategoryId(UUID categoryId);

}
