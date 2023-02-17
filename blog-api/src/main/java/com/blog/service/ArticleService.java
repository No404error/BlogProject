package com.blog.service;

import com.blog.vo.Result;
import com.blog.vo.fromParams.ArticleParam;
import com.blog.vo.fromParams.PageParams;

public interface ArticleService {
    Result listArticlesPage(PageParams pageParams);

    Result getHotArticles(int limit);

    Result getNewArticles(int limit);

    Result listArchives();

    Result findArticleById(Long articleId);

    Result publish(ArticleParam articleParam);

}
