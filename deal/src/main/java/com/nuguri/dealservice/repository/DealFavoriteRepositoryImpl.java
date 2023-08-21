package com.nuguri.dealservice.repository;

import com.nuguri.dealservice.domain.QDeal;
import com.nuguri.dealservice.domain.QDealFavorite;
import com.nuguri.dealservice.dto.deal.DealListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.nuguri.dealservice.domain.QDeal.deal;
import static com.nuguri.dealservice.domain.QDealFavorite.dealFavorite;
import static java.lang.Boolean.TRUE;

public class DealFavoriteRepositoryImpl implements DealFavoriteRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public DealFavoriteRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<DealListDto> findDealByMemberIdAndIsFavorite(Long memberId) {
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
                .innerJoin(deal.dealFavoriteList, dealFavorite)
                .where(dealFavorite.memberId.eq(memberId).and(dealFavorite.isFavorite.isTrue()))
                .fetch();
        return dealListDtoList;
    }
}
