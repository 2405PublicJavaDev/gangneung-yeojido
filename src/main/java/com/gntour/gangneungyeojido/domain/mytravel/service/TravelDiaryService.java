package com.gntour.gangneungyeojido.domain.mytravel.service;

import com.gntour.gangneungyeojido.domain.mytravel.vo.TravelDiary;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TravelDiaryService {

    List<TravelDiary> getAllDiariesByMember(String memberId);

    TravelDiary getDetailDiaryByMember(String memberId);

    int addDiary(TravelDiary travelDiary, MultipartFile uploadFile, String memberIdFromSession);

    void modifyDiary();

    int removeDiary(String memberId);

}
