package com.nuguri.dealservice.service;

import com.nuguri.dealservice.domain.DealHistory;
import com.nuguri.dealservice.dto.deal.DealFinishedDto;
import com.nuguri.dealservice.dto.deal.DealHistoryResponseDto;
import com.nuguri.dealservice.dto.deal.DealHistoryUpdateDto;
import com.nuguri.dealservice.repository.DealHistoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class DealHistoryServiceTest {

    @Autowired
    DealHistoryService dealHistoryService;
    @Autowired
    DealHistoryRepository dealHistoryRepository;

    @Test
    @Commit
    public void 중고거래기록남기기() throws Exception{
        //given
        DealHistoryResponseDto dealHistory1 = dealHistoryService.createDealHistory(1L, 4L);
        System.out.println("dealHistoryId = " + dealHistory1.getDealHistoryId());
        Optional<DealHistory> dealHistory = dealHistoryRepository.findById(1L);
        System.out.println("dealHistory.get().getDealStatus() = " + dealHistory.get().getDealStatus());
        System.out.println("dealHistory.get().getPromiseLocation() = " + dealHistory.get().getPromiseLocation());
    }

    @Test
    @Commit
    public void 구매자로바꾸기() throws Exception{
        DealHistoryUpdateDto dealHistoryUpdateDto = DealHistoryUpdateDto.builder()
                .buyerId(1L)
                .dealId(2L)
                .promiseTime(LocalDateTime.now())
                .promiseLocation("역삼동 멀티캠퍼스")
                .build();

        dealHistoryService.updateToReserver(dealHistoryUpdateDto);

    }

    @Test
    @Commit
    public void 판매완료() throws Exception{
        dealHistoryService.dealFinished(DealFinishedDto.builder()
                .buyerId(1L)
                .dealId(3L)
                .build());
    }

}