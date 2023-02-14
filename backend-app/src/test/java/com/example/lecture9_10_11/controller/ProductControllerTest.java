package com.example.lecture9_10_11.controller;

import com.example.lecture9_10_11.Lecture91011Application;
import com.example.lecture9_10_11.dto.ProductNameCategoryDto;
import com.example.lecture9_10_11.entity.Category;
import com.example.lecture9_10_11.entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Lecture91011Application.class)
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper mapper = new ObjectMapper();

    // Integration test for create product REST API
    @Test
    @Sql(scripts = "/init.sql")
    public void givenProductObject_whenCreateProduct_thenReturnSavedProduct() throws Exception {

        Product product = Product.builder()
                .name("Iphone 14")
                .price(60000.0F)
                .category(Category.builder()
                        .name("Electronics")
                        .build()
                )
                .build();

        String content = mapper.writeValueAsString(product);

        ResultActions resp = mockMvc.perform(post("/api/categories/{categoryId}/products", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        );

        resp.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Iphone 14"))
                .andExpect(jsonPath("$.price").value(60000.0))
                .andExpect(jsonPath("$.category.name").value("Electronics"));
    }


    // positive scenario - valid product id
    // Integration test for GET product by id REST API
    @Test
    @Sql(scripts = "/init.sql")
    public void givenProductId_whenGetProductById_thenReturnProductObject() throws Exception {
        ResultActions resp = mockMvc.perform(get("/api/products/{id}", 1L));

        resp.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name").value("Iphone 14"))
                .andExpect(jsonPath("$.price").value(60000))
                .andExpect(jsonPath("$.category.name").value("Electronics"));
    }


    // negative scenario - valid product id
    // Integration test for GET product by id REST API
    @Test
    @Sql(scripts = "/init.sql")
    public void givenInvalidProductId_whenGetProductById_thenReturnEmpty() throws Exception {
        ResultActions resp = mockMvc.perform(get("/api/products/{id}", 80L));
        resp.andExpect(status().isNotFound())
                .andDo(print());
    }


    // Integration test for update product REST API
    @Test
    @Sql(scripts = "/init.sql")
    public void givenUpdatedProduct_whenUpdateProduct_thenReturnUpdateProductObject() throws Exception {

        Category updatedCategory = Category.builder()
                .id(2L)
                .name("Food and beverages")
                .build();

        Product updatedProduct = Product.builder()
                .name("Coca cola")
                .price(40.3F)
                .category(updatedCategory)
                .build();

        ResultActions response = mockMvc.perform(put("/api/categories/{categoryId}/products/{id}", updatedCategory.getId(), 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedProduct)));


        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name").value(updatedProduct.getName()))
                .andExpect(jsonPath("$.price").value(updatedProduct.getPrice()))
                .andExpect(jsonPath("$.category.name").value(updatedProduct.getCategory().getName()));
    }

    // Integration test for delete employee REST API
    @Test
    @Sql(scripts = "/init.sql")
    public void givenProductId_whenDeleteProduct_thenReturnOk() throws Exception {
        ResultActions resp = mockMvc.perform(delete("/api/products/{id}", 1L));

        resp.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Sql(scripts = "/init.sql")
    public void givenProductNameAndCategoryId_whenSearchProduct_thenReturnOk() throws Exception {

        ProductNameCategoryDto dto = new ProductNameCategoryDto("Iphone 14", 1L);
        String content = mapper.writeValueAsString(dto);

        ResultActions resp = mockMvc.perform(get("/api/products/nameandcategoryid")
                .requestAttr("pageNo", 0)
                .requestAttr("pageSize", 5)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        );

        Product expected = Product
                .builder()
                .name("Iphone 14")
                .category(Category.builder()
                        .name("Electronics")
                        .build()
                )
                .price(60000)
                .build();


        resp.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value(expected.getName()))
                .andExpect(jsonPath("$[0].price").value(expected.getPrice()))
                .andExpect(jsonPath("$[0].category.name").value(expected.getCategory().getName()));

    }

}