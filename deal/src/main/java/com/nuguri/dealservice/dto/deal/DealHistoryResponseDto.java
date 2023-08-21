package com.nuguri.dealservice.dto.deal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealHistoryResponseDto {

    private Long dealHistoryId;
    private boolean isDuplicated;

    public void changeIsDuplicated(){
        this.isDuplicated = true;
    }
}
