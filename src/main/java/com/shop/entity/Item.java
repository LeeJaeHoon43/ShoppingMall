package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import com.shop.exception.OutOfStockException;
import lombok.Data;
import javax.persistence.*;

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

    public void removeStock(int stockNumber){
        int restStock = this.stockNumber = stockNumber; // 상품의 재고 수량에서 주문 후 남은 재고 수량을 구한다.

        // 상품의 재고가 주문 수량보다 작을 경우 재고 부족 예외 발생.
        if (restStock < 0){
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량 : " + this.stockNumber + ")");
        }
        this.stockNumber = restStock; // 주문 후 남은 재고 수량을 상품의 현재 재고 값으로 최신화.
    }
}
