package com.alohcmute.dao;

import com.alohcmute.entity.ChatMessage;
import java.util.List;

public interface ChatDao {
    ChatMessage save(ChatMessage m);
    List<ChatMessage> findRecent(int limit);
}
