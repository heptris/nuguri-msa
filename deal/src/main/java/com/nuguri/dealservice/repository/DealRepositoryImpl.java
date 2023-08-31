package com.nuguri.dealservice.repository;

import com.nuguri.dealservice.domain.QDeal;
import com.nuguri.dealservice.dto.deal.DealDetailDto;
import com.nuguri.dealservice.dto.deal.DealDetailExceptDongDto;
import com.nuguri.dealservice.dto.deal.DealListDto;
import com.nuguri.dealservice.dto.deal.DealListRequestCondition;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.nuguri.dealservice.domain.QDeal.deal;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class DealRepositoryImpl implements DealRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public DealRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<DealListDto> findDealByMemberId(Long memberId) {
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
                .where(deal.memberId.eq(memberId))
                .fetch();
        return dealListDtoList;
    }

    @Override
    public List<DealListDto> findDealByMemberIdAndIsDealTrue(Long memberId) {
        List<DealListDto> dealListDtoList = queryFactory
                .select(Projections.constructor(DealListDto.class,
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
                .where(deal.memberId.eq(memberId).and(deal.isDeal.eq(TRUE)))
                .fetch();
        return dealListDtoList;
    }

    @Override
    public List<DealListDto> findDealByMemberIdAndIsDealFalse(Long memberId) {
        List<DealListDto> dealListDtoList = queryFactory
                .select(Projections.constructor(DealListDto.class,
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
                .where(deal.memberId.eq(memberId).and(deal.isDeal.eq(FALSE)))
                .fetch();
        return dealListDtoList;
    }

    @Override
    public Optional<DealDetailExceptDongDto> dealDetail(Long dealId) {
        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(DealDetailExceptDongDto.class,
                        deal.id,
                        deal.title,
                        deal.description,
                        deal.price,
                        deal.hit,
                        deal.isDeal,
                        deal.dealImage,
                        deal.memberId
                        ))
                .from(deal)
                .where(deal.id.eq(dealId))
                .fetchOne()
        );
    }
}
