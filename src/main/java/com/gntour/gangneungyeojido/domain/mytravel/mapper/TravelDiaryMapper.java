package com.gntour.gangneungyeojido.domain.mytravel.mapper;

import com.gntour.gangneungyeojido.domain.mytravel.vo.TravelDiary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TravelDiaryMapper {

    List<TravelDiary> selectAllDiariesByMember(String memberId);

    void selectAllDiariesCountByMember();

    void selectAllDiariesFileByDiaryNo();

    TravelDiary selectOneDiaryByMember(String memberId);

    void insertDiary();

    void insertDiaryFile();

    void updateDiary();

    void deleteDiary();

    void deleteDiaryFile();
}
