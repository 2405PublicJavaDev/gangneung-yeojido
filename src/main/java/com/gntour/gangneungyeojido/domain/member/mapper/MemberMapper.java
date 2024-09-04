package com.gntour.gangneungyeojido.domain.member.mapper;

import com.gntour.gangneungyeojido.domain.member.vo.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface MemberMapper {
    Member selectOneById(String memberId);
    List<Member> selectAllBlackListMember(Integer currentPage, RowBounds rowBounds);
    int selectAllBlackListMemberCount();
    int insertMember(Member member);
    int updateMember(Member member);
    void deleteMember();
}
