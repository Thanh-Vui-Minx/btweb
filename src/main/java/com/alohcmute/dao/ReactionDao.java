package com.alohcmute.dao;

import com.alohcmute.entity.Reaction;
import java.util.List;
import java.util.Map;

public interface ReactionDao {
    Reaction save(Reaction r);
    List<Reaction> findByPostId(Long postId);
    Map<String, Long> countByEmojiForPost(Long postId);
    void deleteByPostId(Long postId);
}
