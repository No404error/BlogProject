package com.blog.vo.fromParams;

import com.blog.vo.toParams.CategoryVo;
import com.blog.vo.toParams.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;
}
