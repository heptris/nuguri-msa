package com.nuguri.dealservice.controller;

import com.nuguri.dealservice.dto.deal.DealFinishedDto;
import com.nuguri.dealservice.dto.deal.DealHistoryUpdateDto;
import com.nuguri.dealservice.dto.response.ResponseDto;
import com.nuguri.dealservice.service.DealHistoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/app/deal-history")
public class DealHistoryController {

    private final DealHistoryService dealHistoryService;

    @ApiOperation(value = "구매자가 채팅하기 눌렀을 때 로그 쌓이기 위한 API, 생긴 중고거래 history_id와 중복 체크 isDuplicated 반환")
    @PostMapping("/{memberId}/{dealId}/create")
    public ResponseEntity pushChatButton(@PathVariable Long memberId, @PathVariable Long dealId){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(HttpStatus.OK.value(), "중고거래 History 생성",
                        dealHistoryService.createDealHistory(memberId, dealId))
        );
    }

    @ApiOperation(value = "채팅 중 문의자를 구매예정자로 바꾸는 버튼을 누르고 약속 시간, 장소를 정했을 때 API")
    @PutMapping("/update")
    public ResponseEntity updateToReserver(@RequestBody DealHistoryUpdateDto dealHistoryUpdateDto){
        dealHistoryService.updateToReserver(dealHistoryUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(HttpStatus.OK.value(), "약속시간, 장소 저장", "약속시간, 장소 저장 완료 !!")
        );
    }

    @ApiOperation(value = "판매자가 판매완료 버튼 눌렀을 때 API")
    @PostMapping("/finish")
    public ResponseEntity dealFinished(@RequestBody DealFinishedDto dealFinishedDto){
        dealHistoryService.dealFinished(dealFinishedDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(HttpStatus.OK.value(), "해당 구매자, 판매자 판매 완료 처리", "판매완료 처리 완료 !!")
        );
    }

    @ApiOperation(value = "memberId로 내가 거래한 중고거래 dealListDto 목록 조회")
    @GetMapping("/{memberId}/list/buyer")
    public ResponseEntity findDealByMemberIdAndBuyer(@PathVariable Long memberId){
        return ResponseEntity.status(HttpStatus.OK).body(dealHistoryService.findDealByMemberIdAndBuyer(memberId));
    }

}
