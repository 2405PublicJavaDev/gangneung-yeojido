package com.gntour.gangneungyeojido.domain.mytravel.mapper;

import com.gntour.gangneungyeojido.app.my.dto.FavoritesSearchCondition;
import com.gntour.gangneungyeojido.domain.mytravel.vo.Favorites;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FavoritesMapper {

    List<Favorites> selectAllFavoritesByMember(FavoritesSearchCondition memberId);

    void selectAllFavoritesCountByMember();

    void insertFavorites(Favorites favorites);

    void deleteFavorites();

}
