package com.alohcmute.dao;

import com.alohcmute.entity.Post;
import java.util.List;

public interface PostDao {
    Post save(Post post);
    Post findById(Long id);
    List<Post> findRecent(int max);
    long countAll();
    void deleteById(Long id);
}
