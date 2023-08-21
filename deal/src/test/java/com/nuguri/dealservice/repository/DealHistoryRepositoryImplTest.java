package com.nuguri.dealservice.repository;

import com.nuguri.dealservice.dto.deal.DealListDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class DealHistoryRepositoryImplTest {

    @Autowired
    DealHistoryRepository dealHistoryRepository;

    @Test
    public void 내가구매한중고거래목록() throws Exception{
        List<DealListDto> dealByMemberIdAndBuyer = dealHistoryRepository.findDealByMemberIdAndBuyer(1L);
        if(dealByMemberIdAndBuyer == null || dealByMemberIdAndBuyer.isEmpty()){
            System.out.println("나 비어있다~~~~~~~~~~~~~~~~~~~~~~~");
        }else {
            for (DealListDto dealListDto : dealByMemberIdAndBuyer) {
                System.out.println("dealListDto = " + dealListDto);
            }
        }
    }
}