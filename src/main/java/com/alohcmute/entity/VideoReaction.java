package com.alohcmute.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "video_reactions")
public class VideoReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String reactionType = "LIKE"; // LIKE, LOVE, etc.

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public VideoReaction() {}

    public VideoReaction(Video video, User user, String reactionType) {
        this.video = video;
        this.user = user;
        this.reactionType = reactionType;
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

    public User getUser() { 
        return user; 
    }
    
    public void setUser(User user) { 
        this.user = user; 
    }

    public String getReactionType() { 
        return reactionType; 
    }
    
    public void setReactionType(String reactionType) { 
        this.reactionType = reactionType; 
    }

    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }
}
