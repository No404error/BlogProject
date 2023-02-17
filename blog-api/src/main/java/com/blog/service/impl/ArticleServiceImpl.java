package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.dao.dos.Archive;
import com.blog.dao.mapper.ArticleBodyMapper;
import com.blog.dao.mapper.ArticleMapper;
import com.blog.dao.mapper.ArticleTagMapper;
import com.blog.dao.pojo.*;
import com.blog.service.*;
import com.blog.utils.UserThreadLocal;
import com.blog.vo.fromParams.ArticleParam;
import com.blog.vo.toParams.ArticleBodyVo;
import com.blog.vo.toParams.ArticleVo;
import com.blog.vo.Result;
import com.blog.vo.fromParams.PageParams;
import com.blog.vo.toParams.TagVo;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public Result listArticlesPage(PageParams pageParams) {
        //分页设置
        Page<Article> page=new Page<>(pageParams.getPage(),pageParams.getPageSize());

        IPage<Article> articleIPage = articleMapper.listArticle(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());

        List<Article> articles=articleIPage.getRecords();
        List<ArticleVo> articleVos = copyList(articles, true, true, true, false, false);
        return Result.success(articleVos);
    }

    @Override
    public Result getHotArticles(int limit) {
        //select id,title from article order by view_counts desc limit 5
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);

        List<Article> articles=articleMapper.selectList(queryWrapper);
        List<ArticleVo> articleVos = copyList(articles, false, false, false, false, false);

        return Result.success(articleVos);
    }

    @Override
    public Result getNewArticles(int limit) {
        //select id,title from article order by create_date desc limit 5
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);

        List<Article> articles=articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles,false,false,false,false,false));
    }

    @Override
    public Result listArchives() {
        //select year(FROM_UNIXTIME(create_date/1000)) as year,month(FROM_UNIXTIME(create_date/1000)) as month,count(*) from ms_article group by year,month
        List<Archive> archiveList=articleMapper.listArchives();
        return Result.success(archiveList);
    }

    @Override
    public Result findArticleById(Long articleId) {
        Article article = articleMapper.selectById(articleId);
        ArticleVo articleVo=copy(article,true,true,true,true,true);

        threadService.updateArticleViewCount(articleId);

        return Result.success(articleVo);
    }

    @Override
    public Result publish(ArticleParam articleParam) {
        SysUser user = UserThreadLocal.getUser();

        //得到文章id
        Article article=new Article();
        article.setAuthorId(user.getId());
        article.setCategoryId(articleParam.getCategory().getId());
        article.setCreateDate(System.currentTimeMillis());
        article.setSummary(articleParam.getSummary());
        article.setTitle(articleParam.getTitle());
        article.setCommentCounts(0);
        article.setViewCounts(0);
        article.setWeight(Article.Article_Common);
        article.setBodyId(-1L);
        articleMapper.insert(article);

        Long articleId = article.getId();

        //添加articleTag
        for(TagVo tag:articleParam.getTags()){
            ArticleTag articleTag=new ArticleTag();
            articleTag.setArticleId(articleId);
            articleTag.setTagId(tag.getId());
            articleTagMapper.insert(articleTag);
        }

        //写入文本
        ArticleBody articleBody=new ArticleBody();
        articleBody.setArticleId(articleId);
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);

        //更新article表数据
        article.setBodyId(articleBody.getId());

        articleMapper.updateById(article);
        Map<String,String> map=new HashMap<>();
        map.put("id",article.getId().toString());
        return Result.success(map);
    }

    private ArticleBodyVo findArticleBodyById(Long bodyId){
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo=new ArticleBodyVo();
        BeanUtils.copyProperties(articleBody,articleBodyVo);
        return articleBodyVo;
    }

    private List<ArticleVo> copyList(List<Article> records,boolean needTime,boolean needTag,boolean needAuthor,boolean needBody,boolean needCategory){
        List<ArticleVo> articleVoList=new ArrayList<>();
        for(Article record:records)
            articleVoList.add(copy(record,needTime,needTag,needAuthor,needBody, needCategory));
        return articleVoList;
    }

    private ArticleVo copy(Article article,boolean needTime,boolean needTag,boolean needAuthor,boolean needBody,boolean needCategory){
        ArticleVo articleVo=new ArticleVo();
        try {
            BeanUtils.copyProperties(article, articleVo);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(needTime){
            DateTime time = new DateTime(article.getCreateDate());
            articleVo.setCreateDate(time.toString("yyyy-MM-dd HH:mm"));
        }
         if(needTag){
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }

        if(needAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId));
        }

        if(needBody){
            Long bodyId=article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }

        if(needCategory){
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }

        return articleVo;
    }
}
