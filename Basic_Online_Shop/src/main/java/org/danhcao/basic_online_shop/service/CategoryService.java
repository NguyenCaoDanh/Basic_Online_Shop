package org.danhcao.basic_online_shop.service;

import org.danhcao.basic_online_shop.entity.Category;
import org.danhcao.basic_online_shop.generic.IService;
import org.springframework.data.domain.Page;

public interface CategoryService extends IService<Category,Integer> {
    public Page<Category> getCategories(int page, int size);
    public Category updateCategory(int id, String newCategoryName);
}
