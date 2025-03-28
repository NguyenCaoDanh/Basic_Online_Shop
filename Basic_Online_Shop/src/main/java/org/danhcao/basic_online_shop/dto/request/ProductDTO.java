package org.danhcao.basic_online_shop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.danhcao.basic_online_shop.entity.Product;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String productName;
    private int price;
    private String imgUrl;
    private int stockQuantity;
    private LocalDateTime createdAt;
    private String description;
    private Integer categoryId;

    // Chuyển đổi từ Product entity sang ProductDTO
    public static ProductDTO fromEntity(Product product) {
        return new ProductDTO(
                product.getProductName(),
                product.getPrice(),
                product.getImgUrl(),
                product.getStockQuantity(),
                product.getCreatedAt().toLocalDateTime(),
                product.getDescription(),
                product.getCategory() != null ? product.getCategory().getIdCategory() : null
        );
    }
}
