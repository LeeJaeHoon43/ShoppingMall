package com.shop.repository;

import com.shop.dto.ItemSearchDto;
import com.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    // 상품 조회 조건을 가지고 있는 itemSearchDto객체와 페이징 정보를 가지고 있는 pageable 객체를 파리미터로 받는 메서드.
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
