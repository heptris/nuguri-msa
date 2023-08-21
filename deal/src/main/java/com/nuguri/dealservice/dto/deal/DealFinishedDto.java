package com.nuguri.dealservice.dto.deal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealFinishedDto {

    private Long buyerId;
    private Long dealId;

}
