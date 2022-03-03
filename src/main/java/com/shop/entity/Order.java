package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne // 한 명의 회원은 여러 주문을 할 수 있음. (주문 엔티티 기준에서 다대일 매핑)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate; // 주문일.

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문 상태.

    // 외래키(order_id)가 order_item테이블에 있으므로 연관 관계 주인은 OrderItem 엔티티.
    // Order 엔티티가 주인이 아니어서 mappedBy 속성으로 연관 관계 주인 설정.
    // 연관 관계 주인의 필드인 order를 mappedBy의 값으로 세팅.
    @OneToMany(mappedBy = "order") // 주문 상품 엔티티와 1대1 매핑. OrderItem에 있는 Order에 의해 관리.
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime reqTime;
    private LocalDateTime updateTime;
}
