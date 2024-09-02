package com.gntour.gangneungyeojido.domain.travel.service;

import com.gntour.gangneungyeojido.app.admin.dto.ReqMarkAddRequest;
import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.domain.travel.vo.ReqMarkAdd;
import com.gntour.gangneungyeojido.domain.travel.vo.TravelInfo;

import java.util.List;

public interface TravelService {
    /**
     * 모든 여행지 정보를 얻어오는 Service
     *
     * @return
     */
    List<TravelInfo> getAllTravels();

    /**
     * 여행지 정보 Page Service
     */
    Page<TravelInfo, Void> getAllTravelsPage(int currentPage);
    TravelInfo getDetailTravel(Long travelNo);
    void getThisMonthTravel();

    Page<ReqMarkAdd, Void> getRequestMarkList(int currentPage);

    ReqMarkAdd getRequestMarkDetail(Long reqMarkAddNo);
    void addRequestMark();
    int determineRequestMark(ReqMarkAddRequest reqMarkAdd);
    int modifyTravel();
    void removeTravel();
}
