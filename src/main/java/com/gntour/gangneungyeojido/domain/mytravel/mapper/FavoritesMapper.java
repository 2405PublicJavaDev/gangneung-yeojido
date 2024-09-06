package com.gntour.gangneungyeojido.domain.mytravel.mapper;

import com.gntour.gangneungyeojido.app.my.dto.FavoritesResponse;
import com.gntour.gangneungyeojido.app.my.dto.FavoritesSearchCondition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FavoritesMapper {

    List<FavoritesResponse> selectAllFavoritesByMember(FavoritesSearchCondition memberId);

    void selectAllFavoritesCountByMember();

    int insertFavorites(String memberId);

    int deleteFavorites(Long favoritesNo);
}
