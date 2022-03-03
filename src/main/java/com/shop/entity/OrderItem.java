package com.shop.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne // 하나의 상품은 여러 주문 상품으로 들어갈 수 있음. 다대일 단방향 매핑.
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne // 한 번의 주문에 여러 개의 상품을 주문할 수 있음. 주문 상품 엔티티와 주문 엔티티를 다대일 단방향 매핑.
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격.
    private int count; // 수량.
    private LocalDateTime reqTime;
    private LocalDateTime updateTime;
}
