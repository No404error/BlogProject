package com.blog.controller;

import com.blog.service.CategoryService;
import com.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorys")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result getAllCategory(){
        return categoryService.getAllCategory();
    }

    @GetMapping("/detail")
    public Result getAllCategoryDetail(){
        return categoryService.getAllCategoryDetail();
    }

    @GetMapping("/detail/{id}")
    public Result getCategoryDetailById(@PathVariable("id") Long id){
        return categoryService.getCategoryDetailById(id);
    }
}
