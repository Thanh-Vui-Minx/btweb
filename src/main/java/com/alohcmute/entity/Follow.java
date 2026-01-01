package com.alohcmute.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a follow relationship between two users.
 * follower follows followedUser
 */
@Entity
@Table(name = "follows", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id", "followed_id"}))
public class Follow {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // The user who is following
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;
    
    // The user being followed
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followed_id", nullable = false)
    private User followed;
    
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    public Follow() {}
    
    public Follow(User follower, User followed) {
        this.follower = follower;
        this.followed = followed;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getFollower() {
        return follower;
    }
    
    public void setFollower(User follower) {
        this.follower = follower;
    }
    
    public User getFollowed() {
        return followed;
    }
    
    public void setFollowed(User followed) {
        this.followed = followed;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
