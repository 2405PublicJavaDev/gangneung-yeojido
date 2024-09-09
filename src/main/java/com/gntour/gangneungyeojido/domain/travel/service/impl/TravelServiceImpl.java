package com.gntour.gangneungyeojido.domain.travel.service.impl;

import com.gntour.gangneungyeojido.app.admin.dto.ReqMarkAddRequest;
import com.gntour.gangneungyeojido.app.main.dto.ReqMarkAddInMainRequest;
import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.domain.review.vo.Review;
import com.gntour.gangneungyeojido.domain.travel.mapper.TravelMapper;
import com.gntour.gangneungyeojido.domain.travel.service.TravelService;
import com.gntour.gangneungyeojido.domain.travel.vo.ReqMarkAdd;
import com.gntour.gangneungyeojido.domain.travel.vo.TravelInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TravelServiceImpl implements TravelService {
    private final TravelMapper travelMapper;

    public List<TravelInfo> getAllTravels() {
        List<TravelInfo> tList = travelMapper.selectAllTravels();
        return tList;
    }

    @Override
    public Page<TravelInfo, Void> getAllTravelsPage(int currentPage, int boardLimit) {
        return Page.of(currentPage, travelMapper.selectAllTravelsCount(), travelMapper::selectAllTravelsPage, boardLimit,10);
    }

    @Override
    public TravelInfo getDetailTravel(Long travelNo) {
        return travelMapper.selectOneTravel(travelNo);
    }

    @Override
    public List<TravelInfo> getThisMonthTravel() {
        return travelMapper.selectAllThisMonthTravels();
    }

    @Override
    public Page<ReqMarkAdd, Void> getRequestMarkList(int currentPage) {
        return Page.of(currentPage, travelMapper.selectAllRequestMarkCount(), travelMapper::selectAllRequestMark);
    }

    @Override
    public ReqMarkAdd getRequestMarkDetail(Long reqMarkAddNo) {
        return travelMapper.selectOneRequestMark(reqMarkAddNo);
    }

    @Override
    public int addRequestMark(ReqMarkAddInMainRequest req) {
        return travelMapper.insertRequestMark(req);
    }

    @Override
    public int determineRequestMark(ReqMarkAddRequest reqMarkAddRequest) {
        int result = 0;
        ReqMarkAdd reqMarkAdd = new ReqMarkAdd();
        reqMarkAdd.setReqMarkAddNo(reqMarkAddRequest.getReqMarkAddNo());
        reqMarkAdd.setLatitude(reqMarkAddRequest.getLatitude());
        reqMarkAdd.setLongitude(reqMarkAddRequest.getLongitude());
        reqMarkAdd.setTravelName(reqMarkAddRequest.getTravelName());
        reqMarkAdd.setAddress(reqMarkAddRequest.getAddress());
        reqMarkAdd.setPhone(reqMarkAddRequest.getPhone());
        reqMarkAdd.setUseTime(reqMarkAddRequest.getUseTime());
        reqMarkAdd.setParkFee(reqMarkAddRequest.getParkFee());
        reqMarkAdd.setEntryFee(reqMarkAddRequest.getEntryFee());
        reqMarkAdd.setUseFee(reqMarkAddRequest.getUseFee());
        reqMarkAdd.setIntroduce(reqMarkAddRequest.getIntroduce());
        reqMarkAdd.setSiteUrl(reqMarkAddRequest.getSiteUrl());
        reqMarkAdd.setImageUrl(reqMarkAddRequest.getImageUrl());
        if(reqMarkAddRequest.getIsAccepted()) {
            reqMarkAdd.setAcceptableStatus("Y");
            result += travelMapper.updateRequestMark(reqMarkAdd);
            TravelInfo travelInfo = new TravelInfo();
            travelInfo.setLatitude(reqMarkAddRequest.getLatitude());
            travelInfo.setLongitude(reqMarkAddRequest.getLongitude());
            travelInfo.setTravelName(reqMarkAddRequest.getTravelName());
            travelInfo.setAddress(reqMarkAddRequest.getAddress());
            travelInfo.setPhone(reqMarkAddRequest.getPhone());
            travelInfo.setUseTime(reqMarkAddRequest.getUseTime());
            travelInfo.setParkFee(reqMarkAddRequest.getParkFee());
            travelInfo.setEntryFee(reqMarkAddRequest.getEntryFee());
            travelInfo.setUseFee(reqMarkAddRequest.getUseFee());
            travelInfo.setSiteUrl(reqMarkAddRequest.getSiteUrl());
            travelInfo.setImageUrl(reqMarkAddRequest.getImageUrl());
            travelInfo.setRegion(reqMarkAddRequest.getRegion());
            travelInfo.setCategory(reqMarkAddRequest.getCategory());
            travelInfo.setIntroduce(reqMarkAddRequest.getIntroduce());
            if(reqMarkAddRequest.getZoomLevel() == null) {
                travelInfo.setZoomLevel(1L);
            } else {
                travelInfo.setZoomLevel(reqMarkAddRequest.getZoomLevel());
            }
            result += travelMapper.insertTravel(travelInfo);
        } else {
            reqMarkAdd.setAcceptableStatus("N");
            result += travelMapper.updateRequestMark(reqMarkAdd);
        }
        return result;
    }

    @Override
    public int modifyTravel(TravelInfo travelInfo) {
        return travelMapper.updateTravel(travelInfo);
    }

    @Override
    public int removeTravel(Long travelNo) {
        return travelMapper.deleteTravel(travelNo);
    }

    @Override
    public List<TravelInfo> selectSearchedTravelsPage(String searchKeyword) {
        return travelMapper.selectSearchedTravels(searchKeyword);
    };

    @Override
    public Double getScoreByTravelNo(Long travelNo) {
        return travelMapper.getScoreByTravelNo(travelNo);
    }
}
