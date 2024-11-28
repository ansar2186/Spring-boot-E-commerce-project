package com.shopme.admin.category;

import com.shopme.admin.category.service.CategoryService;
import com.shopme.commom.entity.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CategoryController {
    private Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryService service;

    @GetMapping("/categories")
    public String listAll(Model model) {
        logger.info("listAll method called under the CategoryController ");
        List<Category> listCategories = service.listAll();
        logger.info("Categories list size --- " + listCategories.size());
        model.addAttribute("listCategories", listCategories);
        return "categories/categories";
    }
}
