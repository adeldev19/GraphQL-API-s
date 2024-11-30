package com.dev.inventoryservice.repository;

import com.dev.inventoryservice.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
