package com.blog.controller;

import com.blog.common.aop.LogAnnotation;
import com.blog.common.cache.Cache;
import com.blog.service.ArticleService;
import com.blog.vo.Result;
import com.blog.vo.fromParams.ArticleParam;
import com.blog.vo.fromParams.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    /**
     * 得到对应页的文章列表
     * @param pageParams
     * @return
     */
    @PostMapping()
    @LogAnnotation(module = "文章",operation = "获取文章列表")
    @Cache(name = "listArticle")
    public Result listArticle(@RequestBody PageParams pageParams){
        return articleService.listArticlesPage(pageParams);
    }

    @PostMapping("/hot")
    @LogAnnotation(module = "文章",operation = "获取热门文章")
    @Cache(name = "hotArticle")
    public Result getHotArticles(){
        int limit=5;
        return articleService.getHotArticles(limit);
    }

    @PostMapping("/new")
    @LogAnnotation(module = "文章",operation = "获取最新文章")
    @Cache(name = "newArticle")
    public Result getNewArticles(){
        int limit=5;
        return articleService.getNewArticles(limit);
    }

    @PostMapping("/listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }

    @PostMapping("/view/{id}")
    public Result findArticleById(@PathVariable("id")Long articleId){
        return articleService.findArticleById(articleId);
    }

    @PostMapping("/publish")
    public Result publish(@RequestBody ArticleParam  articleParam){
        return articleService.publish(articleParam);
    }
}
