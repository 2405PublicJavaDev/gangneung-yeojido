package com.gntour.gangneungyeojido.domain.mytravel.mapper;

public interface FavoritesMapper {


    void selectAllFavoritesByMember();

    void selectAllFavoritesCountByMember();

    void insertFavorites();

    void deleteFavorites();

}
