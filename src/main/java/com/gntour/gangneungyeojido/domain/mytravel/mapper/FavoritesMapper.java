package com.gntour.gangneungyeojido.domain.mytravel.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FavoritesMapper {


    void selectAllFavoritesByMember();

    void selectAllFavoritesCountByMember();

    void insertFavorites();

    void deleteFavorites();

}
