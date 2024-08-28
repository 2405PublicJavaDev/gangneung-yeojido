package com.gntour.gangneungyeojido.domain.travel.mapper;

public interface TravelMapper {
    void selectAllTravels();
    void selectAllTravelsPage();
    void selectOneTravel();
    void selectAllRequestMark();
    void selectOneRequestMark();
    void insertRequestMark();
    void updateTravel();
    void updateRequestMark();
    void deleteTravel();
}
