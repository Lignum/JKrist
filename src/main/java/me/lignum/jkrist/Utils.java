package me.lignum.jkrist;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Date;
import java.util.Formatter;

class Utils {
    static String byteArrayToHexString(byte[] bytes) {
        Formatter formatter = new Formatter();

        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }

    static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return byteArrayToHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            // This shouldn't happen.
            e.printStackTrace();
            return null;
        }
    }

    static Date parseISO8601(String date) {
        return Date.from(Instant.parse(date));
    }
}
