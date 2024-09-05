package com.gntour.gangneungyeojido.app.admin;

import com.gntour.gangneungyeojido.app.notice.dto.NoticeSearchCondition;
import com.gntour.gangneungyeojido.common.MemberUtils;
import com.gntour.gangneungyeojido.domain.notice.service.NoticeService;
import com.gntour.gangneungyeojido.domain.notice.vo.Notice;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminNoticeController {
    private final NoticeService noticeService;

    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [관리자 기능(페이지 폼)] 공지사항 등록
     */
    @GetMapping("/admin/notice/register")
    public String showRegisterNoticePage(){
        return "admin/notice-register";
    }

    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [관리자 기능(페이지 폼)] 공지사항 수정용 리스트*/
    @GetMapping("/admin/notice")
    public String showNoticeListPage(
            Model model
            , @RequestParam(value="currentPage", defaultValue = "1") Integer currentPage
            , @RequestParam(value = "searchType", required = false) String searchType
            , @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {
        NoticeSearchCondition condition = new NoticeSearchCondition();
        if ("title".equals(searchType)) {
            condition.setTitle(searchKeyword);
        } else if ("content".equals(searchType)) {
            condition.setContent(searchKeyword);
        }
        log.info("Search Condition - Title: {}, Content: {}", condition.getTitle(), condition.getContent());
        model.addAttribute("page", noticeService.getAllNotices(currentPage, condition));
        return "admin/notice-update-list";
    }


    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [관리자 기능(페이지 폼)] 공지사항 수정
     */
    @GetMapping("/admin/notice/modify/{noticeNo}")
    public String showUpdateNoticePage(Model model
            , @PathVariable("noticeNo") Long noticeNo){
        Notice notice = noticeService.getDetailNotice(noticeNo);
        model.addAttribute("notice", notice);
        return "admin/notice-update";
    }

    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [관리자 기능] 공지사항 등록
     */
    @PostMapping("/admin/notice/register")
    public String addNotice(HttpSession session, Notice notice){
        notice.setAdminId(MemberUtils.getMemberIdFromSession(session));
        notice.setImportantYn(notice.getImportantYn() != null ? notice.getImportantYn() : "N");
        int result = noticeService.addNotice(notice);
        return "redirect:/notice/" + notice.getNoticeNo();
    }

    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [관리자 기능] 공지사항 수정
     */
    @PostMapping("/admin/notice/modify")
    public String modifyNotice(HttpSession session, Notice notice){
        notice.setAdminId(MemberUtils.getMemberIdFromSession(session));
        notice.setImportantYn(notice.getImportantYn() != null ? notice.getImportantYn() : "N");
        int result = noticeService.modifyNotice(notice);
        return "redirect:/notice/" + notice.getNoticeNo();
    }

}
