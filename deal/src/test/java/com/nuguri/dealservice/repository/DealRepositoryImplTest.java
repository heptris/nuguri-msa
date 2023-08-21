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
class DealRepositoryImplTest {

    @Autowired
    DealRepository dealRepository;
    
    @Test
    public void 판매완료된목록() throws Exception{

        List<DealListDto> dealByMemberIdAndIsDealTrue = dealRepository.findDealByMemberIdAndIsDealTrue(1L);
        if(dealByMemberIdAndIsDealTrue == null || dealByMemberIdAndIsDealTrue.size() == 0){
            System.out.println("나 비어있다~~~~~~~~~~~~~~~~~~~~~~~");
        }else {
            for (DealListDto dealListDto : dealByMemberIdAndIsDealTrue) {
                System.out.println("dealListDto = " + dealListDto);
            }
        }
    }

    @Test
    public void 판매중인목록() throws Exception{

        List<DealListDto> dealByMemberIdAndIsDealTrue = dealRepository.findDealByMemberIdAndIsDealFalse(1L);
        if(dealByMemberIdAndIsDealTrue == null || dealByMemberIdAndIsDealTrue.size() == 0){
            System.out.println("나 비어있다~~~~~~~~~~~~~~~~~~~~~~~");
        }else {
            for (DealListDto dealListDto : dealByMemberIdAndIsDealTrue) {
                System.out.println("dealListDto = " + dealListDto);
            }
        }
    }

}