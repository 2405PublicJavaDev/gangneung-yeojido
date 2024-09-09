package com.gntour.gangneungyeojido.domain.mytravel.service;

import com.gntour.gangneungyeojido.app.my.dto.MyDiaryResponse;
import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.domain.mytravel.vo.TravelDiary;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TravelDiaryService {

    Page<MyDiaryResponse, String> getAllDiariesByMember(Integer currentPage, String memberId);

    MyDiaryResponse getDetailDiaryByMember(int diaryNo, String memberId);

    int addDiary(TravelDiary travelDiary, List<MultipartFile> multipartFiles);

    int modifyDiary(TravelDiary updatedDiary, List<MultipartFile> uploadFiles);

    int removeDiary(int diaryNo, String memberId);

    int removeDiaryFiles(int diaryNo);

}
