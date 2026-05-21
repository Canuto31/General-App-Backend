package com.canuto.general_app.finance.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canuto.general_app.finance.category.model.CategoryKeyword;

@Repository
public interface CategoryKeywordRepository extends JpaRepository<CategoryKeyword, Long>{
    
}
