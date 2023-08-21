package com.nuguri.dealservice.service;

import com.nuguri.dealservice.domain.Deal;
import com.nuguri.dealservice.domain.DealHistory;
import com.nuguri.dealservice.domain.DealStatus;
import com.nuguri.dealservice.dto.deal.*;
import com.nuguri.dealservice.exception.ex.CustomException;
import com.nuguri.dealservice.messagequeue.KafkaProducer;
import com.nuguri.dealservice.repository.DealRepository;
import com.nuguri.dealservice.repository.DealHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.nuguri.dealservice.exception.ex.ErrorCode.DEAL_HISTORY_NOT_FOUND;
import static com.nuguri.dealservice.exception.ex.ErrorCode.DEAL_NOT_FOUND;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DealHistoryService {

    private final DealHistoryRepository dealHistoryRepository;
    private final DealRepository dealRepository;
    private final KafkaProducer kafkaProducer;

    @Transactional
    public DealHistoryResponseDto createDealHistory(Long memberId, Long dealId){
        // 이미 채팅을 했던 것에 대한 중복 로그 쌓임 방지를 위한 중복 체크
        DealHistory duplicateCheckDealRepository = dealHistoryRepository.findByMemberIdAndDealId(memberId, dealId);
        // 중복일 경우
        if(duplicateCheckDealRepository != null){
            return DealHistoryResponseDto.builder()
                    .dealHistoryId(duplicateCheckDealRepository.getId())
                    .isDuplicated(true)
                    .build();
//            throw new CustomException(ALREADY_USED_DEAL_HISTORY);
        }

        Deal deal = dealRepository.findById(dealId)
                .orElseThrow(()->new CustomException(DEAL_NOT_FOUND));

        DealHistory dealHistory = DealHistory.builder()
                .memberId(memberId)
                .deal(deal)
                .dealStatus(DealStatus.AWAITER)
                .build();
        dealHistoryRepository.save(dealHistory);

        DealHistoryResponseDto dealHistoryResponseDto = DealHistoryResponseDto.builder()
                .dealHistoryId(dealHistory.getId())
                .isDuplicated(false)
                .build();
        return dealHistoryResponseDto;
    }

    @Transactional
    public void updateToReserver(DealHistoryUpdateDto dealHistoryUpdateDto){
        DealHistory dealHistory = dealHistoryRepository.
                findByMemberIdAndDealId(dealHistoryUpdateDto.getBuyerId(), dealHistoryUpdateDto.getDealId());
        if(dealHistory == null) throw new CustomException(DEAL_HISTORY_NOT_FOUND);

        dealHistory.updateDealHistory(DealStatus.RESERVER,
                dealHistoryUpdateDto.getPromiseTime(), dealHistoryUpdateDto.getPromiseLocation());
    }

    @Transactional
    public void dealFinished(DealFinishedDto dealFinishedDto){
        Deal deal = dealRepository.findById(dealFinishedDto.getDealId())
                .orElseThrow(()->new CustomException(DEAL_NOT_FOUND));
        deal.finishDeal();

        DealHistory dealHistory = dealHistoryRepository.
                findByMemberIdAndDealId(dealFinishedDto.getBuyerId(), dealFinishedDto.getDealId());
        if(dealHistory == null) throw new CustomException(DEAL_HISTORY_NOT_FOUND);

        kafkaProducer.send("example-deal-topic",
                ToMemberTempDto.builder()
                .sellerId(deal.getMemberId())
                .buyerId(dealFinishedDto.getBuyerId())
                .dealId(dealFinishedDto.getDealId())
                .build());

        dealHistory.dealFinished();
    }

    public List<DealListDto> findDealByMemberIdAndBuyer(Long memberId){
        return dealHistoryRepository.findDealByMemberIdAndBuyer(memberId);
    }

}
