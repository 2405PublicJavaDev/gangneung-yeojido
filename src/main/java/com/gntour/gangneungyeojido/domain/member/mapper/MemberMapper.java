package com.gntour.gangneungyeojido.domain.member.mapper;

import com.gntour.gangneungyeojido.domain.member.vo.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    Member selectOneById(String memberId);
    void selectAllBlackListMember();
    void selectAllBlackListMemberCount();
    void insertMember();
    int updateMember(Member member);
    void deleteMember();
}
