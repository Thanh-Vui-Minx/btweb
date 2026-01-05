package com.alohcmute.listener;

import com.alohcmute.util.FileUploadUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Initialize application resources on startup
 */
@WebListener
public class AppInitListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initialize upload directories
        FileUploadUtil.initializeDirectories();
        System.out.println("✓ Upload directories initialized at: " + FileUploadUtil.getBaseUploadDir());
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cleanup if needed
    }
}
