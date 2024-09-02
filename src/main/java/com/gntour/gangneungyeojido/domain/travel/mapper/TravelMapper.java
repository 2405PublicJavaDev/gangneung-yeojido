package com.gntour.gangneungyeojido.domain.travel.mapper;

import com.gntour.gangneungyeojido.domain.travel.vo.ReqMarkAdd;
import com.gntour.gangneungyeojido.domain.travel.vo.TravelInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface TravelMapper {
    /**
     * 모든 여행지를 얻어오는 Mapper
     */
    List<TravelInfo> selectAllTravels();
    void selectAllTravelsCount();
    void selectAllTravelsPage();
    TravelInfo selectOneTravel(Long travelNo);
    List<ReqMarkAdd> selectAllRequestMark(int currentPage, RowBounds rowBounds);
    int selectAllRequestMarkCount();
    void selectOneRequestMark();
    void insertRequestMark();
    int updateTravel();
    void updateRequestMark();
    void deleteTravel();
}
