package com.gntour.gangneungyeojido.domain.member.service;

import com.gntour.gangneungyeojido.domain.member.vo.Member;
import org.apache.ibatis.session.SqlSession;

public interface MemberService {
    Member loginMember(Member member);
    void joinMember();
    void findMemberId();
    void findPassword();
    int modifyMemberInfo(Member member);
    void removeMember();
    void getAllBlackListMember();
    Member getProfileMember(String memberId);
}
