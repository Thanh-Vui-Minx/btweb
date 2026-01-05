package com.alohcmute.util;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Utility class for handling file uploads
 * Files are stored in a persistent directory outside the webapp
 */
public class FileUploadUtil {
    
    // Base upload directory - change this to your desired location
    private static final String BASE_UPLOAD_DIR = "D:/web/trangweb/alohcmute/uploads";
    
    public static final String AVATAR_DIR = "avatars";
    public static final String COVER_DIR = "covers";
    public static final String POST_DIR = "posts";
    public static final String VIDEO_DIR = "videos";
    public static final String THUMBNAIL_DIR = "thumbnails";
    
    /**
     * Initialize upload directories
     */
    public static void initializeDirectories() {
        createDirectory(AVATAR_DIR);
        createDirectory(COVER_DIR);
        createDirectory(POST_DIR);
        createDirectory(VIDEO_DIR);
        createDirectory(THUMBNAIL_DIR);
    }
    
    /**
     * Create a directory if it doesn't exist
     */
    private static void createDirectory(String subDir) {
        File dir = new File(BASE_UPLOAD_DIR, subDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    /**
     * Get the full path for a subdirectory
     */
    public static String getUploadPath(String subDir) {
        return new File(BASE_UPLOAD_DIR, subDir).getAbsolutePath();
    }
    
    /**
     * Save an uploaded file
     * @param part The uploaded file part
     * @param subDir The subdirectory (avatars, posts, etc.)
     * @param prefix Optional prefix for the filename
     * @return The relative URL path to access the file
     */
    public static String saveFile(Part part, String subDir, String prefix) throws IOException {
        if (part == null || part.getSize() == 0) {
            return null;
        }
        
        String originalFilename = part.getSubmittedFileName();
        String extension = "";
        int lastDot = originalFilename.lastIndexOf('.');
        if (lastDot > 0) {
            extension = originalFilename.substring(lastDot);
        }
        
        String uniqueFilename = (prefix != null ? prefix + "_" : "") 
                              + System.currentTimeMillis() + extension;
        
        String uploadPath = getUploadPath(subDir);
        Path filePath = Paths.get(uploadPath, uniqueFilename);
        
        Files.copy(part.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        
        // Return the URL path that can be used in the application
        return "/uploads/" + subDir + "/" + uniqueFilename;
    }
    
    /**
     * Save an uploaded file with user-specific naming
     */
    public static String saveUserFile(Part part, String subDir, Long userId) throws IOException {
        return saveFile(part, subDir, "user" + userId);
    }
    
    /**
     * Delete a file
     */
    public static boolean deleteFile(String relativePath) {
        if (relativePath == null || !relativePath.startsWith("/uploads/")) {
            return false;
        }
        
        // Remove /uploads/ prefix
        String filePath = relativePath.substring("/uploads/".length());
        File file = new File(BASE_UPLOAD_DIR, filePath);
        
        if (file.exists() && file.isFile()) {
            return file.delete();
        }
        return false;
    }
    
    /**
     * Get the base upload directory path
     */
    public static String getBaseUploadDir() {
        return BASE_UPLOAD_DIR;
    }
}
