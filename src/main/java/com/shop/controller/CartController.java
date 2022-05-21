package com.shop.controller;

import com.shop.dto.CartDetailDto;
import com.shop.dto.CartItemDto;
import com.shop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid CartItemDto cartItemDto, BindingResult bindingResult, Principal principal){
        // 장바구니에 담을 상품 정보를 cartItemDto 객체에 데이터 바인딩 시 에러가 있는지 검사.
        if (bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName(); // 현재 로그인한 회원의 이메일 정보를 저장.
        Long cartItemId;

        try{
            // 장바구니에 담을 상품 정보와 현재 로그인한 회원의 이메일 정보를 이용하여 장바구니에 상품을 담는다.
            cartItemId = cartService.addCart(cartItemDto, email);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        // 생성된 장바구니 상품 아이디와 HTTP 응답 상태 코드 리턴.
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @GetMapping(value = "/cart")
    public String orderHist(Principal principal, Model model){
        // 현재 로그인한 사용자의 이메일로 장바구니에 담겨 있는 상품 정보 조회.
        List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());
        model.addAttribute("cartItems", cartDetailList);
        return "cart/cartList";
    }

    @PatchMapping(value = "/cartItem/{cartItemId}") // HTTP 메서드에서 PATCH는 요청된 자원의 일부를 업데이트할 때 사용.
    public @ResponseBody ResponseEntity updatCartItem(@PathVariable("cartItemId") Long cartItemId, int count, Principal principal){
        // 장바구니에 담겨있는 상품의 개수를 0개 이하로 업데이트 요청을 할 때 에러 메시지와 같이 리턴.
        if (count <= 0){
            return new ResponseEntity<String>("최소 1개 이상 담아주세요.", HttpStatus.BAD_REQUEST);
        } else if (!cartService.validateCartItem(cartItemId, principal.getName())){ // 수정 권한 체크.
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        cartService.updateCartItemCount(cartItemId, count); // 장바구니 상품의 개수를 업데이트.
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @DeleteMapping(value = "/cartItem/{cartItemId}") // HTTP 메서드에서 DELETE는 요청된 자원을 삭제할 때 사용.
    public @ResponseBody ResponseEntity deleteCartItem(@PathVariable("cartItemId") Long cartItemId, Principal principal){
        if (!cartService.validateCartItem(cartItemId, principal.getName())){ // 수정 권한 체크.
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        cartService.deleteCartItem(cartItemId); // 해당 장바구니 상품 삭제.
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }
}
