package com.gntour.gangneungyeojido.domain.mytravel.mapper;

public interface TravelDiaryMapper {

    void selectAllDiaryByMember();

    void selectAllDiaryCountByMember();

    void insertDiary();

    void updateDiary();

    void deleteDiary();
}
