package com.nuguri.dealservice.repository;

import com.nuguri.dealservice.dto.deal.DealListDto;

import java.util.List;

public interface DealFavoriteRepositoryCustom {
    List<DealListDto> findDealByMemberIdAndIsFavorite(Long memberId);
}
