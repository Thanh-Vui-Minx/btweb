package com.alohcmute.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import com.alohcmute.entity.Report;
import com.alohcmute.entity.User;
import com.alohcmute.entity.Post;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@WebServlet("/seed-reports")
public class SeedReportsServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("alohcmutePU");
            em = emf.createEntityManager();
            
            // Lấy danh sách users
            List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
            if (users.isEmpty()) {
                resp.getWriter().println("<h3>❌ Không có users trong database. Hãy seed users trước!</h3>");
                resp.getWriter().println("<p><a href='" + req.getContextPath() + "/init-seed'>Tạo users mẫu</a></p>");
                return;
            }
            
            // Lấy danh sách posts
            List<Post> posts = em.createQuery("SELECT p FROM Post p", Post.class).getResultList();
            
            // Kiểm tra xem đã có reports chưa
            Long reportCount = em.createQuery("SELECT COUNT(r) FROM Report r", Long.class).getSingleResult();
            if (reportCount > 0) {
                resp.setContentType("text/html; charset=UTF-8");
                resp.getWriter().println("<!DOCTYPE html>");
                resp.getWriter().println("<html><head><meta charset='UTF-8'><title>Reports Already Exist</title></head><body>");
                resp.getWriter().println("<h3>✅ Database đã có " + reportCount + " reports</h3>");
                resp.getWriter().println("<p><a href='" + req.getContextPath() + "/moderator/queue'>Xem Moderation Queue</a></p>");
                resp.getWriter().println("</body></html>");
                return;
            }
            
            em.getTransaction().begin();
            
            String[] reasons = {"SPAM", "HARASSMENT", "VIOLENCE", "SENSITIVE_CONTENT", "HATE_SPEECH", "OTHER"};
            String[] contentTypes = {"POST", "COMMENT", "VIDEO"};
            String[] descriptions = {
                "Nội dung spam quá nhiều",
                "Bài viết có tính chất quấy rối",
                "Nội dung bạo lực không phù hợp",
                "Hình ảnh nhạy cảm",
                "Ngôn từ thù địch, kỳ thị",
                "Vi phạm quy định cộng đồng"
            };
            
            Random random = new Random();
            
            // Tạo 8-12 reports mẫu
            int numReports = 8 + random.nextInt(5); // 8-12 reports
            
            for (int i = 0; i < numReports; i++) {
                Report report = new Report();
                
                // Random reporter (không phải admin)
                User reporter;
                do {
                    reporter = users.get(random.nextInt(users.size()));
                } while ("ADMIN".equals(reporter.getRole()));
                report.setReporter(reporter);
                
                // Random reported user
                if (random.nextBoolean() && users.size() > 1) {
                    User reportedUser;
                    do {
                        reportedUser = users.get(random.nextInt(users.size()));
                    } while (reportedUser.getId().equals(reporter.getId()));
                    report.setReportedUser(reportedUser);
                }
                
                // Random content type và ID
                String contentType = contentTypes[random.nextInt(contentTypes.length)];
                report.setContentType(contentType);
                
                if ("POST".equals(contentType) && !posts.isEmpty()) {
                    Post randomPost = posts.get(random.nextInt(posts.size()));
                    report.setContentId(randomPost.getId());
                } else {
                    report.setContentId((long)(random.nextInt(100) + 1));
                }
                
                // Random reason và description
                int reasonIndex = random.nextInt(reasons.length);
                report.setReason(reasons[reasonIndex]);
                report.setDescription(descriptions[reasonIndex]);
                
                // Status - hầu hết PENDING, một số đã xử lý
                if (i < numReports - 3) {
                    report.setStatus("PENDING");
                } else {
                    String[] statuses = {"RESOLVED", "REJECTED", "REVIEWED"};
                    report.setStatus(statuses[random.nextInt(statuses.length)]);
                    
                    // Nếu đã xử lý, set reviewer
                    if (!"PENDING".equals(report.getStatus())) {
                        User moderator = users.stream()
                            .filter(u -> "MODERATOR".equals(u.getRole()) || "ADMIN".equals(u.getRole()))
                            .findFirst().orElse(users.get(0));
                        report.setReviewedBy(moderator);
                        report.setReviewedAt(LocalDateTime.now().minusHours(random.nextInt(24)));
                        report.setReviewNote("Đã xem xét và xử lý");
                    }
                }
                
                // Random created time (1-7 ngày trước)
                report.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(7)).minusHours(random.nextInt(24)));
                
                em.persist(report);
            }
            
            em.getTransaction().commit();
            
            resp.setContentType("text/html; charset=UTF-8");
            resp.getWriter().println("<!DOCTYPE html>");
            resp.getWriter().println("<html><head><meta charset='UTF-8'><title>Reports Seeded</title>");
            resp.getWriter().println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
            resp.getWriter().println("</head><body>");
            resp.getWriter().println("<div class='container mt-4'>");
            resp.getWriter().println("<h2>✅ Tạo dữ liệu reports thành công!</h2>");
            resp.getWriter().println("<p>Đã tạo <strong>" + numReports + "</strong> reports mẫu gồm:</p>");
            resp.getWriter().println("<ul>");
            resp.getWriter().println("<li>🔴 Reports PENDING: " + (numReports - 3) + "</li>");
            resp.getWriter().println("<li>✅ Reports đã xử lý: 3</li>");
            resp.getWriter().println("<li>📊 Các loại vi phạm: SPAM, Harassment, Violence, Hate Speech...</li>");
            resp.getWriter().println("<li>📝 Content types: POST, COMMENT, VIDEO</li>");
            resp.getWriter().println("</ul>");
            resp.getWriter().println("<div class='mt-4'>");
            resp.getWriter().println("<a href='" + req.getContextPath() + "/moderator/queue' class='btn btn-primary'>");
            resp.getWriter().println("🛡️ Vào Moderation Queue</a> ");
            resp.getWriter().println("<a href='" + req.getContextPath() + "/user/login' class='btn btn-secondary'>");
            resp.getWriter().println("🔑 Đăng nhập Moderator</a>");
            resp.getWriter().println("</div>");
            resp.getWriter().println("<div class='alert alert-info mt-3'>");
            resp.getWriter().println("<strong>💡 Hướng dẫn:</strong><br>");
            resp.getWriter().println("• Đăng nhập với tài khoản <code>moderator/mod123</code> hoặc <code>admin/admin123</code><br>");
            resp.getWriter().println("• Truy cập Moderation Queue để xem các reports cần xử lý<br>");
            resp.getWriter().println("• Có thể Delete Content, Ban User hoặc Reject Report");
            resp.getWriter().println("</div>");
            resp.getWriter().println("</div>");
            resp.getWriter().println("</body></html>");
            
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            resp.setContentType("text/html; charset=UTF-8");
            resp.getWriter().println("<!DOCTYPE html>");
            resp.getWriter().println("<html><head><meta charset='UTF-8'><title>Seed Error</title></head><body>");
            resp.getWriter().println("<h3>❌ Lỗi khi tạo reports: " + e.getMessage() + "</h3>");
            resp.getWriter().println("<pre>");
            e.printStackTrace(resp.getWriter());
            resp.getWriter().println("</pre></body></html>");
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }
}