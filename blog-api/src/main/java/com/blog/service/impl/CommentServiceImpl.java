package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.dao.mapper.CommentMapper;
import com.blog.dao.pojo.Comment;
import com.blog.dao.pojo.SysUser;
import com.blog.service.CommentService;
import com.blog.service.SysUserService;
import com.blog.service.ThreadService;
import com.blog.utils.UserThreadLocal;
import com.blog.vo.fromParams.CommentParam;
import com.blog.vo.toParams.CommentVo;
import com.blog.vo.Result;
import com.blog.vo.toParams.UserVo;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class
CommentServiceImpl implements CommentService{
    @Autowired
    ThreadService threadService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SysUserService sysUserService;

    //先查询第一层，再根据第一层来循环查询
    @Override
    public Result getCommentsByArticle(Long articleId) {
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,articleId);
        queryWrapper.eq(Comment::getLevel,1);

        List<Comment> comments = commentMapper.selectList(queryWrapper);

        if(comments==null||comments.size()==0)
            return Result.success(null);

        //作者信息
        UserVo userVo = sysUserService.findUserVoById(comments.get(0).getAuthorId());

        List<CommentVo> commentVos=copyList(comments,userVo);

        return Result.success(commentVos);
    }

    @Override
    public Result addComment(CommentParam commentParam) {
        SysUser user = UserThreadLocal.getUser();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(user.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        commentMapper.insert(comment);


        threadService.updateArticleCommentCount(comment.getArticleId());

        return Result.success(null);
    }

    private List<CommentVo> copyList(List<Comment> comments,UserVo author) {
        List<CommentVo> commentVos=new ArrayList<>();
        for(Comment comment:comments)
            commentVos.add(copy(comment,author));
        return commentVos;
    }

    private CommentVo copy(Comment comment,UserVo author) {
        CommentVo commentVo=new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);

        //time
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));

        //author
        commentVo.setAuthor(author);

        //toUser
        if(comment.getLevel()>1)
            commentVo.setToUser(sysUserService.findUserVoById(comment.getToUid()));

        //children
        List<Comment> sonComments=getCommentsByFatherComment(comment.getId());

        commentVo.setChildrens(copyList(sonComments,author));

        return commentVo;
    }

    private List<Comment> getCommentsByFatherComment(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);

        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return comments;
    }

}
