package com.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.dao.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询最热标签 前limit条
     * @param limit
     * @return
     */
    List<Long> findHotTagsId(int limit);

    /**
     * 根据标签Id列表返回标签列表,无需avatar
     * @param tagsId
     * @return
     */
    List<Tag> findTagsByIds(List<Long> tagsId);

}
