package com.blog.controller;

import com.blog.service.TagService;
import com.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/hot")
    public Result getHotTags(){
        int limit = 6;
        return tagService.getHots(limit);
    }

    @GetMapping
    public Result getAllTags(){
        return tagService.getAllTags();
    }

    @GetMapping("/detail")
    public Result getAllTagsDetail(){
        return tagService.getAllTagsDetail();
    }

    @GetMapping("/detail/{id}")
    public Result getTagDetailById(@PathVariable("id")Long id){
        return tagService.getTagDetailById(id);
    }
}
