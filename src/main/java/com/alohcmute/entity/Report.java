package com.alohcmute.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_user_id")
    private User reportedUser;

    @Column(nullable = false)
    private String contentType; // POST, COMMENT, VIDEO, USER

    private Long contentId; // ID của content bị báo cáo

    @Column(nullable = false)
    private String reason; // SPAM, HARASSMENT, VIOLENCE, SENSITIVE_CONTENT, HATE_SPEECH, OTHER

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String status = "PENDING"; // PENDING, REVIEWED, RESOLVED, REJECTED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by")
    private User reviewedBy;

    private LocalDateTime reviewedAt;

    @Column(length = 500)
    private String reviewNote;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Report() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getReporter() { return reporter; }
    public void setReporter(User reporter) { this.reporter = reporter; }

    public User getReportedUser() { return reportedUser; }
    public void setReportedUser(User reportedUser) { this.reportedUser = reportedUser; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public Long getContentId() { return contentId; }
    public void setContentId(Long contentId) { this.contentId = contentId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public User getReviewedBy() { return reviewedBy; }
    public void setReviewedBy(User reviewedBy) { this.reviewedBy = reviewedBy; }

    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }

    public String getReviewNote() { return reviewNote; }
    public void setReviewNote(String reviewNote) { this.reviewNote = reviewNote; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
