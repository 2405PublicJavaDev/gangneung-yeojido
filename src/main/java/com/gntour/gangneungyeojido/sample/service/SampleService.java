package com.gntour.gangneungyeojido.sample.service;

import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.sample.domain.Sample;
import com.gntour.gangneungyeojido.sample.dto.SampleSearchCondition;
import com.gntour.gangneungyeojido.sample.dto.SampleWriteDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SampleService {
    List<Sample> selectAll();

    Page<Sample, Void> selectPage(int currentPage);

    Page<Sample, SampleSearchCondition> selectPageSearch(int currentPage, SampleSearchCondition searchCondition);

    int insertSample(SampleWriteDTO dto, List<MultipartFile> uploadFile) throws IOException;
}
