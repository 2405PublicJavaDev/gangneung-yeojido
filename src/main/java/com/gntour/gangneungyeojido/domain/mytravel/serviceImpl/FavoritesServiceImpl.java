package com.gntour.gangneungyeojido.domain.mytravel.serviceImpl;

import com.gntour.gangneungyeojido.app.my.dto.FavoritesResponse;
import com.gntour.gangneungyeojido.app.my.dto.FavoritesSearchCondition;
import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.domain.mytravel.mapper.FavoritesMapper;
import com.gntour.gangneungyeojido.domain.mytravel.service.FavoritesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FavoritesServiceImpl implements FavoritesService {
    private final FavoritesMapper favoritesMapper;

    @Override
    public Page<FavoritesResponse, FavoritesSearchCondition> getAllFavoritesByMember(Integer currentPage, FavoritesSearchCondition favoritesSearchCondition) {
        return Page.of(currentPage, favoritesMapper.selectAllFavoritesCountByMember(favoritesSearchCondition), favoritesSearchCondition, favoritesMapper::selectAllFavoritesByMember);
    }

    @Override
    public int addFavorite(String memberId, Long travelNo) {
        int result = favoritesMapper.insertFavorites(memberId, travelNo);
        return result;
    }

    @Override
    public int removeFavorite(String memberId, Long favoritesNo) {
        log.info("favoritesNo {} memberId {}", favoritesNo, memberId);
        int result = favoritesMapper.deleteFavorites(memberId, favoritesNo);
        return result;
    }


}
