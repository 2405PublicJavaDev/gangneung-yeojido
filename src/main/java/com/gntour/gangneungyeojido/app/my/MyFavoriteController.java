package com.gntour.gangneungyeojido.app.my;

import com.gntour.gangneungyeojido.app.my.dto.FavoritesSearchCondition;
import com.gntour.gangneungyeojido.common.MemberUtils;
import com.gntour.gangneungyeojido.domain.mytravel.service.FavoritesService;
import com.gntour.gangneungyeojido.domain.mytravel.vo.Favorites;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MyFavoriteController {

    private final FavoritesService favoritesService;

    /**
     * 담당자 : 백인호님
     * 관련 기능 : [마이페이지 기능] 즐겨찾기 리스트 조회
     */
    @GetMapping("/favorites")
    public String showMyFavoritesPage(HttpSession session, Model model) {
        List<Favorites> favoritesList = favoritesService.getAllFavoritesByMember(new FavoritesSearchCondition(MemberUtils.getMemberIdFromSession(session), "Y"));
        model.addAttribute("favoritesList",favoritesList);
         return "/myPage/myPage";
    }

    /**
     * 담당자 : 백인호님
     * 관련 기능 : [마이페이지 기능] 즐겨찾기, 여행 기록, 문의 내역, 답변 내역(문의 내역에 관리자가 답변한 것) 등록 건수 조회
     */
    @GetMapping("/statistics")
    public String getMyStatisticsInfo(){
        return "/myPage/myPage";
    };

    /**
     * 담당자 : 백인호님
     * 관련 기능 : [마이페이지 기능] 즐겨찾기 등록 API 구현
     */
    @PostMapping
    public void addFavorite(){

    };

    /**
     * 담당자 : 백인호님
     * 관련 기능 :[마이페이지 기능] 즐겨찾기 해제
     */
    public void removeFavorite(){};
}
