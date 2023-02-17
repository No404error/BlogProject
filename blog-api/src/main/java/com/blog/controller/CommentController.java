package com.blog.controller;

import com.blog.service.CommentService;
import com.blog.vo.Result;
import com.blog.vo.fromParams.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/article/{id}")
    public Result getCommentsByArticle(@PathVariable("id")Long articleId){
        return commentService.getCommentsByArticle(articleId);
    }

    @PostMapping("/create/change")
    public Result addComment(@RequestBody CommentParam commentParam){
        return commentService.addComment(commentParam);
    }
}
