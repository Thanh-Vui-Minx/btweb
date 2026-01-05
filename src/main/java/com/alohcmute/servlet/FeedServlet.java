package com.alohcmute.servlet;

import com.alohcmute.entity.Post;
import com.alohcmute.entity.User;
import com.alohcmute.repo.PostRepository;
import com.alohcmute.repo.UserRepository;
import com.alohcmute.repo.FollowRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/user/home"})
public class FeedServlet extends HttpServlet {
    private final PostRepository postRepo = new PostRepository();
    private final com.alohcmute.repo.CommentRepository commentRepo = new com.alohcmute.repo.CommentRepository();
    private final com.alohcmute.repo.ReactionRepository reactionRepo = new com.alohcmute.repo.ReactionRepository();
    private final UserRepository userRepo = new UserRepository();
    private final FollowRepository followRepo = new FollowRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // ensure UTF-8 encoding for request/response so emoji and accented text render correctly
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        
        User currentUser = (User) req.getSession().getAttribute("user");
        
        List<Post> posts = postRepo.findRecent(20);
        req.setAttribute("posts", posts);
        
        java.util.Map<Long, java.util.List<com.alohcmute.entity.Comment>> commentsMap = new java.util.HashMap<>();
        java.util.Map<Long, java.util.Map<String, Long>> reactionsMap = new java.util.HashMap<>();
        for (Post p : posts) {
            commentsMap.put(p.getId(), commentRepo.findByPostId(p.getId()));
            java.util.Map<String, Long> r = reactionRepo.countByEmojiForPost(p.getId());
            if (r == null) r = new java.util.HashMap<>();
            // ensure common emoji token keys exist so JSP can always display a count
            String[] keys = new String[]{"heart", "thumb", "laugh"};
            for (String k : keys) {
                if (!r.containsKey(k)) r.put(k, 0L);
            }
            System.out.println("[FeedServlet] reactions for post " + p.getId() + " => " + r);
            reactionsMap.put(p.getId(), r);
        }
        req.setAttribute("commentsMap", commentsMap);
        req.setAttribute("reactionsMap", reactionsMap);
        
        // Lấy danh sách người dùng để gợi ý (loại trừ current user)
        List<User> allUsers = userRepo.listAll();
        List<User> suggestedUsers = new ArrayList<>();
        for (User user : allUsers) {
            if (currentUser != null && user.getId().equals(currentUser.getId())) continue;
            suggestedUsers.add(user);
            if (suggestedUsers.size() >= 3) break; // Chỉ lấy 3 người
        }
        req.setAttribute("suggestedUsers", suggestedUsers);
        
        // Thêm follow status cho từng suggested user
        if (currentUser != null) {
            Map<Long, Boolean> followStatusMap = new HashMap<>();
            for (User user : suggestedUsers) {
                boolean isFollowing = followRepo.isFollowing(currentUser.getId(), user.getId());
                followStatusMap.put(user.getId(), isFollowing);
            }
            req.setAttribute("followStatusMap", followStatusMap);
            
            // Thêm số liệu thống kê follow
            long followersCount = followRepo.getFollowersCount(currentUser.getId());
            long followingCount = followRepo.getFollowingCount(currentUser.getId());
            req.setAttribute("followersCount", followersCount);
            req.setAttribute("followingCount", followingCount);
        }
        
        req.getRequestDispatcher("/user/home.jsp").forward(req, resp);
    }
}

