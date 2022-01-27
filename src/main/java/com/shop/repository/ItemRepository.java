package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    // JpaRepository는 2개의 제네릭 타입을 사용.
    // 첫 번째에는 엔티티 타입 클래스를 넣고, 두 번째는 기본키 타입을 넣음.
}
