package com.alohcmute.servlet;

import com.alohcmute.entity.Post;
import com.alohcmute.entity.User;
import com.alohcmute.repo.PostRepository;
import com.alohcmute.repo.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet(urlPatterns = {"/dev/seed"})
public class SeedServlet extends HttpServlet {
    private final UserRepository userRepo = new UserRepository();
    private final PostRepository postRepo = new PostRepository();
    private final Random random = new Random();
    
    // Sample data for generating realistic content
    private final String[] firstNames = {
        "Nguyễn", "Trần", "Lê", "Phạm", "Hoàng", "Phan", "Vũ", "Võ", "Đặng", "Bùi",
        "Đỗ", "Hồ", "Ngô", "Dương", "Lý", "Mai", "Đinh", "Tô", "Lâm", "Đoàn"
    };
    
    private final String[] lastNames = {
        "Văn An", "Thị Bình", "Minh Châu", "Quốc Dũng", "Hồng Giang", 
        "Thu Hà", "Tuấn Kiệt", "Thanh Lan", "Hoàng Long", "Phương Mai",
        "Đức Nam", "Thảo Nguyên", "Hải Phong", "Thanh Quân", "Ngọc Sơn",
        "Khánh Tâm", "Minh Tú", "Bảo Uyên", "Anh Vũ", "Thanh Xuân"
    };
    
    private final String[] postContents = {
        "Hôm nay thời tiết thật đẹp! ☀️",
        "Vừa xem một bộ phim hay lắm mọi người ơi! 🎬",
        "Học kỳ này chắc phải cố gắng nhiều thôi 📚",
        "Cafe buổi sáng là tuyệt nhất ☕",
        "Ai đi chơi cuối tuần không? 🎉",
        "Mình vừa hoàn thành project lớn rồi! 🎯",
        "Chia sẻ một số tips học tập hiệu quả nhé 💡",
        "Tuần này bận quá, ai cũng vậy không? 😅",
        "Sắp đến kỳ thi rồi, cùng nhau cố gắng nào! 💪",
        "Mới phát hiện một quán ăn ngon ở gần trường 🍜",
        "Dự án nhóm tuần này deadline rồi! ⏰",
        "Chào buổi sáng! Chúc mọi người một ngày tốt lành 🌅",
        "Code cả đêm mệt quá, nhưng xong rồi! 💻",
        "Share kinh nghiệm làm bài tập lớn cho junior nè 📝",
        "Weekend vibes! Ai đi du lịch không? 🏖️",
        "Thư viện trường đông quá, khó tìm chỗ ngồi 📖",
        "Mình có vài câu hỏi về môn lập trình web 🤔",
        "Vừa tham gia hackathon, trải nghiệm thú vị! 🏆",
        "Chia sẻ tài liệu ôn thi cho ae nhé 📄",
        "Hôm nay có seminar hay, mọi người tham gia không? 🎤",
        "Mình làm intern ở công ty X, ai có câu hỏi không? 💼",
        "Tips để balance giữa học và chơi đây 🎯",
        "Có ai học cùng môn Database không? Cùng study group nào 👥",
        "Presentation hôm nay đã qua rồi! Nhẹ nhõm quá 😌",
        "Mới tải game mới, ai chơi cùng không? 🎮",
        "Tìm teammate cho dự án môn AI, inbox nhé! 🤖",
        "Buổi tối đi gym ai cùng không? 🏋️",
        "Share playlist học bài chill chill 🎵",
        "Mình vừa pass interview, cảm ơn mọi người đã support! 🙏",
        "Food review: Quán cơm gần trường giá sinh viên 🍚"
    };
    
    private final String[] tags = {
        "#coding", "#student", "#HCMUTE", "#university", "#programming",
        "#coffee", "#study", "#project", "#exam", "#weekend",
        "#food", "#travel", "#tech", "#AI", "#web", "#mobile",
        "#design", "#teamwork", "#internship", "#career"
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        Random random = new Random();
        
        out.println("<html><head><meta charset='UTF-8'>");
        out.println("<style>body{font-family:Arial;padding:20px;max-width:1200px;margin:0 auto;} ");
        out.println(".success{color:green;margin:10px 0;} .info{color:blue;margin:10px 0;} ");
        out.println(".stats{background:#f0f0f0;padding:20px;border-radius:8px;margin:20px 0;} ");
        out.println(".accounts{background:#e8f5e9;padding:15px;border-radius:8px;margin:20px 0;} ");
        out.println("h2{color:#333;border-bottom:2px solid #4CAF50;padding-bottom:10px;}</style>");
        out.println("</head><body>");
        out.println("<h1>🌱 ALOHCMUTE Database Seed</h1>");
        
        int usersCreated = 0;
        int postsCreated = 0;
        
        try {
            // Create admin account
            out.println("<h2>👨‍💼 Creating Admin Account</h2>");
            if (userRepo.findByEmail("admin@alohcmute.edu.vn") == null) {
                User admin = new User();
                admin.setEmail("admin@alohcmute.edu.vn");
                admin.setUsername("admin");
                admin.setDisplayName("Administrator");
                admin.setPasswordHash(BCrypt.hashpw("admin123", BCrypt.gensalt()));
                admin.setRole("ADMIN");
                userRepo.save(admin);
                usersCreated++;
                out.println("<div class='success'>✓ Admin account created: admin@alohcmute.edu.vn</div>");
            } else {
                out.println("<div class='info'>ℹ Admin account already exists</div>");
            }
            
            // Create 30 test users
            out.println("<h2>👥 Creating 30 Test Users</h2>");
            String[] bios = {
                "Sinh viên HCMUTE, yêu thích lập trình và công nghệ",
                "Đam mê thiết kế UI/UX và phát triển web",
                "Coffee lover ☕ | Developer 💻",
                "Learning AI and Machine Learning",
                "Passionate about mobile app development",
                "Web developer | Tech enthusiast",
                "Student at HCMUTE | Coder",
                "Future software engineer",
                "Love coding, music, and travel",
                "Building cool stuff with code"
            };
            
            for (int i = 1; i <= 30; i++) {
                String email = "user" + i + "@alohcmute.edu.vn";
                
                if (userRepo.findByEmail(email) == null) {
                    User user = new User();
                    user.setEmail(email);
                    user.setUsername("user" + i);
                    
                    // Generate Vietnamese name
                    String fullName = firstNames[random.nextInt(firstNames.length)] + " " + 
                                    lastNames[random.nextInt(lastNames.length)];
                    user.setDisplayName(fullName);
                    user.setBio(bios[random.nextInt(bios.length)]);
                    user.setPasswordHash(BCrypt.hashpw("password123", BCrypt.gensalt()));
                    user.setRole("USER");
                    userRepo.save(user);
                    usersCreated++;
                    
                    // Create 2-5 random posts for each user
                    int numPosts = 2 + random.nextInt(4); // 2-5 posts
                    for (int j = 0; j < numPosts; j++) {
                        Post post = new Post();
                        post.setAuthor(user);
                        
                    
                    // Random content with 1-3 hashtags
                    String content = postContents[random.nextInt(postContents.length)];
                    int numTags = 1 + random.nextInt(3); // 1-3 tags
                    for (int k = 0; k < numTags; k++) {
                        content += " " + tags[random.nextInt(tags.length)];
                    }
                    post.setContent(content);
                    
                    // 30% chance of having an image
                    if (random.nextInt(100) < 30) {
                        post.setMediaUrl("https://picsum.photos/800/600?random=" + random.nextInt(1000));
                    }
                    
                    postRepo.save(post);
                    postsCreated++;
                }                    if (i % 10 == 0) {
                        out.println("<div class='success'>✓ Created " + i + " users so far...</div>");
                    }
                }
            }
            
            out.println("<div class='success'>✓ All test users created successfully!</div>");
            
            // Display statistics
            out.println("<div class='stats'>");
            out.println("<h2>📊 Seeding Statistics</h2>");
            out.println("<p><strong>Users Created:</strong> " + usersCreated + "</p>");
            out.println("<p><strong>Posts Created:</strong> " + postsCreated + "</p>");
            out.println("<p><strong>Average Posts per User:</strong> " + (usersCreated > 0 ? postsCreated / usersCreated : 0) + "</p>");
            out.println("</div>");
            
            // Display test accounts
            out.println("<div class='accounts'>");
            out.println("<h2>🔑 Test Accounts</h2>");
            out.println("<p><strong>Admin:</strong></p>");
            out.println("<ul>");
            out.println("<li>Email: admin@alohcmute.edu.vn</li>");
            out.println("<li>Password: admin123</li>");
            out.println("</ul>");
            out.println("<p><strong>Test Users (1-30):</strong></p>");
            out.println("<ul>");
            out.println("<li>Email: user1@alohcmute.edu.vn to user30@alohcmute.edu.vn</li>");
            out.println("<li>Password: password123 (same for all)</li>");
            out.println("</ul>");
            out.println("</div>");
            
            // Navigation links
            out.println("<h2>🔗 Quick Links</h2>");
            out.println("<p><a href='/alohcmute/user/home'>Go to Home Page</a></p>");
            out.println("<p><a href='/alohcmute/auth/login'>Go to Login Page</a></p>");
            
        } catch (Exception e) {
            out.println("<div style='color:red;'>❌ Error: " + e.getMessage() + "</div>");
            e.printStackTrace(out);
        }
        
        out.println("</body></html>");
    }
}
