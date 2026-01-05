package com.alohcmute.servlet;

import com.alohcmute.entity.Post;
import com.alohcmute.entity.User;
import com.alohcmute.repo.PostRepository;
import com.alohcmute.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/user/trending"})
public class TrendingServlet extends HttpServlet {
    private final PostRepository postRepo = new PostRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get trending posts from database
        List<Map<String, Object>> trendingPosts = getTrendingPostsFromDatabase();
        req.setAttribute("trendingPosts", trendingPosts);
        
        // Top contributors from database
        List<Map<String, Object>> topContributors = getTopContributorsFromDatabase();
        req.setAttribute("topContributors", topContributors);
        
        req.getRequestDispatcher("/user/trending.jsp").forward(req, resp);
    }

    private List<Map<String, Object>> getTrendingPostsFromDatabase() {
        List<Map<String, Object>> result = new ArrayList<>();
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            // Query to get posts with their reaction and comment counts
            String jpql = "SELECT p, " +
                         "(SELECT COUNT(r) FROM Reaction r WHERE r.post.id = p.id) as reactionCount, " +
                         "(SELECT COUNT(c) FROM Comment c WHERE c.post.id = p.id) as commentCount " +
                         "FROM Post p " +
                         "JOIN FETCH p.author " +
                         "ORDER BY reactionCount DESC, commentCount DESC, p.createdAt DESC";
            
            Query query = em.createQuery(jpql);
            query.setMaxResults(10);
            
            @SuppressWarnings("unchecked")
            List<Object[]> queryResults = query.getResultList();
            
            for (Object[] row : queryResults) {
                Post post = (Post) row[0];
                Long reactionCount = (Long) row[1];
                Long commentCount = (Long) row[2];
                
                Map<String, Object> postMap = new HashMap<>();
                
                // Author info
                Map<String, String> authorMap = new HashMap<>();
                User author = post.getAuthor();
                authorMap.put("displayName", author.getDisplayName() != null ? author.getDisplayName() : author.getUsername());
                authorMap.put("username", author.getUsername());
                authorMap.put("avatarUrl", author.getAvatarUrl() != null ? author.getAvatarUrl() : "");
                postMap.put("author", authorMap);
                
                // Post content
                postMap.put("content", post.getContent());
                postMap.put("mediaUrl", post.getMediaUrl());
                postMap.put("createdAt", formatTimeAgo(post.getCreatedAt()));
                
                // Stats
                long likeCount = reactionCount != null ? reactionCount : 0L;
                long cmtCount = commentCount != null ? commentCount : 0L;
                
                // Calculate trend score: (likes * 2) + (comments * 3)
                long trendScore = (likeCount * 2) + (cmtCount * 3);
                
                postMap.put("likeCount", likeCount);
                postMap.put("commentCount", cmtCount);
                postMap.put("viewCount", "Đang cập nhật"); // View count not yet implemented
                postMap.put("trendScore", trendScore);
                
                result.add(postMap);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        
        return result;
    }

    private List<Map<String, Object>> getTopContributorsFromDatabase() {
        List<Map<String, Object>> result = new ArrayList<>();
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            // Query to get users with most posts
            String jpql = "SELECT u, COUNT(p) as postCount " +
                         "FROM User u " +
                         "LEFT JOIN Post p ON p.author.id = u.id " +
                         "GROUP BY u " +
                         "ORDER BY postCount DESC";
            
            Query query = em.createQuery(jpql);
            query.setMaxResults(5);
            
            @SuppressWarnings("unchecked")
            List<Object[]> queryResults = query.getResultList();
            
            for (Object[] row : queryResults) {
                User user = (User) row[0];
                Long postCount = (Long) row[1];
                
                Map<String, Object> contributorMap = new HashMap<>();
                contributorMap.put("displayName", user.getDisplayName() != null ? user.getDisplayName() : user.getUsername());
                contributorMap.put("avatarUrl", user.getAvatarUrl() != null ? user.getAvatarUrl() : "");
                contributorMap.put("postCount", postCount != null ? postCount : 0L);
                
                result.add(contributorMap);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        
        return result;
    }
    
    private String formatTimeAgo(LocalDateTime dateTime) {
        if (dateTime == null) return "Vừa xong";
        
        Duration duration = Duration.between(dateTime, LocalDateTime.now());
        long seconds = duration.getSeconds();
        
        if (seconds < 60) return "Vừa xong";
        if (seconds < 3600) return (seconds / 60) + " phút trước";
        if (seconds < 86400) return (seconds / 3600) + " giờ trước";
        if (seconds < 604800) return (seconds / 86400) + " ngày trước";
        if (seconds < 2592000) return (seconds / 604800) + " tuần trước";
        
        return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
