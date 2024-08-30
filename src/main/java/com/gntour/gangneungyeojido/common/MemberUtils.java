package com.gntour.gangneungyeojido.common;

import jakarta.servlet.http.HttpSession;

public class MemberUtils {
    public static final String MEMBER_ID = "memberId";
    public static final String MEMBER_ROLE = "memberRole";
    public static String getMemberIdFromSession(HttpSession session) {
        return session.getAttribute(MEMBER_ID).toString();
    }
    public static String getMemberRoleFromSession(HttpSession session) {
        return session.getAttribute(MEMBER_ROLE).toString();
    }
}
