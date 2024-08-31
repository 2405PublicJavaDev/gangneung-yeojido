package com.gntour.gangneungyeojido.domain.travel.mapper;

import com.gntour.gangneungyeojido.domain.travel.vo.TravelInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TravelMapper {
    /**
     * 모든 여행지를 얻어오는 Mapper
     */
    List<TravelInfo> selectAllTravels();
    void selectAllTravelsCount();
    void selectAllTravelsPage();
    void selectOneTravel();
    void selectAllRequestMark();
    void selectAllRequestMarkCount();
    void selectOneRequestMark();
    void insertRequestMark();
    void updateTravel();
    void updateRequestMark();
    void deleteTravel();
}
