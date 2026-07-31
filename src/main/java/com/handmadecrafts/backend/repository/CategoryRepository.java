package com.handmadecrafts.backend.repository;

import com.handmadecrafts.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
