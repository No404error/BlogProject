package com.blog.service;

import com.blog.vo.Result;
import com.blog.vo.toParams.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);

    Result getHots(int limit);

    Result getAllTags();

    Result getAllTagsDetail();

    Result getTagDetailById(Long id);
}
