package com.alohcmute.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import com.alohcmute.entity.Post;
import com.alohcmute.entity.User;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@WebServlet("/seed-posts")
public class SeedPostsServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("alohcmutePU");
            em = emf.createEntityManager();
            
            // Lấy danh sách users
            List<User> users = em.createQuery("SELECT u FROM User u WHERE u.role = 'USER'", User.class).getResultList();
            if (users.isEmpty()) {
                resp.getWriter().println("<h3>❌ Không có users trong database. Hãy seed users trước!</h3>");
                return;
            }
            
            // Kiểm tra xem đã có posts chưa
            Long postCount = em.createQuery("SELECT COUNT(p) FROM Post p", Long.class).getSingleResult();
            if (postCount > 0) {
                resp.getWriter().println("<h3>✅ Database đã có " + postCount + " posts</h3>");
                resp.getWriter().println("<p><a href='" + req.getContextPath() + "/seed-reports'>Seed Reports</a></p>");
                return;
            }
            
            em.getTransaction().begin();
            
            String[] postContents = {
                "Hôm nay thời tiết đẹp quá! 🌞 #weather #beautiful",
                "Vừa ăn xong món bánh mì siêu ngon tại quán góc phố 🥖",
                "Coding suốt đêm để hoàn thành dự án 💻 #developer #coding",
                "Cuối tuần rảnh rồi, ai rủ đi cà phê không? ☕",
                "Mới xem xong bộ phim hay lắm, recommend cho mọi người! 🎬",
                "Traffic jam kinh khủng vào giờ tan tầm 😵 #traffic",
                "Học Java 21 LTS rất thú vị, nhiều tính năng mới! ☕👨‍💻",
                "Weekend vibes! Ai có kế hoạch gì không? 🎉",
                "Ăn phở sáng cho ngày mới tràn đầy năng lượng 🍜",
                "Deadline đến gần rồi, stress quá đi! 😰 #work #deadline"
            };
            
            Random random = new Random();
            
            // Tạo 15-20 posts
            int numPosts = 15 + random.nextInt(6);
            
            for (int i = 0; i < numPosts; i++) {
                Post post = new Post();
                
                // Random author
                User author = users.get(random.nextInt(users.size()));
                post.setAuthor(author);
                
                // Random content
                post.setContent(postContents[random.nextInt(postContents.length)]);
                
                // Random media URL (30% có media)
                if (random.nextInt(10) < 3) {
                    String[] mediaUrls = {
                        "/uploads/images/sample1.jpg",
                        "/uploads/images/sample2.jpg", 
                        "/uploads/videos/sample1.mp4"
                    };
                    post.setMediaUrl(mediaUrls[random.nextInt(mediaUrls.length)]);
                }
                
                // Random created time (1-30 ngày trước)
                post.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(30)));
                
                em.persist(post);
            }
            
            em.getTransaction().commit();
            
            resp.setContentType("text/html; charset=UTF-8");
            resp.getWriter().println("<!DOCTYPE html>");
            resp.getWriter().println("<html><head><meta charset='UTF-8'><title>Posts Seeded</title>");
            resp.getWriter().println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
            resp.getWriter().println("</head><body>");
            resp.getWriter().println("<div class='container mt-4'>");
            resp.getWriter().println("<h2>✅ Tạo dữ liệu posts thành công!</h2>");
            resp.getWriter().println("<p>Đã tạo <strong>" + numPosts + "</strong> posts mẫu với:</p>");
            resp.getWriter().println("<ul>");
            resp.getWriter().println("<li>📝 Nội dung đa dạng từ users</li>");
            resp.getWriter().println("<li>👥 Random tác giả từ user list</li>");
            resp.getWriter().println("<li>�️ 30% posts có media (images/videos)</li>");
            resp.getWriter().println("<li>📅 Thời gian tạo từ 1-30 ngày trước</li>");
            resp.getWriter().println("</ul>");
            resp.getWriter().println("<div class='mt-4'>");
            resp.getWriter().println("<a href='" + req.getContextPath() + "/seed-reports' class='btn btn-primary'>");
            resp.getWriter().println("🚨 Seed Reports cho Moderation</a> ");
            resp.getWriter().println("<a href='" + req.getContextPath() + "/user/home' class='btn btn-secondary'>");
            resp.getWriter().println("🏠 Xem Home Page</a>");
            resp.getWriter().println("</div>");
            resp.getWriter().println("</div>");
            resp.getWriter().println("</body></html>");
            
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            resp.setContentType("text/html; charset=UTF-8");
            resp.getWriter().println("<h3>❌ Lỗi khi tạo posts: " + e.getMessage() + "</h3>");
            resp.getWriter().println("<pre>");
            e.printStackTrace(resp.getWriter());
            resp.getWriter().println("</pre>");
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }
}