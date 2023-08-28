package com.it.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CodeUtil {
    public static String encode() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return currentTime.format(formatter);
    }

    public static String decode(String input) {
        StringBuilder decoded = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                // 对于字母字符，将小写字母还原为对应的 ASCII 字符
                decoded.append((char) ('A' + (c - 'a')));
            } else if (Character.isDigit(c)) {
                // 对于数字字符，直接使用原始数字
                decoded.append(c);
            }
        }
        return decoded.toString();
    }
}
