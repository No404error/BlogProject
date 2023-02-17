package com.blog.schedule;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.dao.mapper.ArticleMapper;
import com.blog.dao.pojo.Article;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

//定时任务,将ArticleCommentCount定时更新
@Component
@EnableScheduling
@EnableAsync
public class ArticleCommentCount {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private ArticleMapper articleMapper;

    private static final Object object=new Object();

    private static final ConcurrentHashMap<Long, Object> articleIds = new ConcurrentHashMap<>();

    private long expire=30*1000;

    @Async
    @Scheduled(fixedDelay = 10*1000)
    public void run() {
        for(Long articleId:articleIds.keySet()){
            //得到viewCount
            String id="ArticleCommentCount::"+articleId;
            String commitCountStr = redisTemplate.opsForValue().get(id);
            if(StringUtils.isEmpty(commitCountStr))
                continue;
            Integer commitCount = Integer.valueOf(commitCountStr);

            //更新操作
            Article article=new Article();
            article.setId(articleId);
            article.setCommentCounts(commitCount);
            articleMapper.updateById(article);
        }
        articleIds.clear();
    }

    private void initArticleCommentCount(Long articleId, String articleIdStr){
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.select(Article::getViewCounts);
        Article article = articleMapper.selectById(articleId);
        Integer commitCount = article.getCommentCounts();
        //添加至redis
        redisTemplate.opsForValue().set(articleIdStr,String.valueOf(commitCount), Duration.ofMillis(expire));
    }

    public void updateArticleCommentCount(Long articleId){
        String id="ArticleCommentCount::"+ articleId;

        String commitCountStr = redisTemplate.opsForValue().get(id);
        if(StringUtils.isEmpty(commitCountStr))
            initArticleCommentCount(articleId,id);

        redisTemplate.opsForValue().increment(id);

        //添加需要返回任务的对象
        articleIds.put(articleId,object);

    }
}