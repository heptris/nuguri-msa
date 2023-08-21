package com.nuguri.basic.repository.baseaddress;

import com.nuguri.basic.domain.baseaddress.QBaseAddress;
import com.nuguri.basic.dto.baseaddress.*;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class BaseaddressRepositoryImpl implements BaseaddressRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public BaseaddressRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<BaseAddressResponseDto> findBaseaddressByDong(String keyword) {
        List<BaseAddressSearchDto> baseAddressSearchDtoList = queryFactory.
                select(Projections.constructor(BaseAddressSearchDto.class,
                        QBaseAddress.baseAddress.id,
                        QBaseAddress.baseAddress.sido,
                        QBaseAddress.baseAddress.gugun,
                        QBaseAddress.baseAddress.dong
                ))
                .from(QBaseAddress.baseAddress)
//                성능 비교 : 280ms
//                .where(baseAddress.dong.like("%"+keyword+"%"))
//                성능 비교 : 257ms
                .where(QBaseAddress.baseAddress.dong.contains(keyword))
                .fetch();
        List<BaseAddressResponseDto> list = new ArrayList<>();
        for (BaseAddressSearchDto baseAddressSearchDto : baseAddressSearchDtoList) {
            list.add(baseAddressSearchDto.toBaseAddressResponseDto());
        }

        return list;
    }

    @Override
    public List<BaseAddressSidoDto> findSidoList() {
        List<BaseAddressSidoDto> baseAddressSidoDtoList = queryFactory
                .select(Projections.constructor(BaseAddressSidoDto.class,
                        QBaseAddress.baseAddress.sido
                ))
                .from(QBaseAddress.baseAddress)
                .groupBy(QBaseAddress.baseAddress.sido)
                .fetch();

        return baseAddressSidoDtoList;
    }

    @Override
    public List<BaseAddressGugunDto> findGugunList(String sido) {
        List<BaseAddressGugunDto> baseAddressGugunDtoList = queryFactory
                .select(Projections.constructor(BaseAddressGugunDto.class,
                        QBaseAddress.baseAddress.gugun
                ))
                .from(QBaseAddress.baseAddress)
                .where(QBaseAddress.baseAddress.sido.eq(sido))
                .groupBy(QBaseAddress.baseAddress.gugun)
                .fetch();

        return baseAddressGugunDtoList;
    }

    @Override
    public List<BaseAddressDongDto> findDongList(String gugun) {
        List<BaseAddressDongDto> baseAddressDongDtoList = queryFactory
                .select(Projections.constructor(BaseAddressDongDto.class,
                        QBaseAddress.baseAddress.id,
                        QBaseAddress.baseAddress.dong
                ))
                .from(QBaseAddress.baseAddress)
                .where(QBaseAddress.baseAddress.gugun.eq(gugun))
                .fetch();

        return baseAddressDongDtoList;
    }

    @Override
    public List<BaseAddressDto> findAllBaseAddress() {
        List<BaseAddressDto> baseAddressDtoList = queryFactory
                .select(Projections.constructor(BaseAddressDto.class,
                        QBaseAddress.baseAddress.id,
                        QBaseAddress.baseAddress.sido,
                        QBaseAddress.baseAddress.gugun,
                        QBaseAddress.baseAddress.dong,
                        QBaseAddress.baseAddress.lat,
                        QBaseAddress.baseAddress.lng
                ))
                .from(QBaseAddress.baseAddress)
                .fetch();
        return baseAddressDtoList;
    }
}
