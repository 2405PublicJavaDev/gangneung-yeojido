package com.gntour.gangneungyeojido.domain.member.service.impl;

import com.gntour.gangneungyeojido.domain.member.mapper.MemberMapper;
import com.gntour.gangneungyeojido.domain.member.service.MemberService;
import com.gntour.gangneungyeojido.domain.member.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper mMapper;

    @Override
    public Member loginMember(Member member) {
        Member result = mMapper.selectOneById(member.getMemberId());
        if(result.getPassword().equals(member.getPassword())) {
            return result;
        } else {
            throw new RuntimeException(); // TODO 에러 핸들링 방식 정하기
        }
    }

    @Override
    public void joinMember() {

    }

    @Override
    public void findMemberId() {

    }

    @Override
    public void findPassword() {

    }

    @Override
    public int modifyMemberInfo(Member member) {
        int result = mMapper.updateMember(member);
        return result;
    }

    @Override
    public void removeMember() {

    }

    @Override
    public void getAllBlackListMember() {

    }

    @Override
    public Member getProfileMember(String memberId) {
        Member result = mMapper.selectOneById(memberId);
        return result;
    }
}
