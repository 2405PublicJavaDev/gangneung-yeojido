package com.gntour.gangneungyeojido.domain.member.service;

public interface MemberService {
    void loginMember();
    void joinMember();
    void findMemberId();
    void findPassword();
    void modifyMemberInfo();
    void removeMember();
    void getAllBlackListMember();
    void getProfileMember();
}
