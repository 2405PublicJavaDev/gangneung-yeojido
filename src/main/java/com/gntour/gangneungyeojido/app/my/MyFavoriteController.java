package com.gntour.gangneungyeojido.app.my;

import com.gntour.gangneungyeojido.app.my.dto.FavoritesResponse;
import com.gntour.gangneungyeojido.app.my.dto.FavoritesSearchCondition;
import com.gntour.gangneungyeojido.common.MemberUtils;
import com.gntour.gangneungyeojido.common.exception.BusinessException;
import com.gntour.gangneungyeojido.common.exception.EmptyResponse;
import com.gntour.gangneungyeojido.common.exception.ErrorCode;
import com.gntour.gangneungyeojido.domain.mytravel.service.FavoritesService;
import com.gntour.gangneungyeojido.domain.mytravel.vo.Favorites;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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
    public String showMyFavoritesPage(HttpSession session, Model model,
                                      @RequestParam(value="currentPage", defaultValue = "1") Integer currentPage,
                                      @RequestParam(value="isNew", defaultValue = "Y") String isNew) {
        List<FavoritesResponse> favoritesList = favoritesService.getAllFavoritesByMember(new FavoritesSearchCondition(MemberUtils.getMemberIdFromSession(session), isNew));
        model.addAttribute("favoritesList",favoritesList);
        model.addAttribute("isNew", isNew);
         return "myPage/myFavoritePage";
    }

    /**
     * 담당자 : 백인호님
     * 관련 기능 : [마이페이지 기능] 즐겨찾기 등록 API 구현
     */
    @PostMapping("/favorites")
    @ResponseBody
    public EmptyResponse addFavorite(HttpSession session) {
        Favorites favorites = new Favorites();
        String favoriteNo = favorites.getFavoritesNo().toString();
        int result =  favoritesService.addFavorite(favoriteNo,MemberUtils.getMemberIdFromSession(session));
        if(result == 0) {
            throw new BusinessException(ErrorCode.NO_UPDATE);
        }
        return new EmptyResponse();
    };

    @GetMapping("/favorite")
    public String removeFavorite(HttpSession session) {
        Favorites favorites = new Favorites();
        String favoriteNo = favorites.getFavoritesNo().toString();
        int result = favoritesService.removeFavorite(favoriteNo);
        if (result == 0) {
            throw new BusinessException(ErrorCode.NO_UPDATE);
        }
        return "myPage/myFavoritePage";
    }
}
