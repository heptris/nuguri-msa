package com.nuguri.dealservice.repository;

import com.nuguri.dealservice.domain.DealFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DealFavoriteRepository extends JpaRepository<DealFavorite, Long>, DealFavoriteRepositoryCustom {
    Optional<DealFavorite> findByMemberIdAndDealId(Long memberId, Long dealId);
}
