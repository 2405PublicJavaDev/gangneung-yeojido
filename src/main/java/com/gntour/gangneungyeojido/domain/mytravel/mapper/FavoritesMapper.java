package com.gntour.gangneungyeojido.domain.mytravel.mapper;

import com.gntour.gangneungyeojido.app.my.dto.FavoritesResponse;
import com.gntour.gangneungyeojido.app.my.dto.FavoritesSearchCondition;
import com.gntour.gangneungyeojido.domain.mytravel.vo.Favorites;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface FavoritesMapper {

    List<FavoritesResponse> selectAllFavoritesByMember(Integer currentPage, FavoritesSearchCondition condition, RowBounds rowBounds);

    int selectAllFavoritesCountByMember(FavoritesSearchCondition condition);

    int insertFavorites(Favorites favorites);

    int deleteFavorites(String memberId, Long favoritesNo);

    Long getFavoritesNo(String memberId, Long travelNo);
}
