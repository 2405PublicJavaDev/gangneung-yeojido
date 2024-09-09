package com.gntour.gangneungyeojido.domain.member.service.impl;

import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.common.exception.BusinessException;
import com.gntour.gangneungyeojido.common.exception.ErrorCode;
import com.gntour.gangneungyeojido.domain.member.mapper.MemberMapper;
import com.gntour.gangneungyeojido.domain.member.service.MemberService;
import com.gntour.gangneungyeojido.domain.member.vo.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper mMapper;

    @Override
    public Member loginMember(Member member) {
        Member result = mMapper.selectOneById(member.getMemberId());
        if (result != null && result.getPassword() != null && result.getPassword().equals(member.getPassword()) && result.getDeleteYn().equals("N")) {
            return result;
        } else {
            throw new BusinessException(ErrorCode.LOGIN_FAIL);
        }
    }

    @Override
    public int joinMember(Member member) {
        return mMapper.insertMember(member);
    }

    @Override
    public Member findMemberId(Member member) {
        Member result = mMapper.selectOneByName(member.getName());
        if (result != null) {
            // Timestamp를 LocalDate로 변환하여 날짜만 비교
            LocalDate resultDate = result.getBirthDate().toLocalDateTime().toLocalDate();
            LocalDate memberDate = member.getBirthDate().toLocalDateTime().toLocalDate();
            log.info(resultDate.toString());
            log.info(memberDate.toString());
            if (resultDate.equals(memberDate)) {
                return result;
            }
        }
        throw new BusinessException(ErrorCode.ID_FIND_FAIL);
    }

    @Override
    public void findPassword() {

    }

    @Override
    public int modifyMemberInfo(Member member) {
        return mMapper.updateMember(member);
    }

    @Override
    public int removeMember(String memberId) {
        return mMapper.deleteMember(memberId);
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
