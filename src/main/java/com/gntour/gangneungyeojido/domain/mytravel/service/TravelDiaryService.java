package com.gntour.gangneungyeojido.domain.mytravel.service;

import com.gntour.gangneungyeojido.domain.mytravel.vo.TravelDiary;

import java.util.List;

public interface TravelDiaryService {

    List<TravelDiary> getAllDiariesByMember(String memberId);

    TravelDiary getDetailDiaryByMember(String memberId);

    void addDiary();

    void modifyDiary();

    void removeDiary();


}
