package org.danhcao.basic_online_shop.service.impl;

import org.danhcao.basic_online_shop.entity.Category;
import org.danhcao.basic_online_shop.exception.ErrorHandler;
import org.danhcao.basic_online_shop.repository.CategoryRepository;
import org.danhcao.basic_online_shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public void save(Category category) {
        try {
            categoryRepository.save(category);
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving category");
        }
    }

    @Override
    public void delete(Integer integer) {
        try {
            Category category = categoryRepository.findById(integer)
                    .orElseThrow(() -> new ErrorHandler(HttpStatus.NOT_FOUND, "Category not found"));
            categoryRepository.delete(category);
        } catch (ErrorHandler e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, "Error while deleting category");
        }
    }

    @Override
    public Iterator<Category> findAll() {
        return null;
    }

    @Override
    public Category findOne(Integer integer) {
        return categoryRepository.findById(integer)
                .orElseThrow(() -> new ErrorHandler(HttpStatus.NOT_FOUND, "Category not found"));
    }
    @Override
    public Page<Category> getCategories(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return categoryRepository.findAll(pageable);
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving category");
        }
    }

    @Override
    public Category updateCategory(int id, String newCategoryName) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ErrorHandler(HttpStatus.NOT_FOUND, "Category not found"));

            category.setCategoryName(newCategoryName);
            return categoryRepository.save(category);
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating category: " + e.getMessage());
        }
    }

}
