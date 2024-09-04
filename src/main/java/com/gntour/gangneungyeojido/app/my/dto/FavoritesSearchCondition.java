package com.gntour.gangneungyeojido.app.my.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoritesSearchCondition {
    private String memberId;
    private String isNew; // isNew.equals('Y') : 최신순 정렬, isNew.equals('N') : 오래된것부터 정렬
}
