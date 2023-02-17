package com.blog.service;

import com.blog.vo.Result;
import com.blog.vo.toParams.CategoryVo;

public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);

    Result getAllCategory();

    Result getAllCategoryDetail();

    Result getCategoryDetailById(Long id);
}
