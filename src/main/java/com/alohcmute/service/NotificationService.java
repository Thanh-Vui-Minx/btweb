package com.alohcmute.service;

import com.alohcmute.entity.Notification;
import com.alohcmute.entity.Post;
import com.alohcmute.entity.User;
import com.alohcmute.entity.Video;
import com.alohcmute.repo.NotificationRepository;

public class NotificationService {
    private final NotificationRepository notificationRepo = new NotificationRepository();

    // Tạo thông báo khi có người like bài viết
    public void createLikeNotification(User postOwner, User liker, Post post) {
        if (postOwner.getId().equals(liker.getId())) {
            return; // Không tạo thông báo cho chính mình
        }
        
        String message = "đã thích bài viết của bạn";
        Notification notification = new Notification(
            postOwner, liker, "LIKE", message, post.getId(), "POST"
        );
        
        // Đảm bảo message được lưu với encoding đúng
        System.out.println("[NotificationService] Creating notification with message: " + message);
        notificationRepo.save(notification);
    }

    // Tạo thông báo khi có người like video
    public void createVideoLikeNotification(User videoOwner, User liker, Video video) {
        if (videoOwner.getId().equals(liker.getId())) {
            return; // Không tạo thông báo cho chính mình
        }
        
        String message = "đã thích video của bạn";
        Notification notification = new Notification(
            videoOwner, liker, "VIDEO_LIKE", message, video.getId(), "VIDEO"
        );
        notificationRepo.save(notification);
    }

    // Tạo thông báo khi có người comment bài viết
    public void createCommentNotification(User postOwner, User commenter, Post post, String commentText) {
        if (postOwner.getId().equals(commenter.getId())) {
            return; // Không tạo thông báo cho chính mình
        }
        
        String truncatedComment = commentText.length() > 50 ? 
            commentText.substring(0, 47) + "..." : commentText;
        String message = "đã bình luận bài viết của bạn: \"" + truncatedComment + "\"";
        Notification notification = new Notification(
            postOwner, commenter, "COMMENT", message, post.getId(), "POST"
        );
        notificationRepo.save(notification);
    }

    // Tạo thông báo khi có người comment video
    public void createVideoCommentNotification(User videoOwner, User commenter, Video video, String commentText) {
        if (videoOwner.getId().equals(commenter.getId())) {
            return; // Không tạo thông báo cho chính mình
        }
        
        String truncatedComment = commentText.length() > 50 ? 
            commentText.substring(0, 47) + "..." : commentText;
        String message = "đã bình luận video của bạn: \"" + truncatedComment + "\"";
        Notification notification = new Notification(
            videoOwner, commenter, "VIDEO_COMMENT", message, video.getId(), "VIDEO"
        );
        notificationRepo.save(notification);
    }

    // Tạo thông báo khi có người follow
    public void createFollowNotification(User followedUser, User follower) {
        if (followedUser.getId().equals(follower.getId())) {
            return; // Không tạo thông báo cho chính mình
        }
        
        String message = "đã bắt đầu theo dõi bạn";
        Notification notification = new Notification(
            followedUser, follower, "FOLLOW", message
        );
        notificationRepo.save(notification);
    }

    // Tạo thông báo khi có người share bài viết
    public void createShareNotification(User postOwner, User sharer, Post post) {
        if (postOwner.getId().equals(sharer.getId())) {
            return; // Không tạo thông báo cho chính mình
        }
        
        String message = "đã chia sẻ bài viết của bạn";
        Notification notification = new Notification(
            postOwner, sharer, "SHARE", message, post.getId(), "POST"
        );
        notificationRepo.save(notification);
    }

    // Tạo thông báo khi có người share video
    public void createVideoShareNotification(User videoOwner, User sharer, Video video) {
        if (videoOwner.getId().equals(sharer.getId())) {
            return; // Không tạo thông báo cho chính mình
        }
        
        String message = "đã chia sẻ video của bạn lên trang cá nhân";
        Notification notification = new Notification(
            videoOwner, sharer, "VIDEO_SHARE", message, video.getId(), "VIDEO"
        );
        notificationRepo.save(notification);
    }
    
    /**
     * Create notification for post reaction (like, love, laugh, etc.)
     */
    public void createPostReactionNotification(User postOwner, User reactor, Post post, String reactionType) {
        // Don't notify if user reacts to their own post
        if (postOwner.getId().equals(reactor.getId())) {
            return;
        }
        
        String emojiMap = getEmojiForReaction(reactionType);
        String message = "đã " + emojiMap + " bài viết của bạn";
        
        // Debug encoding
        try {
            byte[] messageBytes = message.getBytes("UTF-8");
            String reconstructed = new String(messageBytes, "UTF-8");
            System.out.println("[NotificationService] Original message: " + message);
            System.out.println("[NotificationService] Reconstructed message: " + reconstructed);
        } catch (Exception e) {
            System.out.println("[NotificationService] Encoding check failed: " + e.getMessage());
        }
        
        Notification notification = new Notification(
            postOwner, reactor, "POST_REACTION", message, post.getId(), "POST"
        );
        notificationRepo.save(notification);
    }
    
    /**
     * Create notification for post comment
     */
    public void createPostCommentNotification(User postOwner, User commenter, Post post, String commentContent) {
        // Don't notify if user comments on their own post
        if (postOwner.getId().equals(commenter.getId())) {
            return;
        }
        
        // Truncate comment if too long for notification
        String preview = commentContent.length() > 50 
            ? commentContent.substring(0, 47) + "..." 
            : commentContent;
        
        String message = "đã bình luận về bài viết của bạn: \"" + preview + "\"";
        Notification notification = new Notification(
            postOwner, commenter, "POST_COMMENT", message, post.getId(), "POST"
        );
        notificationRepo.save(notification);
    }
    
    private String getEmojiForReaction(String reactionType) {
        switch (reactionType.toLowerCase()) {
            case "heart": return "yêu thích ❤️";
            case "thumb": return "thích 👍";
            case "laugh": return "cười 😆";
            case "angry": return "phẫn nộ 😠";
            case "sad": return "buồn 😢";
            case "wow": return "wow 😮";
            default: return "phản ứng với";
        }
    }
}
