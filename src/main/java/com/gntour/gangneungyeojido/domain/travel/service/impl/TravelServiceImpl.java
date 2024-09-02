package com.gntour.gangneungyeojido.domain.travel.service.impl;

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
    public void getRequestMarkDetail() {

    }

    @Override
    public void addRequestMark() {

    }

    @Override
    public int modifyTravel() {
        int result = travelMapper.updateTravel();
        return result;
    }

    @Override
    public void modifyRequestMark() {

    }

    @Override
    public void removeTravel() {

    }
}
