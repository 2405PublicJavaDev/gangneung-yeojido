package com.gntour.gangneungyeojido.domain.mytravel.serviceImpl;

import com.gntour.gangneungyeojido.common.FileUtil;
import com.gntour.gangneungyeojido.common.UploadCategory;
import com.gntour.gangneungyeojido.domain.mytravel.mapper.TravelDiaryMapper;
import com.gntour.gangneungyeojido.domain.mytravel.service.TravelDiaryService;
import com.gntour.gangneungyeojido.domain.mytravel.vo.TravelDiary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelDiaryServiceImpl implements TravelDiaryService {
    private final TravelDiaryMapper travelDiaryMapper;
    private final FileUtil fileUtil;

    @Override
    public List<TravelDiary> getAllDiariesByMember(String memberId) {
        return travelDiaryMapper.selectAllDiariesByMember(memberId);
    }

    @Override
    public TravelDiary getDetailDiaryByMember(String memberId) {
        return travelDiaryMapper.selectOneDiaryByMember(memberId);
    }

    @Override
    public int addDiary(TravelDiary travelDiary, List<MultipartFile> multipartFiles) {
        int result = travelDiaryMapper.insertDiary(travelDiary);
        try {
            fileUtil.uploadFiles(UploadCategory.DIARY, multipartFiles, travelDiary.getDiaryNo());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void modifyDiary() {

    }

    @Override
    public int removeDiary(String memberId) {
        int result = travelDiaryMapper.deleteDiary(memberId);
        return result;
    }

}
