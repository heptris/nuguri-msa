package com.nuguri.dealservice.repository;

import com.nuguri.dealservice.domain.DealFavorite;
import com.nuguri.dealservice.dto.deal.DealListDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class DealFavoriteRepositoryImplTest {

    @Autowired
    DealFavoriteRepository dealFavoriteRepository;

    @Test
    public void 멤버즐겨찾기목록() throws Exception{
        List<DealListDto> dealByMemberIdAndIsFavorite = dealFavoriteRepository.findDealByMemberIdAndIsFavorite(1L);
        for (DealListDto dealListDto : dealByMemberIdAndIsFavorite) {
            System.out.println("dealListDto = " + dealListDto);
        }
    }
}