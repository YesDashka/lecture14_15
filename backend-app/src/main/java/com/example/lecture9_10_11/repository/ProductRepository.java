package com.example.lecture9_10_11.repository;

import com.example.lecture9_10_11.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {

    List<Product> findByNameAndCategoryId(String name, long categoryId, Pageable pageable);

}
