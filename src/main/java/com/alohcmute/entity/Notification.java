package com.alohcmute.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Người nhận thông báo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id", nullable = false)
    private User fromUser; // Người tạo thông báo

    @Column(nullable = false)
    private String type; // LIKE, COMMENT, FOLLOW, SHARE, etc.

    @Column(nullable = false, columnDefinition = "NVARCHAR(1000)")
    private String message;

    @Column(name = "content_id")
    private Long contentId; // ID của post/video/comment được tương tác

    @Column(name = "content_type")
    private String contentType; // POST, VIDEO, COMMENT, USER

    @Column(name = "is_read")
    private boolean read = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Notification() {}

    public Notification(User user, User fromUser, String type, String message) {
        this.user = user;
        this.fromUser = fromUser;
        this.type = type;
        this.message = message;
    }

    public Notification(User user, User fromUser, String type, String message, Long contentId, String contentType) {
        this.user = user;
        this.fromUser = fromUser;
        this.type = type;
        this.message = message;
        this.contentId = contentId;
        this.contentType = contentType;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public User getFromUser() { return fromUser; }
    public void setFromUser(User fromUser) { this.fromUser = fromUser; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Long getContentId() { return contentId; }
    public void setContentId(Long contentId) { this.contentId = contentId; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
