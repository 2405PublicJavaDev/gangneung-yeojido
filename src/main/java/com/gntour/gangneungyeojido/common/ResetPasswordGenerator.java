package com.gntour.gangneungyeojido.common;

import java.security.SecureRandom;

public class ResetPasswordGenerator {

    // 사용 가능한 문자 집합
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^*+=-";
    private static final String ALL_CHARACTERS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARACTERS;

    private static final int CODE_LENGTH = 8;  // 8~16자리로 설정

    private static final SecureRandom random = new SecureRandom();

    public static String generateVerificationCode() {
        StringBuilder sb = new StringBuilder();

        // 각 조건을 만족하는 문자를 하나씩 추가
        sb.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        sb.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        sb.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        sb.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

        // 나머지 랜덤한 문자 추가
        for (int i = 4; i < CODE_LENGTH; i++) {
            sb.append(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
        }

        // 문자열 섞기
        return shuffleString(sb.toString());
    }

    // 문자열 섞기
    private static String shuffleString(String input) {
        StringBuilder sb = new StringBuilder(input.length());
        char[] characters = input.toCharArray();
        for (int i = input.length() - 1; i >= 0; i--) {
            int index = random.nextInt(i + 1);
            sb.append(characters[index]);
            characters[index] = characters[i];  // 선택된 문자와 마지막 문자를 교환
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // 이메일 인증 코드 생성
        String resetPassword = generateVerificationCode();
        System.out.println("Generated Reset Password: " + resetPassword);
    }
}
