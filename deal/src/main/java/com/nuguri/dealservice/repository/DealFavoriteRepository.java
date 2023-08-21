package com.nuguri.dealservice.repository;

import com.nuguri.dealservice.domain.DealFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealFavoriteRepository extends JpaRepository<DealFavorite, Long>, DealFavoriteRepositoryCustom {
    DealFavorite findByMemberIdAndDealId(Long memberId, Long dealId);
}
