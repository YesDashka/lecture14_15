package com.example.lecture9_10_11.dto;

import com.example.lecture9_10_11.entity.Category;
import com.example.lecture9_10_11.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private float price;
    private String name;
    private long categoryId;

    public static Product toProduct(ProductDto productDto) {
        return Product.builder()
                .category(Category.builder().id(productDto.categoryId).build())
                .name(productDto.name)
                .price(productDto.price)
                .build();
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "price=" + price +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
