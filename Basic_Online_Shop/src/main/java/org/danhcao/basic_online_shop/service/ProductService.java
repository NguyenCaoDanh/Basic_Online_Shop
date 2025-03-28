package org.danhcao.basic_online_shop.service;

import org.danhcao.basic_online_shop.entity.Category;
import org.danhcao.basic_online_shop.entity.Product;
import org.danhcao.basic_online_shop.generic.IService;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService extends IService<Product,Integer> {
    public Product save(Product product, MultipartFile file);
    public Product updateProduct(Integer id, Product updatedProduct, MultipartFile file) throws IOException;
    public Page<Product> getProducts(int page, int size);
}
