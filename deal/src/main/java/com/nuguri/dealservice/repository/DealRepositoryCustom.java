package com.nuguri.dealservice.repository;

import com.nuguri.dealservice.dto.deal.DealDetailDto;
import com.nuguri.dealservice.dto.deal.DealListDto;
import com.nuguri.dealservice.dto.deal.DealListRequestCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DealRepositoryCustom {

    List<DealListDto> findDealByMemberId(Long memberId);

    List<DealListDto> findDealByMemberIdAndIsDealTrue(Long memberId);

    List<DealListDto> findDealByMemberIdAndIsDealFalse(Long memberId);

}
