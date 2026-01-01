package com.alohcmute.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reactions")
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String emoji; // e.g. ❤️, 👍, 😂

    private String username; // optional user who reacted

    private LocalDateTime createdAt = LocalDateTime.now();

    public Reaction() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }

    public String getEmoji() { return emoji; }
    public void setEmoji(String emoji) { this.emoji = emoji; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
