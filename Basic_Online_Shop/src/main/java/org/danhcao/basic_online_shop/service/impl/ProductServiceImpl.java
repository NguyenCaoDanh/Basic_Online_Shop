package org.danhcao.basic_online_shop.service.impl;

import org.danhcao.basic_online_shop.entity.Product;
import org.danhcao.basic_online_shop.exception.ErrorHandler;
import org.danhcao.basic_online_shop.generic.GenericService;
import org.danhcao.basic_online_shop.repository.ProductRepository;
import org.danhcao.basic_online_shop.service.CategoryService;
import org.danhcao.basic_online_shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;  // Thêm CategoryService để xử lý khóa ngoại

    @Autowired
    private GenericService genericService;


    @Override
    public Product save(Product product, MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                String imagePath = genericService.saveFile(file, "products/");
                product.setImgUrl(imagePath);
            }

            if (product.getCategory() == null) {
                throw new ErrorHandler(HttpStatus.BAD_REQUEST, "Category is required!");
            }

            return productRepository.save(product);
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving product: " + e.getMessage());
        }
    }

    @Override
    public Product updateProduct(Integer id, Product updatedProduct, MultipartFile file) throws IOException {
        Optional<Product> productOpt = productRepository.findById(id);

        if (productOpt.isEmpty()) {
            throw new ErrorHandler(HttpStatus.NOT_FOUND, "Product not found!");
        }

        Product existingProduct = productOpt.get();

        // Cập nhật thông tin
        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStockQuantity(updatedProduct.getStockQuantity());
        existingProduct.setDescription(updatedProduct.getDescription());

        if (updatedProduct.getCategory() != null) {
            existingProduct.setCategory(updatedProduct.getCategory());
        }

        if (file != null && !file.isEmpty()) {
            if (existingProduct.getImgUrl() != null) {
                genericService.deleteFile(existingProduct.getImgUrl());
            }
            String newImagePath = genericService.saveFile(file, "products/");
            existingProduct.setImgUrl(newImagePath);
        }

        return productRepository.save(existingProduct);
    }
    @Override
    public void save(Product product) {
        try {
            productRepository.save(product);
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving product: " + e.getMessage());
        }
    }

    // Xóa sản phẩm và ảnh đi kèm
    @Override
    public void delete(Integer productId) {
        try {
            Optional<Product> productOpt = productRepository.findById(productId);
            if (productOpt.isPresent()) {
                Product product = productOpt.get();

                // Xóa ảnh nếu có
                if (product.getImgUrl() != null) {
                    genericService.deleteFile(product.getImgUrl());
                }

                productRepository.deleteById(productId);
            } else {
                throw new ErrorHandler(HttpStatus.NOT_FOUND, "Product not found!");
            }
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting product: " + e.getMessage());
        }
    }

    @Override
    public Iterator<Product> findAll() {
        try {
            return productRepository.findAll().iterator();
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving products: " + e.getMessage());
        }
    }

    @Override
    public Product findOne(Integer productId) {
        try {
            return productRepository.findById(productId)
                    .orElseThrow(() -> new ErrorHandler(HttpStatus.NOT_FOUND, "Product not found!"));
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving product: " + e.getMessage());
        }
    }

    @Override
    public Page<Product> getProducts(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return productRepository.findAll(pageable);
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving product list: " + e.getMessage());
        }
    }
}
