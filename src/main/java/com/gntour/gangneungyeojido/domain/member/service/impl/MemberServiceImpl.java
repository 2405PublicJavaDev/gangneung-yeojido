package com.gntour.gangneungyeojido.domain.member.service.impl;

import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.common.exception.BusinessException;
import com.gntour.gangneungyeojido.common.exception.ErrorCode;
import com.gntour.gangneungyeojido.domain.member.mapper.MemberMapper;
import com.gntour.gangneungyeojido.domain.member.service.MemberService;
import com.gntour.gangneungyeojido.domain.member.vo.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper mMapper;

    @Override
    public Member loginMember(Member member) {
        Member result = mMapper.selectOneById(member.getMemberId());
        if (result != null && result.getPassword() != null && result.getPassword().equals(member.getPassword())) {
            return result;
        } else {
            throw new BusinessException(ErrorCode.LOGIN_FAIL);
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
        return mMapper.updateMember(member);
    }

    @Override
    public void removeMember() {

    }

    @Override
    public Page<Member, Void> getAllBlackListMember(int currentPage) {
        return Page.of(currentPage, mMapper.selectAllBlackListMemberCount(), mMapper::selectAllBlackListMember);
    }

    @Override
    public Member getProfileMember(String memberId) {
        return mMapper.selectOneById(memberId);
    }
}
