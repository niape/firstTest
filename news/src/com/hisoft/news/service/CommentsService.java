package com.hisoft.news.service;

import com.hisoft.news.entity.Comment;

public interface CommentsService {

    // 添加评论
    public int addComment(Comment comment);
}
