package com.alohcmute.dao;

import com.alohcmute.entity.Comment;
import java.util.List;

public interface CommentDao {
    Comment save(Comment c);
    List<Comment> findByPostId(Long postId);
    void deleteByPostId(Long postId);
}
