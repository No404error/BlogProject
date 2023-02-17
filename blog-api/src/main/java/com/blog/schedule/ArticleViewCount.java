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
import java.util.concurrent.ConcurrentHashMap;


//定时任务,将viewCount定时更新
@Component
@EnableScheduling
@EnableAsync
public class ArticleViewCount{
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private ArticleMapper articleMapper;

    private static final Object object=new Object();

    //高并发map
    private static final ConcurrentHashMap<Long, Object> articleIds = new ConcurrentHashMap<>();

    private long expire=30*1000;

    @Async
    @Scheduled(fixedDelay = 10*1000)
    public void run() {
        for(Long articleId:articleIds.keySet()){
            //得到viewCount
            String id="ArticleViewCount::"+ articleId;
            String viewCountStr = redisTemplate.opsForValue().get(id);
            if(StringUtils.isEmpty(viewCountStr))
                continue;
            Integer viewCount = Integer.valueOf(viewCountStr);

            //更新操作
            Article article=new Article();
            article.setId(articleId);
            article.setViewCounts(viewCount);
            articleMapper.updateById(article);
        }
        articleIds.clear();
    }

    //用数据库初始化redis的数据
    private void initArticleViewCount(Long articleId, String articleIdStr){
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.select(Article::getViewCounts);
        Article article = articleMapper.selectById(articleId);
        Integer viewCount = article.getViewCounts();
        //添加至redis
        redisTemplate.opsForValue().set(articleIdStr,String.valueOf(viewCount), Duration.ofMillis(expire));
    }

    //原子更新viewCount
    public void updateArticleViewCount(Long articleId){
        String id="ArticleViewCount::"+articleId;

        String viewCountStr = redisTemplate.opsForValue().get(id);
        if(StringUtils.isEmpty(viewCountStr))
            initArticleViewCount(articleId,id);

        redisTemplate.opsForValue().increment(id);

        //添加需要返回任务的对象
        articleIds.put(articleId,object);

    }
}
