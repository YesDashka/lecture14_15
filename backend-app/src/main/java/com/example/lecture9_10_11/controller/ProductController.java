package com.example.lecture9_10_11.controller;

import com.example.lecture9_10_11.AppConstants;
import com.example.lecture9_10_11.dto.ProductDto;
import com.example.lecture9_10_11.dto.ProductNameCategoryDto;
import com.example.lecture9_10_11.entity.Product;
import com.example.lecture9_10_11.repository.ProductRepository;
import com.example.lecture9_10_11.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("http://localhost:3000")
public class ProductController {

    private final ProductService productService;
    private final ProductRepository repository;

    public ProductController(ProductRepository repository, ProductService productService) {
        this.productService = productService;
        this.repository = repository;
    }


    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = repository.findAll();

        if(products.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestBody ProductDto productRequest){
        System.out.println(productRequest);
        Product product = productService.createProduct(productRequest);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id){
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable(value = "id") Long id,
            @RequestBody ProductDto productDto){
        Product product = ProductDto.toProduct(productDto);
        product.setId(id);
        Product updatedProduct = productService.updateProduct(id, product);

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProductById(@PathVariable(value = "id") Long id){
        productService.deleteProductById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/nameandcategoryid")
    public ResponseEntity<List<Product>> searchByNameAndCategoryId(
            @Valid @RequestBody ProductNameCategoryDto dto,
            @RequestParam(
                    value = "pageNo",
                    defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false
            ) int pageNo,
            @RequestParam(
                    value = "pageSize",
                    defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false
            ) int pageSize){
        List<Product> products = productService._search(dto, PageRequest.of(pageNo, pageSize));

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
