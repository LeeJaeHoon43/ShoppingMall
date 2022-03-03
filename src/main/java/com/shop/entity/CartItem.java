package com.shop.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "cart_item")
@Data
public class CartItem {

    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne // 하나의 장바구니에는 여러 개의 상품을 담을 수 있음 (다대일 관계 매핑).
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne // 장바구니에 담을 상품의 정보를 위해 상품 엔티티 매핑. 하나의 상품은 여러 장바구니에 담길 수 있음.(다대일 관계 매핑)
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;
}
