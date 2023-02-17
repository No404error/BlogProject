package com.blog.service;

import com.blog.schedule.ArticleCommentCount;
import com.blog.schedule.ArticleViewCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Author: 34848
 * @Date: 2023/01/15/15:49
 * @Description:
 */
@Component
public class ThreadService {
    @Autowired
    private ArticleViewCount articleViewCount;

    @Autowired
    private ArticleCommentCount articleCommentCount;

    //更新任务不需要在service中执行,交给线程池能够提高系统响应速度
    @Async("taskExecutor")
    public void updateArticleViewCount(Long articleId){
        articleViewCount.updateArticleViewCount(articleId);
    }

    @Async("taskExecutor")
    public void updateArticleCommentCount(Long articleId){
        articleCommentCount.updateArticleCommentCount(articleId);
    }
}
