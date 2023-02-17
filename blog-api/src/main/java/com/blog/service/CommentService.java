package com.blog.service;

import com.blog.vo.Result;
import com.blog.vo.fromParams.CommentParam;

/**
 * @Author: 34848
 * @Date: 2023/01/15/16:23
 * @Description:
 */
public interface CommentService {
    Result getCommentsByArticle(Long articleId);


    Result addComment(CommentParam commentParam);

}
