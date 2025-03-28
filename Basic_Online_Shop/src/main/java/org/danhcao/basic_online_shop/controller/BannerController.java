package org.danhcao.basic_online_shop.controller;

import org.danhcao.basic_online_shop.dto.RequestResponse;
import org.danhcao.basic_online_shop.entity.Banner;
import org.danhcao.basic_online_shop.exception.ErrorHandler;
import org.danhcao.basic_online_shop.exception.ExceptionResponse;
import org.danhcao.basic_online_shop.service.impl.BannerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/banners")
public class BannerController {

    @Autowired
    private BannerServiceImpl bannerService;

    /**
     * Tạo mới một banner
     * Chỉ ADMIN có quyền truy cập
     *
     * @param file   Hình ảnh của banner
     * @param status Trạng thái của banner
     * @return Thông tin banner mới tạo
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createBanner(@RequestParam("file") MultipartFile file,
                                          @RequestParam("status") String status) {
        try {
            Banner banner = bannerService.createBanner(file, status);
            return ResponseEntity.ok(new RequestResponse("Banner created successfully", banner));
        } catch (ErrorHandler e) {
            return ResponseEntity.status(e.getStatus()).body(new ExceptionResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ExceptionResponse("Unexpected error"));
        }
    }

    /**
     * Cập nhật thông tin banner theo ID
     * Chỉ ADMIN có quyền truy cập
     *
     * @param id     ID của banner cần cập nhật
     * @param file   Hình ảnh mới của banner
     * @param status Trạng thái mới của banner
     * @return Thông tin banner sau khi cập nhật
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBanner(@PathVariable int id,
                                          @RequestParam("file") MultipartFile file,
                                          @RequestParam("status") String status) {
        try {
            Banner banner = bannerService.updateBanner(id, file, status);
            return ResponseEntity.ok(new RequestResponse("Banner updated successfully", banner));
        } catch (ErrorHandler e) {
            return ResponseEntity.status(e.getStatus()).body(new ExceptionResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ExceptionResponse("Unexpected error"));
        }
    }

    /**
     * Xóa banner theo ID
     * Chỉ ADMIN có quyền truy cập
     *
     * @param id ID của banner cần xóa
     * @return Thông báo thành công hoặc lỗi
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBanner(@PathVariable int id) {
        try {
            bannerService.delete(id);
            return ResponseEntity.ok(new RequestResponse("Banner deleted successfully"));
        } catch (ErrorHandler e) {
            return ResponseEntity.status(e.getStatus()).body(new ExceptionResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ExceptionResponse("Unexpected error"));
        }
    }

    /**
     * Lấy thông tin banner theo ID
     *
     * @param id ID của banner cần lấy
     * @return Thông tin banner
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getBannerById(@PathVariable int id) {
        try {
            Banner banner = bannerService.findOne(id);
            return ResponseEntity.ok(new RequestResponse("Banner found", banner));
        } catch (ErrorHandler e) {
            return ResponseEntity.status(e.getStatus()).body(new ExceptionResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ExceptionResponse("Unexpected error"));
        }
    }

    /**
     * Lấy danh sách tất cả banners có phân trang
     *
     * @param page Số trang (mặc định = 0)
     * @param size Số lượng bản ghi trên mỗi trang (mặc định = 10)
     * @return Danh sách banners
     */
    @GetMapping
    public ResponseEntity<?> getAllBanners(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<Banner> banners = bannerService.getBanners(page, size);
            return ResponseEntity.ok(new RequestResponse("Banners retrieved successfully", banners));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ExceptionResponse("Unexpected error: " + e.getMessage()));
        }
    }
}
