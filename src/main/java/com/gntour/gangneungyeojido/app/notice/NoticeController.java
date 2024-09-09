package com.gntour.gangneungyeojido.app.notice;

import com.gntour.gangneungyeojido.app.notice.dto.NoticeSearchCondition;
import com.gntour.gangneungyeojido.domain.notice.service.NoticeService;
import com.gntour.gangneungyeojido.domain.notice.vo.Notice;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NoticeController {
    private final NoticeService noticeService;

    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [공지사항 기능] 공지사항 리스트 조회
     */
    @GetMapping("/notice")
    public String showNoticeListPage(
            Model model,
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
            @RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {
        // 검색 조건 설정
        NoticeSearchCondition condition = new NoticeSearchCondition();
        if ("title".equals(searchType)) {
            condition.setTitle(searchKeyword);
        } else if ("content".equals(searchType)) {
            condition.setContent(searchKeyword);
        }
        // 중요 공지사항 3개 조회
        List<Notice> importantNotices = noticeService.getImportantNotices();
        model.addAttribute("importantNotices", importantNotices);
        // 일반 공지사항 리스트 조회
        model.addAttribute("page", noticeService.getAllNotices(currentPage, condition));
        return "notice/notice-list";
    }

    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [공지사항 기능] 공지사항 세부 사항 조회
     */
    @GetMapping("/notice/{noticeNo}")
    public String showNoticeDetailPage(Model model, HttpSession session
            , @PathVariable("noticeNo") Long noticeNo){
        Notice notice = noticeService.getDetailNotice(noticeNo);
        model.addAttribute("notice", notice);
        // 세션에서 사용자 역할을 가져옴
        String memberRole = (String) session.getAttribute("MEMBER_ROLE");
        if (memberRole == null) {
            memberRole = "USER";  // 기본적으로 비회원은 일반 사용자로 처리
        }
        model.addAttribute("memberRole", memberRole);
        return "notice/notice-detail";
    }
    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [푸터 기능] 주요 공지사항 리스트 조회
     */
    @ModelAttribute
    public String getImportantNoticeList(Model model){
        List<Notice> importantNotices = noticeService.getImportantNotices();
        model.addAttribute("footerImportantNotices", importantNotices);
        return "fragments/footer";
    }
}
