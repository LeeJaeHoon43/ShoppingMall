package com.shop.controller;

import com.shop.dto.OrderDto;
import com.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 스프링에서 비동기 처리를 할 때 @RequestBody와 @ResponseBody 어노테이션 사용.
    // RequestBody : HTTP 요청의 본문 body에 담긴 내용을 자바 객체로 전달.
    // ResponseBody : 자바 객체를 HTTP 요청의 body로 전달.
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto, BindingResult bindingResult, Principal principal) {
        // 주문 정보를 받는 orderDto객체에 데이터 바인딩 시 에러가 있는지 검사.
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST); // 에러 처리.
        }

        String email = principal.getName(); // principal 객체에서 현재 로그인한 고객의 이메일 정보를 조회.
        Long orderId;

        try {
            orderId = orderService.order(orderDto, email); // 넘어온 주문 정보와 이메일 정보를 이용하여 주문 처리.
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(orderId, HttpStatus.OK); // 생성된 주문 번호 리턴.
    }
}