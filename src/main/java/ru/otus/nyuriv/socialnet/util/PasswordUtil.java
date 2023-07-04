package ru.otus.nyuriv.socialnet.util;

import org.springframework.util.Base64Utils;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PasswordUtil {
    public static String hash(String pass, String secret) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = StandardCharsets.UTF_8.encode(CharBuffer.wrap(pass)).array();
            md.update(bytes);
            return Base64Utils.encodeToString(md.digest(Base64Utils.decodeFromString(secret)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
