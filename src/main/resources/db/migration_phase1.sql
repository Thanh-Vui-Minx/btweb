-- Phase 1: MODERATOR Role + Report System Migration
-- Run this SQL script on your LTWEBTUXA1 database

USE LTWEBTUXA1;
GO

-- Create Reports table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Reports')
BEGIN
    CREATE TABLE Reports (
        id BIGINT PRIMARY KEY IDENTITY(1,1),
        reporter_id BIGINT NOT NULL,
        reported_user_id BIGINT NOT NULL,
        content_type NVARCHAR(50) NOT NULL,  -- POST, COMMENT, VIDEO, USER
        content_id BIGINT NULL,               -- ID of the reported content
        reason NVARCHAR(50) NOT NULL,         -- SPAM, HARASSMENT, VIOLENCE, etc.
        description NVARCHAR(MAX) NULL,       -- Additional details from reporter
        status NVARCHAR(50) NOT NULL DEFAULT 'PENDING',  -- PENDING, REVIEWED, RESOLVED, REJECTED
        reviewed_by BIGINT NULL,              -- User ID of moderator who reviewed
        reviewed_at DATETIME NULL,
        review_note NVARCHAR(MAX) NULL,       -- Moderator's notes
        created_at DATETIME NOT NULL DEFAULT GETDATE(),
        
        CONSTRAINT FK_Reports_Reporter FOREIGN KEY (reporter_id) REFERENCES Users(id),
        CONSTRAINT FK_Reports_ReportedUser FOREIGN KEY (reported_user_id) REFERENCES Users(id),
        CONSTRAINT FK_Reports_Reviewer FOREIGN KEY (reviewed_by) REFERENCES Users(id)
    );
    
    PRINT 'Reports table created successfully';
END
ELSE
BEGIN
    PRINT 'Reports table already exists';
END
GO

-- Create indexes for better query performance
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_Reports_Status')
BEGIN
    CREATE INDEX IX_Reports_Status ON Reports(status);
    PRINT 'Index IX_Reports_Status created';
END
GO

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_Reports_ContentType')
BEGIN
    CREATE INDEX IX_Reports_ContentType ON Reports(content_type, content_id);
    PRINT 'Index IX_Reports_ContentType created';
END
GO

-- Add MODERATOR role users (update existing users or create new ones)
-- Example: Update an existing user to MODERATOR
-- UPDATE Users SET role = 'MODERATOR' WHERE username = 'mod1';

-- Or create new moderator accounts
INSERT INTO Users (username, email, password, displayName, role, isActive, createdAt, updatedAt)
VALUES 
    ('moderator1', 'mod1@alohcmute.edu', '$2a$10$YWGl3xJZvnB2RsKjVhMqE.oMQZQqHKqPcYZQpPJgJmYh8PzQxJDYm', 'Moderator One', 'MODERATOR', 1, GETDATE(), GETDATE()),
    ('moderator2', 'mod2@alohcmute.edu', '$2a$10$YWGl3xJZvnB2RsKjVhMqE.oMQZQqHKqPcYZQpPJgJmYh8PzQxJDYm', 'Moderator Two', 'MODERATOR', 1, GETDATE(), GETDATE());
-- Password for both: "password123" (hashed with BCrypt)

PRINT 'Moderator users created';
GO

-- Add sample reports for testing
-- INSERT INTO Reports (reporter_id, reported_user_id, content_type, content_id, reason, description, status)
-- VALUES 
--     (1, 2, 'POST', 5, 'SPAM', 'This post contains spam links', 'PENDING'),
--     (3, 4, 'COMMENT', 12, 'HARASSMENT', 'Offensive comment targeting user', 'PENDING');

PRINT 'Phase 1 migration completed successfully!';
GO
