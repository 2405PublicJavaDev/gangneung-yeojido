package com.gntour.gangneungyeojido.domain.member.service;

import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.domain.member.vo.Member;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public interface MemberService {
    Member loginMember(Member member);
    void joinMember();
    void findMemberId();
    void findPassword();
    int modifyMemberInfo(Member member);
    void removeMember();
    Page<Member, Void> getAllBlackListMember(int currentPage);
    Member getProfileMember(String memberId);
}
