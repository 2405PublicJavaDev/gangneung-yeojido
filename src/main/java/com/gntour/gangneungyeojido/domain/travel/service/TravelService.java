package com.gntour.gangneungyeojido.domain.travel.service;

import com.gntour.gangneungyeojido.app.admin.dto.ReqMarkAddRequest;
import com.gntour.gangneungyeojido.app.main.dto.ReqMarkAddInMainRequest;
import com.gntour.gangneungyeojido.app.travel.dto.TravelListResponse;
import com.gntour.gangneungyeojido.app.travel.dto.TravelListSearchCondition;
import com.gntour.gangneungyeojido.app.travel.dto.TravelSearchCondition;
import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.domain.review.vo.Review;
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
    Page<TravelListResponse, TravelListSearchCondition> getAllTravelsPage(int currentPage, int boardLimit, TravelListSearchCondition condition);
    TravelInfo getDetailTravel(Long travelNo);
    List<TravelInfo> getThisMonthTravel();

    Page<ReqMarkAdd, Void> getRequestMarkList(int currentPage);

    ReqMarkAdd getRequestMarkDetail(Long reqMarkAddNo);
    int addRequestMark(ReqMarkAddInMainRequest req);
    int determineRequestMark(ReqMarkAddRequest reqMarkAdd);
    int modifyTravel(TravelInfo travelInfo);
    int removeTravel(Long travelNo);

    List<TravelInfo> selectSearchedTravelsPage(String searchKeyword);
    /**
     * 여행지 별점 받아오는 Service
     */
    Double getScoreByTravelNo(Long travelNo);
}
