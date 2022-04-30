package com.shop.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "item_img")
@Data
public class ItemImg extends BaseEntity{

    @Id
    @Column(name = "item_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName; // 이미지 파일명.

    private String oriImgName; // 원본 이미지 파일명.

    private String imgUrl; // 이미지 조회 경로.

    private String repimgYn; // 대표 이미지 여부.

    @ManyToOne(fetch = FetchType.LAZY) // 상품 엔티티와 다대일 단방향 매핑. 지연 로딩 설정하여 매핑된 상품 엔티티 정보가 필요할 경우 데이터를 조회.
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateItemImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
