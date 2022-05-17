package com.shop.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "cart_item")
@Data
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 하나의 장바구니에는 여러 개의 상품을 담을 수 있음 (다대일 관계 매핑).
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY) // 장바구니에 담을 상품의 정보를 위해 상품 엔티티 매핑. 하나의 상품은 여러 장바구니에 담길 수 있음.(다대일 관계 매핑)
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;

    public static CartItem createCartItem(Cart cart, Item item, int count){
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setCount(count);
        return cartItem;
    }

    // 기존에 장바구니에 담겨져 있는 상품을 추가로 장바구니에 담을 때 기존 수량에 현재 담을 수량을 더하는 메서드.
    public void addCount(int count){
        this.count += count;
    }
}
