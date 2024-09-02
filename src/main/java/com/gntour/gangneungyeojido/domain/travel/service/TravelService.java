package com.gntour.gangneungyeojido.domain.travel.service;

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
    void getDetailTravel();
    void getOutlineTravel();
    void getThisMonthTravel();
    void getRequestMarkList();
    void getRequestMarkDetail();
    void addRequestMark();
    void modifyTravel();
    void modifyRequestMark();
    void removeTravel();
}
