package com.blog.service.impl;

import com.blog.dao.mapper.CategoryMapper;
import com.blog.dao.pojo.Category;
import com.blog.service.CategoryService;
import com.blog.vo.Result;
import com.blog.vo.toParams.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVo findCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        return copy(category);
    }

    @Override
    public Result getAllCategory() {
        List<Category> categories = categoryMapper.selectList(null);
        List<CategoryVo> categoryVoList=copyList(categories);
        return Result.success(categoryVoList);
    }

    @Override
    public Result getAllCategoryDetail() {
        List<Category> categories = categoryMapper.selectList(null);
        return Result.success(categories);
    }

    @Override
    public Result getCategoryDetailById(Long id) {
        Category category = categoryMapper.selectById(id);
        return Result.success(category);
    }


    private List<CategoryVo> copyList(List<Category> categories) {
        List<CategoryVo> categoryVoList=new ArrayList<>();
        for(Category category:categories)
            categoryVoList.add(copy(category));
        return categoryVoList;
    }

    private CategoryVo copy(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }
}
