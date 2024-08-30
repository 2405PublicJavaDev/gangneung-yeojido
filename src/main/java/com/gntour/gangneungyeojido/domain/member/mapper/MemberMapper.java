package com.gntour.gangneungyeojido.domain.member.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void selectOneById();
    void selectAllBlackListMember();
    void selectAllBlackListMemberCount();
    void insertMember();
    void updateMember();
    void deleteMember();
}
