package com.gntour.gangneungyeojido.domain.mytravel.mapper;

import com.gntour.gangneungyeojido.domain.mytravel.vo.Favorites;

public interface FavoritesMapper {

    void selectAllFavoritesByMember();

    void selectAllFavoritesCountByMember();

    void insertFavorites();

    void deleteFavorites();

}
