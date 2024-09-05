package com.gntour.gangneungyeojido.domain.mytravel.mapper;

import com.gntour.gangneungyeojido.domain.mytravel.vo.TravelDiary;
import com.gntour.gangneungyeojido.domain.mytravel.vo.TravelDiaryFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TravelDiaryMapper {

    List<TravelDiary> selectAllDiariesByMember(String memberId);

    void selectAllDiariesCountByMember();

    List<TravelDiaryFile> selectAllDiariesFileByDiaryNo(Long diaryNo);

    TravelDiary selectOneDiaryByMember(String memberId);

    void insertDiary();

    int insertDiaryFile(TravelDiaryFile diaryFile);

    void updateDiary();

    void deleteDiary();

    int deleteDiaryFile(Long diaryNo);
}
