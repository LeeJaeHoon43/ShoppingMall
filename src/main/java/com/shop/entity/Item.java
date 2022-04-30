package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity // Item클래스를 entity로 선언.
@Table(name = "item") // item이라는 테이블과 매핑.
public class Item extends BaseEntity{

    @Id // 기본키가 되는 멤버변수
    @Column(name = "item_id") // 매핑될 컬럼 이름.
    @GeneratedValue(strategy = GenerationType.AUTO) // 기본키 생성 전략을 자동으로 설정.
    private Long id; // 상품 코드.

    @Column(nullable = false, length = 50) // NOT NULL 설정.
    private String itemNm; // 상품명.

    @Column(name = "price", nullable = false)
    private int price; // 가격.

    @Column(nullable = false)
    private int stockNumber; // 재고수량.

    @Lob
    @Column(nullable = false)
    private String itemDetail; // 상품 상세 설명.

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // 상품 판매 상태.

    // private LocalDateTime reqTime; // 등록 시간.
    // private LocalDateTime updateTime; // 수정 시간.

    public void updateItem(ItemFormDto itemFormDto){
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }
}
