package com.shop.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "cart")
@Data
public class Cart {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER) // 회원 엔티티와 1대1 매핑. 즉시 로딩(엔티티를 조회할 때 해당 엔티티와 매핑된 엔티티도 한번에 조회)
    @JoinColumn(name = "member_id") // 매핑할 외래키 지정.
    private Member member;
}
