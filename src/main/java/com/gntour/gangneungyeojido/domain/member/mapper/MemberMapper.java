package com.gntour.gangneungyeojido.domain.member.mapper;

public interface MemberMapper {
    void selectOneById();
    void selectAllBlackListMember();
    void insertMember();
    void updateMember();
    void deleteMember();
}
