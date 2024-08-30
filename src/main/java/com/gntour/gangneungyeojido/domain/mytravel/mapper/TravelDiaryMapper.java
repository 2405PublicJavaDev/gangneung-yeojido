package com.gntour.gangneungyeojido.domain.mytravel.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TravelDiaryMapper {

    void selectAllDiariesByMember();

    void selectAllDiariesCountByMember();

    void selectAllDiariesFileByDiaryNo();

    void selectOneDiaryByMember();

    void insertDiary();

    void insertDiaryFile();

    void updateDiary();

    void deleteDiary();

    void deleteDiaryFile();
}
