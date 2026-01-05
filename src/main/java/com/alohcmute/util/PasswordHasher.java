package com.alohcmute.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java PasswordHasher <password>");
            return;
        }
        
        String password = args[0];
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        
        System.out.println("Original password: " + password);
        System.out.println("Hashed password: " + hashedPassword);
        System.out.println();
        System.out.println("SQL to insert admin user:");
        System.out.println("INSERT INTO Users (username, email, passwordHash, displayName, role, active, createdAt)");
        System.out.println("VALUES ('admin', 'admin@alohcmute.edu', '" + hashedPassword + "', 'Administrator', 'ADMIN', 1, GETDATE());");
    }
}
