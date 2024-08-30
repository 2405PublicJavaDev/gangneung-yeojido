package com.gntour.gangneungyeojido.common;

import jakarta.servlet.http.HttpSession;

public class Utils {
    public static String getMemberIdFromSession(HttpSession session) {
        return session.getAttribute("memberId").toString();
    }
}
