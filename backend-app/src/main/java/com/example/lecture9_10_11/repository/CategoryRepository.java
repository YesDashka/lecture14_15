package com.example.lecture9_10_11.repository;

import com.example.lecture9_10_11.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {

}
