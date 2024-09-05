package com.gntour.gangneungyeojido.domain.mytravel.serviceImpl;

import com.gntour.gangneungyeojido.app.my.dto.FavoritesResponse;
import com.gntour.gangneungyeojido.app.my.dto.FavoritesSearchCondition;
import com.gntour.gangneungyeojido.domain.mytravel.mapper.FavoritesMapper;
import com.gntour.gangneungyeojido.domain.mytravel.service.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoritesServiceImpl implements FavoritesService {
    private final FavoritesMapper favoritesMapper;

    @Override
    public List<FavoritesResponse> getAllFavoritesByMember(FavoritesSearchCondition favoritesSearchCondition) {
        return favoritesMapper.selectAllFavoritesByMember(favoritesSearchCondition);
    }

    @Override
    public int addFavorite(String memberId, String favoritesNo) {
        int result = favoritesMapper.insertFavorites(memberId,favoritesNo);
        return result;
    }

    @Override
    public void removeFavorite() {

    }

}
