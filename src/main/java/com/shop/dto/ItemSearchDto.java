package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {
    private String searchDateType; // 상품 데이터
    private ItemSellStatus searchSellStatus; // 상품의 판매상태.
    private String searchBy; // 어떤 유형으로 상품을 조회할지.
    private String searchQuery = ""; // 조회할 검색어 저장 변수.
}
