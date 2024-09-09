package com.gntour.gangneungyeojido.app.my;

import com.gntour.gangneungyeojido.app.my.dto.AddFavoriteRequest;
import com.gntour.gangneungyeojido.app.my.dto.FavoritesResponse;
import com.gntour.gangneungyeojido.app.my.dto.FavoritesSearchCondition;
import com.gntour.gangneungyeojido.common.MemberUtils;
import com.gntour.gangneungyeojido.common.Page;
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
        String memberId = MemberUtils.getMemberIdFromSession(session);
        if(memberId == null) {
            throw new BusinessException(ErrorCode.LOGIN_FAIL);
        }
        Page<FavoritesResponse, FavoritesSearchCondition> page = favoritesService.getAllFavoritesByMember(currentPage, new FavoritesSearchCondition(memberId, isNew));
        log.info(page.getData().toString());
        model.addAttribute("page",page);
        model.addAttribute("isNew", isNew);
         return "myPage/myFavoritePage";
    }

    /**
     * 담당자 : 백인호님
     * 관련 기능 : [마이페이지 기능] 즐겨찾기 등록 API 구현
     */
    @PostMapping("/favorites")
    @ResponseBody
    public EmptyResponse addFavorite(@RequestBody AddFavoriteRequest req, HttpSession session) {
        String memberId = MemberUtils.getMemberIdFromSession(session);
        if(memberId == null) {
            throw new BusinessException(ErrorCode.LOGIN_FAIL);
        }
        int result =  favoritesService.addFavorite(memberId, req.getTravelNo());
        if(result == 0) {
            throw new BusinessException(ErrorCode.NO_UPDATE);
        }
        return new EmptyResponse();
    };

    @DeleteMapping("/favorite/{favoriteNo}")
    @ResponseBody
    public EmptyResponse removeFavorite(HttpSession session, @PathVariable Long favoriteNo) {
        String memberId = MemberUtils.getMemberIdFromSession(session);
        if(memberId == null) {
            throw new BusinessException(ErrorCode.LOGIN_FAIL);
        }
        int result = favoritesService.removeFavorite(memberId, favoriteNo);
        if (result == 0) {
            throw new BusinessException(ErrorCode.NO_UPDATE);
        }
        return new EmptyResponse();
    }


}
