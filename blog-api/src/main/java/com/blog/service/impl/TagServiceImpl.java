package com.blog.service.impl;

import com.blog.dao.mapper.TagMapper;
import com.blog.dao.pojo.Tag;
import com.blog.service.TagService;
import com.blog.vo.Result;
import com.blog.vo.toParams.TagVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public Result getAllTags() {
        List<Tag> tags = tagMapper.selectList(null);
        return Result.success(tags);
    }

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        List<Tag> tags=tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    @Override
    public Result getHots(int limit) {
        List<Long> tagsId=tagMapper.findHotTagsId(limit);
        if(CollectionUtils.isEmpty(tagsId))
            return Result.success(Collections.emptyList());
        List<Tag> tags=tagMapper.findTagsByIds(tagsId);
        return Result.success(tags);
    }

    @Override
    public Result getAllTagsDetail() {
        List<Tag> tags = tagMapper.selectList(null);
        return Result.success(tags);
    }

    @Override
    public Result getTagDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        return Result.success(tag);
    }

    private TagVo copy(Tag tag){
        TagVo tagVo=new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }

    private List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList=new ArrayList<>();
        for(Tag tag:tagList)
            tagVoList.add(copy(tag));
        return tagVoList;
    }
}
