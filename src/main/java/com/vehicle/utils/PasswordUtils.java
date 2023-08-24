package com.vehicle.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

    public static String encryptPassword(String password) {
        try {
            // 创建MessageDigest实例
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            // 将密码转换为字节数组
            byte[] encodedPassword = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            
            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedPassword) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public static void main(String[] args) {
        String password = "123456";
        String encryptedPassword = encryptPassword(password);
        System.out.println("Encrypted Password: " + encryptedPassword);
    }
}