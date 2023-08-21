package com.nuguri.dealservice.dto.deal;

import com.nuguri.dealservice.domain.Deal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealRegistRequestDto {

    private Long memberId;
    private Long categoryId;
    private String title;
    private String description;
    private int price;

    public Deal toDeal(){
        return Deal.builder()
                .memberId(memberId)
                .categoryId(categoryId)
                .title(title)
                .description(description)
                .price(price)
                .hit(0)
                .isDeal(false)
                .build();
    }

}
