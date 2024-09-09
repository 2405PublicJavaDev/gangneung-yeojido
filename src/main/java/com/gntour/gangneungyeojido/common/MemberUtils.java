package com.gntour.gangneungyeojido.common;

import jakarta.servlet.http.HttpSession;

public class MemberUtils {
    public static final String MEMBER_ID = "memberId";
    public static final String MEMBER_ROLE = "memberRole";
    public static final String MEMBER_STATUS = "memberStatus";
    public static String getMemberIdFromSession(HttpSession session) {
        if(session.getAttribute(MEMBER_ID) == null) {
            return null;
        }
        return session.getAttribute(MEMBER_ID).toString();
    }
    public static MemberRole getMemberRoleFromSession(HttpSession session) {
        if(session.getAttribute(MEMBER_ROLE) == null) {
            return null;
        }
        return MemberRole.valueOf(session.getAttribute(MEMBER_ROLE).toString());
    }
    public static MemberStatus getMemberStatusFromSession(HttpSession session) {
        if(session.getAttribute(MEMBER_STATUS) == null) {
            return null;
        }
        return MemberStatus.valueOf(session.getAttribute(MEMBER_STATUS).toString());
    }
}
