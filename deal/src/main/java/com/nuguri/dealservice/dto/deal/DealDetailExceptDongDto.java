package com.nuguri.dealservice.dto.deal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealDetailExceptDongDto {
    // 비로그인시
    private Long dealId;
    private String title;
    private String description;
    private int price;
    private int hit;
    private boolean isDeal;
    private String dealImage;
    // MSA이므로 feignClient로 가져옴
//    private String dong;
    private Long sellerId;
    // MSA이므로 feignClient로 가져옴
//    private String sellerNickname;
}
