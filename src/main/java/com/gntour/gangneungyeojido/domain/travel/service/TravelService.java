package com.gntour.gangneungyeojido.domain.travel.service;

public interface TravelService {
    void getAllTravels();
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
