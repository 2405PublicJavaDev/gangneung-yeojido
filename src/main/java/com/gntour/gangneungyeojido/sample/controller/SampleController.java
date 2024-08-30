package com.gntour.gangneungyeojido.sample.controller;

import com.gntour.gangneungyeojido.common.FileUtil;
import com.gntour.gangneungyeojido.common.UploadCategory;
import com.gntour.gangneungyeojido.common.exception.BusinessException;
import com.gntour.gangneungyeojido.common.exception.ErrorCode;
import com.gntour.gangneungyeojido.sample.dto.SampleSearchCondition;
import com.gntour.gangneungyeojido.sample.dto.SampleWriteDTO;
import com.gntour.gangneungyeojido.sample.service.SampleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SampleController {
    private final FileUtil fileUtil;
    private final SampleService sampleService;
    @GetMapping("/")
    public String sampleAll(Model model) {
        model.addAttribute("samples", sampleService.selectAll());
        throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        // return "sample/home";
    }
    @GetMapping("/page/{currentPage}")
    public String samplePage(@PathVariable int currentPage, Model model) {
        model.addAttribute("samples", sampleService.selectPage(currentPage));
        return "sample/page";
    }

    @GetMapping("/pagesearch/{currentPage}")
    public String samplePageSearch(@PathVariable int currentPage, @ModelAttribute SampleSearchCondition searchCondition, Model model) {
        model.addAttribute("samples", sampleService.selectPageSearch(currentPage, searchCondition));
        return "sample/page";
    }

    @PostMapping("/write")
    @ResponseBody
    public ResponseEntity<String> write(@RequestBody @Valid SampleWriteDTO dto) {
        if("error".equals(dto.getContent())){
            throw new RuntimeException();
        }
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/writePage")
    public String writePage() {
        return "sample/write";
    }

    @PostMapping("/writePage")
    public String writePage(@ModelAttribute @Valid SampleWriteDTO dto, @RequestParam("uploadFile") List<MultipartFile> uploadFiles) throws IOException {
        log.info("size: {}", uploadFiles.size());
        for(MultipartFile file : uploadFiles){
            log.info("file: {}", file.getOriginalFilename());
        }
        sampleService.insertSample(dto, uploadFiles);
        return "sample/home";
    }

    @GetMapping("/deleteSampleFile")
    public String deleteSampleFile() throws IOException {
        fileUtil.deleteFiles(UploadCategory.SAMPLE, 1L);
        return "sample/home";
    }
}
