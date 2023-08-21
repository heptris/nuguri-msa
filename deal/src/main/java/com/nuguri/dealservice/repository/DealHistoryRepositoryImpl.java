package com.nuguri.dealservice.repository;

import com.nuguri.dealservice.domain.DealStatus;
import com.nuguri.dealservice.domain.QDeal;
import com.nuguri.dealservice.domain.QDealHistory;
import com.nuguri.dealservice.dto.deal.DealListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.nuguri.dealservice.domain.DealStatus.BUYER;
import static com.nuguri.dealservice.domain.QDeal.deal;
import static com.nuguri.dealservice.domain.QDealHistory.dealHistory;

public class DealHistoryRepositoryImpl implements DealHistoryRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public DealHistoryRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<DealListDto> findDealByMemberIdAndBuyer(Long memberId) {
        List<DealListDto> dealListDtoList = queryFactory.select(Projections.constructor(DealListDto.class,
                        deal.id,
                        deal.categoryId,
                        deal.localId,
                        deal.title,
                        deal.description,
                        deal.price,
                        deal.hit,
                        deal.isDeal,
                        deal.dealImage
                ))
                .from(deal)
                .innerJoin(deal.dealHistoryList, dealHistory)
                .where(dealHistory.memberId.eq(memberId).and(dealHistory.dealStatus.eq(BUYER)))
                .fetch();
        return dealListDtoList;
    }
}
