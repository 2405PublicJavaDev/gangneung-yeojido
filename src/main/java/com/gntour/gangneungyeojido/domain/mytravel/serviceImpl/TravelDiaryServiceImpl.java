package com.gntour.gangneungyeojido.domain.mytravel.serviceImpl;

import com.gntour.gangneungyeojido.common.FileUtil;
import com.gntour.gangneungyeojido.common.Page;
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
    public Page<TravelDiary, String> getAllDiariesByMember(Integer currentPage, String memberId) {
        return Page.of(currentPage, travelDiaryMapper.selectAllDiariesCountByMember(memberId), memberId, travelDiaryMapper::selectAllDiariesByMember);
    }

    @Override
    public TravelDiary getDetailDiaryByMember(int diaryNo, String memberId) {
        return travelDiaryMapper.selectOneDiaryByMember(diaryNo, memberId);
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
    public int modifyDiary(TravelDiary updatedDiary, List<MultipartFile> multipartFiles) {
        // 1. 기존 데이터 업데이트
        int result = travelDiaryMapper.updateDiary(updatedDiary);

        // 2. 기존 파일 삭제 (필요한 경우)
        travelDiaryMapper.deleteDiaryFile(updatedDiary.getDiaryNo());

        // 3. 새 파일 업로드 처리
        try {
            fileUtil.uploadFiles(UploadCategory.DIARY, multipartFiles, updatedDiary.getDiaryNo());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    @Override
    public int removeDiary(String memberId) {
        int result = travelDiaryMapper.deleteDiary(memberId);
        return result;
    }



}
