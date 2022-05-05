package com.shop.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class OrderItem extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 하나의 상품은 여러 주문 상품으로 들어갈 수 있음. 다대일 단방향 매핑.
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY) // 한 번의 주문에 여러 개의 상품을 주문할 수 있음. 주문 상품 엔티티와 주문 엔티티를 다대일 단방향 매핑.
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격.
    private int count; // 수량.

    // private LocalDateTime reqTime;
    // private LocalDateTime updateTime;

    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item); // 주문할 상품 세팅.
        orderItem.setCount(count); // 주문 수량 세팅.
        orderItem.setOrderPrice(item.getPrice()); // 상품가격을 주문가격으로 세팅.

        item.removeStock(count); // 주문 수량만큼 재고 수량 차감.
        return orderItem;
    }

    // 주문 가격과 주문 수량을 곱해서 해당 상품을 주문한 총 가격으로 계산.
    public int getTotalPrice(){
        return orderPrice * count;
    }
}
