package com.nuguri.dealservice.service;

import com.nuguri.dealservice.domain.DealFavorite;
import com.nuguri.dealservice.dto.baseaddress.BaseAddressDto;
import com.nuguri.dealservice.dto.category.CategoryListDto;
import com.nuguri.dealservice.dto.deal.DealListDto;
import com.nuguri.dealservice.repository.DealFavoriteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
class DealServiceTest {

    @Autowired
    DealService dealService;
    @Autowired
    DealFavoriteRepository dealFavoriteRepository;

    @Test
    public void feignclientest() throws Exception{
        List<BaseAddressDto> allBaseaddress = dealService.getAllBaseaddress();
        for (BaseAddressDto baseaddress : allBaseaddress) {
            System.out.println("baseaddress = " + baseaddress);
        }
    }

    @Test
    public void feignclientest2() throws Exception{
        List<CategoryListDto> allCategory = dealService.getAllCategory();
        for (CategoryListDto categoryListDto : allCategory) {
            System.out.println("categoryListDto = " + categoryListDto);
        }
    }

    @Test
    @Commit
    public void 즐겨찾기등록해제() throws Exception{
        dealService.createOrModifyDealFavorite(4L, 4L);
        dealService.createOrModifyDealFavorite(6L, 3L);

        Optional<DealFavorite> dealFavorite = dealFavoriteRepository.findById(9L);
        System.out.println("dealFavorite.get().isFavorite() = " + dealFavorite.get().isFavorite());
    }

    @Test
    public void 멤버아이디로Deal조회() throws Exception{
        List<DealListDto> dealByMemberId = dealService.findDealByMemberId(1L);
        for (DealListDto dealListDto : dealByMemberId) {
            System.out.println("dealListDto = " + dealListDto);
        }
    }
}