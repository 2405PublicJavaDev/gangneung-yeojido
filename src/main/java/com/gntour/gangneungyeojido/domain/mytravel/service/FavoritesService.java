package com.gntour.gangneungyeojido.domain.mytravel.service;

import com.gntour.gangneungyeojido.app.my.dto.FavoritesSearchCondition;
import com.gntour.gangneungyeojido.domain.mytravel.vo.Favorites;

import java.util.List;

public interface FavoritesService {

    List<Favorites> getAllFavoritesByMember(FavoritesSearchCondition searchCondition);

    void addFavorite();

    void removeFavorite();

}
