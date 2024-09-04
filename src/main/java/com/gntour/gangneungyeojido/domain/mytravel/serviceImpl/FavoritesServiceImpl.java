package com.gntour.gangneungyeojido.domain.mytravel.serviceImpl;

import com.gntour.gangneungyeojido.app.my.dto.FavoritesSearchCondition;
import com.gntour.gangneungyeojido.domain.mytravel.mapper.FavoritesMapper;
import com.gntour.gangneungyeojido.domain.mytravel.service.FavoritesService;
import com.gntour.gangneungyeojido.domain.mytravel.vo.Favorites;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoritesServiceImpl implements FavoritesService {
    private final FavoritesMapper favoritesMapper;

    @Override
    public List<Favorites> getAllFavoritesByMember(FavoritesSearchCondition favoritesSearchCondition) {
        return favoritesMapper.selectAllFavoritesByMember(favoritesSearchCondition);
    }

    @Override
    public void addFavorite() {

    }

    @Override
    public void removeFavorite() {

    }

}
