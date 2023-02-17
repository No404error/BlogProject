package com.blog.service;

import com.blog.vo.Result;
import com.blog.vo.fromParams.CommentParam;

public interface CommentService {
    Result getCommentsByArticle(Long articleId);


    Result addComment(CommentParam commentParam);

}
