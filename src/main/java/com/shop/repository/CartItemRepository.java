package com.shop.repository;

import com.shop.dto.CartDetailDto;
import com.shop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndItemId(Long cartId, Long itemId); // 카트 아이디와 상품 아이디를 이용해서 상품이 장바구니에 들어있는지 조회.

    // CartDetailDto의 생성자를 이용하여 DTO를 리턴할때는 new 키워드와 해당 DTO의 패키지, 클래스명을 적어준다.
    @Query(
            "select new com.shop.dto.CartDetailDto(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) " +
                    "from CartItem ci, ItemImg im " +
                    "join ci.item i " +
                    "where ci.cart.id = :cartId " +
                    "and im.item.id = ci.item.id " +
                    "and im.repimgYn = 'Y' " +
                    "order by ci.reqTime desc"
    )
    List<CartDetailDto> findCartDetailDtoList(Long cartId);
}
