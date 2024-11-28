package com.shopme.admin.category.service.impl;

import com.shopme.admin.category.repository.CategoryRepository;
import com.shopme.admin.category.service.CategoryService;
import com.shopme.commom.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository repository;

    @Override
    public List<Category> listAll() {
        return repository.findAll();
    }
}
