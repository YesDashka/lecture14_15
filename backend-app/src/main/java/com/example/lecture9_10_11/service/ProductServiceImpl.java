package com.example.lecture9_10_11.service;

import com.example.lecture9_10_11.dto.ProductDto;
import com.example.lecture9_10_11.dto.ProductNameCategoryDto;
import com.example.lecture9_10_11.entity.Category;
import com.example.lecture9_10_11.entity.Product;
import com.example.lecture9_10_11.exception.ResourceNotFoundException;
import com.example.lecture9_10_11.repository.CategoryRepository;
import com.example.lecture9_10_11.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        Product product = ProductDto.toProduct(productDto);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(long productId, Product productDetails) {
        Product updatedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        updatedProduct.setName(productDetails.getName());
        updatedProduct.setPrice(productDetails.getPrice());
        updatedProduct.setCategory(productDetails.getCategory());
        productRepository.save(updatedProduct);
        return updatedProduct;
    }

    @Override
    public Product getProductById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    @Override
    public void deleteProductById(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        productRepository.delete(product);
    }

    @Override
    public List<Product> _search(ProductNameCategoryDto dto, Pageable pageable) {
        List<Product> productList = productRepository.findByNameAndCategoryId(dto.getName(), dto.getCategoryId(), pageable);
        if(productList.isEmpty()){
            throw new ResourceNotFoundException("Product", "name", "categoryId");
        }
        return productList;
    }

}
