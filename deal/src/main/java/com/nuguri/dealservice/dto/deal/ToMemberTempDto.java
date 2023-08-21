package com.nuguri.dealservice.dto.deal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToMemberTempDto {

    private Long sellerId;
    private Long buyerId;
    private Long dealId;
}
