package com.gntour.gangneungyeojido.domain.travel.mapper;

import com.gntour.gangneungyeojido.domain.review.vo.Review;
import com.gntour.gangneungyeojido.app.admin.dto.ReqMarkAddRequest;
import com.gntour.gangneungyeojido.app.main.dto.ReqMarkAddInMainRequest;
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
    List<TravelInfo> selectAllThisMonthTravels();
    int selectAllTravelsCount();
    List<TravelInfo> selectAllTravelsPage(int currentPage, RowBounds rowBounds);
    TravelInfo selectOneTravel(Long travelNo);
    List<ReqMarkAdd> selectAllRequestMark(int currentPage, RowBounds rowBounds);
    int selectAllRequestMarkCount();
    ReqMarkAdd selectOneRequestMark(Long reqMarkAddNo);
    int insertTravel(TravelInfo travelInfo);
    int insertRequestMark(ReqMarkAddInMainRequest reqMarkAddRequest);
    int updateTravel(TravelInfo travelInfo);
    int updateRequestMark(ReqMarkAdd reqMarkAdd);
    int deleteTravel(Long travelNo);
    /**
     * 여행지 별점 받아오는 Service
     */
    Double getScoreByTravelNo(Long travelNo);
}
