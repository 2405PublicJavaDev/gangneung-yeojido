package com.gntour.gangneungyeojido.domain.mytravel.mapper;

import com.gntour.gangneungyeojido.domain.mytravel.vo.TravelDiary;
import com.gntour.gangneungyeojido.domain.mytravel.vo.TravelDiaryFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TravelDiaryMapper {

    // 리스트 조회
    List<TravelDiary> selectAllDiariesByMember(String memberId);

    void selectAllDiariesCountByMember();

    List<TravelDiaryFile> selectAllDiariesFileByDiaryNo(Long diaryNo);

    TravelDiary selectOneDiaryByMember(int diaryNo, String memberId);

    int insertDiary(TravelDiary travelDiary);

    int insertDiaryFile(TravelDiaryFile diaryFile);

    int updateDiary(TravelDiary updatedDiary);

    int deleteDiary(String memberId);

    int deleteDiaryFile(Long diaryNo);
}
