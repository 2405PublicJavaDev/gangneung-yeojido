package com.gntour.gangneungyeojido.domain.mytravel.serviceImpl;

import com.gntour.gangneungyeojido.domain.mytravel.mapper.TravelDiaryMapper;
import com.gntour.gangneungyeojido.domain.mytravel.service.TravelDiaryService;
import com.gntour.gangneungyeojido.domain.mytravel.vo.TravelDiary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelDiaryServiceImpl implements TravelDiaryService {
    private final TravelDiaryMapper travelDiaryMapper;
    @Override
    public List<TravelDiary> getAllDiariesByMember(String memberId) {
        return travelDiaryMapper.selectAllDiariesByMember(memberId);
    }

    @Override
    public TravelDiary getDetailDiaryByMember(String memberId) {
        return travelDiaryMapper.selectOneDiaryByMember(memberId);
    }

    @Override
    public void addDiary() {

    }

    @Override
    public void modifyDiary() {

    }

    @Override
    public void removeDiary() {

    }
}
