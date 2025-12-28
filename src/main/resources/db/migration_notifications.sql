-- Notifications System Migration
-- Run this SQL script on your LTWEBTUXA1 database to add notifications table

USE LTWEBTUXA1;
GO

-- Create Notifications table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Notifications')
BEGIN
    CREATE TABLE Notifications (
        id BIGINT PRIMARY KEY IDENTITY(1,1),
        user_id BIGINT NOT NULL,               -- Who receives the notification
        type NVARCHAR(50) NOT NULL,            -- VIDEO_LIKE, VIDEO_COMMENT, VIDEO_SHARE, NEW_FOLLOWER, etc.
        message NVARCHAR(500) NOT NULL,        -- Notification message
        content NVARCHAR(MAX) NULL,            -- Additional content (JSON or text)
        related_user_id BIGINT NULL,           -- User who triggered the notification
        related_video_id BIGINT NULL,          -- Video related to the notification (if applicable)
        related_post_id BIGINT NULL,           -- Post related to the notification (if applicable)
        is_read BIT NOT NULL DEFAULT 0,        -- Whether the notification has been read
        created_at DATETIME NOT NULL DEFAULT GETDATE(),
        read_at DATETIME NULL,                 -- When the notification was read
        
        CONSTRAINT FK_Notifications_User FOREIGN KEY (user_id) REFERENCES Users(id),
        CONSTRAINT FK_Notifications_RelatedUser FOREIGN KEY (related_user_id) REFERENCES Users(id),
        CONSTRAINT FK_Notifications_RelatedVideo FOREIGN KEY (related_video_id) REFERENCES Videos(id),
        CONSTRAINT FK_Notifications_RelatedPost FOREIGN KEY (related_post_id) REFERENCES Posts(id)
    );
    
    PRINT 'Notifications table created successfully';
END
ELSE
BEGIN
    PRINT 'Notifications table already exists';
END
GO

-- Create indexes for better query performance
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_Notifications_User')
BEGIN
    CREATE INDEX IX_Notifications_User ON Notifications(user_id, created_at DESC);
    PRINT 'Index IX_Notifications_User created';
END
GO

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_Notifications_IsRead')
BEGIN
    CREATE INDEX IX_Notifications_IsRead ON Notifications(user_id, is_read);
    PRINT 'Index IX_Notifications_IsRead created';
END
GO

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_Notifications_Type')
BEGIN
    CREATE INDEX IX_Notifications_Type ON Notifications(type, created_at DESC);
    PRINT 'Index IX_Notifications_Type created';
END
GO

-- Create trigger to update read_at when is_read is set to true
IF NOT EXISTS (SELECT * FROM sys.triggers WHERE name = 'TR_Notifications_ReadAt')
BEGIN
    EXEC('
    CREATE TRIGGER TR_Notifications_ReadAt
    ON Notifications
    AFTER UPDATE
    AS
    BEGIN
        IF UPDATE(is_read)
        BEGIN
            UPDATE Notifications
            SET read_at = CASE 
                WHEN i.is_read = 1 AND d.is_read = 0 THEN GETDATE()
                WHEN i.is_read = 0 AND d.is_read = 1 THEN NULL
                ELSE read_at
            END
            FROM Notifications n
            INNER JOIN inserted i ON n.id = i.id
            INNER JOIN deleted d ON n.id = d.id
            WHERE i.is_read != d.is_read
        END
    END
    ');
    
    PRINT 'Trigger TR_Notifications_ReadAt created';
END
GO

PRINT 'Notifications system migration completed successfully!';
GO

-- Sample notifications for testing (uncomment if needed)
/*
-- Sample notifications data
INSERT INTO Notifications (user_id, type, message, related_user_id, related_video_id, is_read)
VALUES 
    (1, 'VIDEO_LIKE', 'John Doe đã thích video của bạn', 2, 5, 0),
    (1, 'VIDEO_COMMENT', 'Jane Smith đã bình luận về video của bạn', 3, 5, 0),
    (2, 'NEW_FOLLOWER', 'Mike Johnson đã bắt đầu theo dõi bạn', 4, NULL, 1),
    (1, 'VIDEO_SHARE', 'Sarah Wilson đã chia sẻ video của bạn', 5, 5, 0);

PRINT 'Sample notifications created';
*/