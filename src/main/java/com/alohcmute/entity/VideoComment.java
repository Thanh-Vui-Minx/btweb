package com.alohcmute.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "video_comments")
public class VideoComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public VideoComment() {}

    public VideoComment(Video video, User author, String content) {
        this.video = video;
        this.author = author;
        this.content = content;
    }

    // Getters and Setters
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }

    public Video getVideo() { 
        return video; 
    }
    
    public void setVideo(Video video) { 
        this.video = video; 
    }

    public User getAuthor() { 
        return author; 
    }
    
    public void setAuthor(User author) { 
        this.author = author; 
    }

    public String getContent() { 
        return content; 
    }
    
    public void setContent(String content) { 
        this.content = content; 
    }

    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }
}
