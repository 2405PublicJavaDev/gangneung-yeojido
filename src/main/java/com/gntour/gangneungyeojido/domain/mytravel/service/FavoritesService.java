package com.gntour.gangneungyeojido.domain.mytravel.service;

import com.gntour.gangneungyeojido.app.my.dto.AddFavoriteResponse;
import com.gntour.gangneungyeojido.app.my.dto.FavoritesResponse;
import com.gntour.gangneungyeojido.app.my.dto.FavoritesSearchCondition;
import com.gntour.gangneungyeojido.common.Page;

import java.util.List;

public interface FavoritesService {

    Page<FavoritesResponse, FavoritesSearchCondition> getAllFavoritesByMember(Integer currentPage, FavoritesSearchCondition searchCondition);

    AddFavoriteResponse addFavorite(String memberId, Long travelNo);

    int removeFavorite(String memberId, Long favoritesNo);
}
