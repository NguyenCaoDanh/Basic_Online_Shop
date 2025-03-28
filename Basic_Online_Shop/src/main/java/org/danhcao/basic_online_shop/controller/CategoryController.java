package org.danhcao.basic_online_shop.controller;

import org.danhcao.basic_online_shop.dto.RequestResponse;
import org.danhcao.basic_online_shop.entity.Category;
import org.danhcao.basic_online_shop.exception.ExceptionResponse;
import org.danhcao.basic_online_shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * API tạo mới một Category.
     * Chỉ ADMIN có quyền truy cập.
     *
     * @param category Đối tượng category cần tạo mới.
     * @return Response chứa thông tin category vừa tạo.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        try {
            categoryService.save(category);
            return ResponseEntity.ok(new RequestResponse("Category created successfully", category));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ExceptionResponse("Error creating category: " + e.getMessage()));
        }
    }

    /**
     * API lấy thông tin category theo ID.
     *
     * @param id ID của category cần lấy.
     * @return Response chứa thông tin category.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable("id") int id) {
        try {
            Category category = categoryService.findOne(id);
            return ResponseEntity.ok(new RequestResponse("Category retrieved successfully", category));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ExceptionResponse("Error retrieving category: " + e.getMessage()));
        }
    }

    /**
     * API cập nhật tên category theo ID.
     * Chỉ ADMIN có quyền truy cập.
     *
     * @param id            ID của category cần cập nhật.
     * @param updateRequest Đối tượng chứa thông tin cập nhật.
     * @return Response chứa thông tin category sau khi cập nhật.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") int id,
                                            @RequestBody Category updateRequest) {
        try {
            Category updatedCategory = categoryService.updateCategory(id, updateRequest.getCategoryName());
            return ResponseEntity.ok(new RequestResponse("Category updated successfully", updatedCategory));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ExceptionResponse("Error updating category: " + e.getMessage()));
        }
    }

    /**
     * API xóa category theo ID.
     * Chỉ ADMIN có quyền truy cập.
     *
     * @param id ID của category cần xóa.
     * @return Response thông báo kết quả xóa.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") int id) {
        try {
            categoryService.delete(id);
            return ResponseEntity.ok(new RequestResponse("Category deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ExceptionResponse("Error deleting category: " + e.getMessage()));
        }
    }

    /**
     * API lấy danh sách tất cả categories có phân trang.
     *
     * @param page Số trang (mặc định = 0).
     * @param size Số lượng bản ghi trên mỗi trang (mặc định = 10).
     * @return Response chứa danh sách categories.
     */
    @GetMapping
    public ResponseEntity<?> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<Category> categories = categoryService.getCategories(page, size);
            return ResponseEntity.ok(new RequestResponse("Categories retrieved successfully", categories));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ExceptionResponse("Unexpected error: " + e.getMessage()));
        }
    }
}
