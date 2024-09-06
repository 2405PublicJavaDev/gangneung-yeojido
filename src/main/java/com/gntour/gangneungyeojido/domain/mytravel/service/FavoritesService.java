package com.gntour.gangneungyeojido.domain.mytravel.service;

import com.gntour.gangneungyeojido.app.my.dto.FavoritesResponse;
import com.gntour.gangneungyeojido.app.my.dto.FavoritesSearchCondition;

import java.util.List;

public interface FavoritesService {

    List<FavoritesResponse> getAllFavoritesByMember(FavoritesSearchCondition searchCondition);

    int addFavorite(String memberId, String favoritesNo);

    int removeFavorite(String favoritesNo);
}
