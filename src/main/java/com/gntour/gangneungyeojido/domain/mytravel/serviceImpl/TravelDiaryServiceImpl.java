package com.gntour.gangneungyeojido.domain.mytravel.serviceImpl;

import com.gntour.gangneungyeojido.app.my.dto.MyDiaryResponse;
import com.gntour.gangneungyeojido.common.FileUtil;
import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.common.UploadCategory;
import com.gntour.gangneungyeojido.domain.mytravel.mapper.TravelDiaryMapper;
import com.gntour.gangneungyeojido.domain.mytravel.service.TravelDiaryService;
import com.gntour.gangneungyeojido.domain.mytravel.vo.TravelDiary;
import com.gntour.gangneungyeojido.domain.mytravel.vo.TravelDiaryFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelDiaryServiceImpl implements TravelDiaryService {
    private final TravelDiaryMapper travelDiaryMapper;
    private final FileUtil fileUtil;

    @Override
    public Page<MyDiaryResponse, String> getAllDiariesByMember(Integer currentPage, String memberId) {
        return Page.of(currentPage, travelDiaryMapper.selectAllDiariesCountByMember(memberId), memberId, travelDiaryMapper::selectAllDiariesByMember);
    }

    @Override
    public MyDiaryResponse getDetailDiaryByMember(Long diaryNo, String memberId) {
        return travelDiaryMapper.selectOneDiaryByMember(diaryNo, memberId);
    }

    @Override
    public int addDiary(TravelDiary travelDiary, List<MultipartFile> multipartFiles) {
        int result = travelDiaryMapper.insertDiary(travelDiary);
        try {
            if(multipartFiles != null && multipartFiles.size() > 0) {
                fileUtil.uploadFiles(UploadCategory.DIARY, multipartFiles, travelDiary.getDiaryNo());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int modifyDiary(TravelDiary updatedDiary, List<MultipartFile> uploadFiles) {
        int updateResult = travelDiaryMapper.updateDiary(updatedDiary);
        if (uploadFiles != null && !uploadFiles.isEmpty()) {
            try {
                fileUtil.deleteFiles(UploadCategory.DIARY, updatedDiary.getDiaryNo());
                fileUtil.uploadFiles(UploadCategory.DIARY, uploadFiles, updatedDiary.getDiaryNo());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return updateResult;
    }


    @Override
    public int removeDiary(Long diaryNo, String memberId) {
        int result = 0;
        try {
            result += fileUtil.deleteFiles(UploadCategory.DIARY, diaryNo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        result = travelDiaryMapper.deleteDiary(diaryNo, memberId);
        return result;
    }
}
