package com.canuto.general_app.finance.category.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canuto.general_app.finance.category.model.Category;
import com.canuto.general_app.finance.category.utils.CategoryType;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
    Optional<Category> findByType(CategoryType type);
}
