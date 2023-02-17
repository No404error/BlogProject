package com.blog.service;

import com.blog.vo.Result;
import com.blog.vo.toParams.TagVo;

import java.util.List;

/**
 * @Author: 34848
 * @Date: 2023/01/13/19:16
 * @Description:
 */
public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);

    Result getHots(int limit);

    Result getAllTags();

    Result getAllTagsDetail();

    Result getTagDetailById(Long id);
}
