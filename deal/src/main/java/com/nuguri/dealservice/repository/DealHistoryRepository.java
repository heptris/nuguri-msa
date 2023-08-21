package com.nuguri.dealservice.repository;

import com.nuguri.dealservice.domain.DealStatus;
import com.nuguri.dealservice.domain.DealHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealHistoryRepository extends JpaRepository<DealHistory, Long>, DealHistoryRepositoryCustom {

    DealHistory findByMemberIdAndDealId(Long memberId, Long dealId);
}
