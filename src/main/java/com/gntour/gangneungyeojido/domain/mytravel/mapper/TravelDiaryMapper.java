package com.gntour.gangneungyeojido.domain.mytravel.mapper;

public interface TravelDiaryMapper {

    void selectAllDiariesByMember();

    void selectAllDiariesCountByMember();

    void selectOneDiaryByMember();

    void insertDiary();

    void insertDiaryFile();

    void updateDiary();

    void deleteDiary();

    void deleteDiaryFile();
}
