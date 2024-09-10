package com.gntour.gangneungyeojido.domain.mytravel.mapper;

import com.gntour.gangneungyeojido.app.my.dto.MyDiaryResponse;
import com.gntour.gangneungyeojido.domain.mytravel.vo.TravelDiary;
import com.gntour.gangneungyeojido.domain.mytravel.vo.TravelDiaryFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface TravelDiaryMapper {

    // 리스트 조회
    List<MyDiaryResponse> selectAllDiariesByMember(Integer currentPage, String memberId, RowBounds rowBounds);

    int selectAllDiariesCountByMember(String memberId);

    List<TravelDiaryFile> selectAllDiariesFileByDiaryNo(Long diaryNo);

    MyDiaryResponse selectOneDiaryByMember(Long diaryNo, String memberId);

    int insertDiary(TravelDiary travelDiary);

    int insertDiaryFile(TravelDiaryFile diaryFile);

    int updateDiary(TravelDiary updatedDiary);

    int deleteDiary(Long diaryNo, String memberId);

    int deleteDiaryFile(Long diaryNo);
}
