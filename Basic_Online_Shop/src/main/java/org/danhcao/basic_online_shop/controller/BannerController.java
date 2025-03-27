package org.danhcao.basic_online_shop.controller;

import org.danhcao.basic_online_shop.dto.RequestResponse;
import org.danhcao.basic_online_shop.entity.Banner;
import org.danhcao.basic_online_shop.exception.ErrorHandler;
import org.danhcao.basic_online_shop.exception.ExceptionResponse;
import org.danhcao.basic_online_shop.service.impl.BannerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/api/banners")
public class BannerController {

    @Autowired
    private BannerServiceImpl bannerService;

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

    @GetMapping
    public ResponseEntity<?> getAllBanners(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<Banner> banners = bannerService.getBanners(page, size);
            return ResponseEntity.ok(new RequestResponse("Banners retrieved successfully", banners)); // ✅ Trả về đúng kiểu dữ liệu
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console để debug
            return ResponseEntity.internalServerError().body(new ExceptionResponse("Unexpected error: " + e.getMessage()));
        }
    }

}
