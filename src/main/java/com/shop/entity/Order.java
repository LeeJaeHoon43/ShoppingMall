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
public class Order extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 한 명의 회원은 여러 주문을 할 수 있음. (주문 엔티티 기준에서 다대일 매핑)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate; // 주문일.

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문 상태.

    // 외래키(order_id)가 order_item테이블에 있으므로 연관 관계 주인은 OrderItem 엔티티.
    // Order 엔티티가 주인이 아니어서 mappedBy 속성으로 연관 관계 주인 설정.
    // 연관 관계 주인의 필드인 order를 mappedBy의 값으로 세팅.
    // 주문 상품 엔티티와 1대1 매핑. OrderItem에 있는 Order에 의해 관리.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) // 부모 엔티티의 영속성 상태 변화를 자식엔티티에 모두 전이.
    private List<OrderItem> orderItems = new ArrayList<>();

    // private LocalDateTime reqTime;
    // private LocalDateTime updateTime;

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem); // 주문 상품 정보를 담는다.
        orderItem.setOrder(this); // orderItem객체에도 order 객체를 세팅.
    }

    public static Order createOrder(Member member, List<OrderItem> orderItemList){
        Order order = new Order();
        order.setMember(member); // 상품을 주문한 고객 정보 세팅.
        for(OrderItem orderItem : orderItemList){ // 장바구니 페이지에서는 한 번에 여러개의 상품을 주문할 수 있다.
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // 총 주문 금액을 구하는 메서드.
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    // 주문 취소 시 주문수량을 상품의 재고에 더해주는 로직 + 주문상태를 취소상태로 변경.
    public void cancelOrder(){
        this.orderStatus = OrderStatus.CANCEL;

        for (OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }
}
