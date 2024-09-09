package com.gntour.gangneungyeojido.domain.member.service;

import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.domain.member.vo.Member;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public interface MemberService {
    Member loginMember(Member member);
    int joinMember(Member member);
    Member findMemberId(Member member);
    void findPassword();
    int modifyMemberInfo(Member member);
    int removeMember(String memberId);
    Page<Member, Void> getAllBlackListMember(int currentPage);
    Member getProfileMember(String memberId);
}
