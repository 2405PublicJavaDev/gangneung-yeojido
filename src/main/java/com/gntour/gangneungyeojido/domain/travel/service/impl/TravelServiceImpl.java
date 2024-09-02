package com.gntour.gangneungyeojido.domain.travel.service.impl;

import com.gntour.gangneungyeojido.app.admin.dto.ReqMarkAddRequest;
import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.domain.travel.mapper.TravelMapper;
import com.gntour.gangneungyeojido.domain.travel.service.TravelService;
import com.gntour.gangneungyeojido.domain.travel.vo.ReqMarkAdd;
import com.gntour.gangneungyeojido.domain.travel.vo.TravelInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TravelServiceImpl implements TravelService {
    private final TravelMapper travelMapper;

    @Override
    public List<TravelInfo> getAllTravels() {
        return travelMapper.selectAllTravels();
    }

    @Override
    public void getAllTravelsPage() {

    }

    @Override
    public TravelInfo getDetailTravel(Long travelNo) {
        TravelInfo travelInfo = travelMapper.selectOneTravel(travelNo);
        return travelInfo;
    }

    @Override
    public void getOutlineTravel() {

    }

    @Override
    public void getThisMonthTravel() {

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
    public void addRequestMark() {

    }

    @Override
    public int determineRequestMark(ReqMarkAddRequest reqMarkAddRequest) {
        int result = 0;
        ReqMarkAdd reqMarkAdd = new ReqMarkAdd();
        reqMarkAdd.setReqMarkAddNo(reqMarkAddRequest.getReqMarkAddNo());
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
//            TravelInfo travelInfo = new TravelInfo();
//            travelInfo.setTravelName(reqMarkAddRequest.getTravelName());
//            travelInfo.setAddress(reqMarkAddRequest.getAddress());
//            travelInfo.setPhone(reqMarkAddRequest.getPhone());
//            travelInfo.setUseTime(reqMarkAddRequest.getUseTime());
//            travelInfo.setParkFee(reqMarkAddRequest.getParkFee());
//            travelInfo.setEntryFee(reqMarkAddRequest.getEntryFee());
//            travelInfo.setUseFee(reqMarkAddRequest.getUseFee());
//            travelInfo.setSiteUrl(reqMarkAddRequest.getSiteUrl());
//            travelInfo.setImageUrl(reqMarkAddRequest.getImageUrl());
//            result += travelMapper.insertTravel(travelInfo);
        } else {
            reqMarkAdd.setAcceptableStatus("N");
            result += travelMapper.updateRequestMark(reqMarkAdd);
        }
        return result;
    }

    @Override
    public int modifyTravel() {
        int result = travelMapper.updateTravel();
        return result;
    }

    @Override
    public void removeTravel() {

    }
}
