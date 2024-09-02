package com.gntour.gangneungyeojido.domain.travel.service;

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
    void getAllTravelsPage();
    TravelInfo getDetailTravel(Long travelNo);
    void getOutlineTravel();
    void getThisMonthTravel();

    Page<ReqMarkAdd, Void> getRequestMarkList(int currentPage);

    void getRequestMarkDetail();
    void addRequestMark();
    int modifyTravel();
    void modifyRequestMark();
    void removeTravel();
}
