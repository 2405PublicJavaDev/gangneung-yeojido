package com.gntour.gangneungyeojido.domain.mytravel.service;

import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.domain.mytravel.vo.TravelDiary;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TravelDiaryService {

    Page<TravelDiary, String> getAllDiariesByMember(Integer currentPage, String memberId);

    TravelDiary getDetailDiaryByMember(int diaryNo, String memberId);

    int addDiary(TravelDiary travelDiary, List<MultipartFile> multipartFiles);


    int modifyDiary(TravelDiary travelDiary, List<MultipartFile> uploadFiles);
    int removeDiary(String memberId);

}
