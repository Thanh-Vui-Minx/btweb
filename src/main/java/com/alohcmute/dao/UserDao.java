package com.alohcmute.dao;

import com.alohcmute.entity.User;
import java.util.List;

public interface UserDao {
    User save(User user);
    User findById(Long id);
    User findByEmail(String email);
    User findByUsername(String username);
    List<User> listAll();
}
