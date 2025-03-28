package org.danhcao.basic_online_shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.danhcao.basic_online_shop.dto.RequestResponse;
import org.danhcao.basic_online_shop.dto.request.ProductDTO;
import org.danhcao.basic_online_shop.entity.Category;
import org.danhcao.basic_online_shop.entity.Product;
import org.danhcao.basic_online_shop.exception.ExceptionResponse;
import org.danhcao.basic_online_shop.service.CategoryService;
import org.danhcao.basic_online_shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService; // Service để xử lý liên kết với Category

    /**
     * Phương thức chung để tạo mới hoặc cập nhật sản phẩm.
     *
     * @param id          ID của sản phẩm (chỉ cần khi cập nhật)
     * @param productJson Dữ liệu sản phẩm dưới dạng JSON
     * @param file        Ảnh sản phẩm (có thể null)
     * @param isNew       Xác định là tạo mới hay cập nhật
     * @return ResponseEntity chứa thông tin phản hồi
     */
    private ResponseEntity<?> saveOrUpdateProduct(Integer id, String productJson, MultipartFile file, boolean isNew) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ProductDTO productDTO = objectMapper.readValue(productJson, ProductDTO.class);

            // Kiểm tra danh mục có tồn tại hay không
            Optional<Category> categoryOpt = Optional.ofNullable(categoryService.findOne(productDTO.getCategoryId()));
            if (categoryOpt.isEmpty()) {
                return ResponseEntity.badRequest().body(new ExceptionResponse("Category not found!"));
            }

            // Tạo đối tượng Product từ dữ liệu của ProductDTO
            Product product = new Product();
            product.setProductName(productDTO.getProductName());
            product.setPrice(productDTO.getPrice());
            product.setStockQuantity(productDTO.getStockQuantity());
            product.setDescription(productDTO.getDescription());
            product.setCategory(categoryOpt.get());

            if (isNew) {
                // Thiết lập ngày tạo cho sản phẩm mới
                product.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                productService.save(product, file);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new RequestResponse("Product created successfully", null));
            } else {
                // Cập nhật sản phẩm có sẵn
                productService.updateProduct(id, product, file);
                return ResponseEntity.ok(new RequestResponse("Product updated successfully", null));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ExceptionResponse("Error processing product: " + e.getMessage()));
        }
    }

    /**
     * API tạo mới sản phẩm.
     * Chỉ ADMIN có quyền truy cập.
     *
     * @param productJson Dữ liệu sản phẩm dưới dạng JSON
     * @param file        Ảnh sản phẩm (tùy chọn)
     * @return Response chứa kết quả tạo sản phẩm
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(
            @RequestPart("product") String productJson,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        return saveOrUpdateProduct(null, productJson, file, true);
    }

    /**
     * API cập nhật sản phẩm theo ID.
     * Chỉ ADMIN có quyền truy cập.
     *
     * @param id          ID của sản phẩm cần cập nhật
     * @param productJson Dữ liệu sản phẩm dưới dạng JSON
     * @param file        Ảnh sản phẩm mới (tùy chọn)
     * @return Response chứa kết quả cập nhật sản phẩm
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable("id") Integer id,
            @RequestPart("product") String productJson,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        return saveOrUpdateProduct(id, productJson, file, false);
    }

    /**
     * API xóa sản phẩm theo ID.
     * Chỉ ADMIN có quyền truy cập.
     *
     * @param id ID của sản phẩm cần xóa
     * @return Response thông báo kết quả xóa
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Integer id) {
        try {
            productService.delete(id);
            return ResponseEntity.ok(new RequestResponse("Product deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ExceptionResponse("Error deleting product: " + e.getMessage()));
        }
    }

    /**
     * API lấy danh sách tất cả sản phẩm có phân trang.
     *
     * @param page Số trang (mặc định = 0)
     * @param size Số lượng sản phẩm trên mỗi trang (mặc định = 10)
     * @return Response chứa danh sách sản phẩm
     */
    @GetMapping
    public ResponseEntity<?> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<ProductDTO> products = productService.getProducts(page, size).map(ProductDTO::fromEntity);
            return ResponseEntity.ok(new RequestResponse("Products retrieved successfully", products));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ExceptionResponse("Unexpected error: " + e.getMessage()));
        }
    }

    /**
     * API lấy thông tin sản phẩm theo ID.
     *
     * @param id ID của sản phẩm cần lấy
     * @return Response chứa thông tin sản phẩm
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Integer id) {
        try {
            Product product = productService.findOne(id);
            return ResponseEntity.ok(new RequestResponse("Product retrieved successfully", ProductDTO.fromEntity(product)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ExceptionResponse("Error retrieving product: " + e.getMessage()));
        }
    }
}
