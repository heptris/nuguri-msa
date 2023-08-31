package com.nuguri.dealservice.controller;

import com.nuguri.dealservice.dto.deal.DealRegistRequestDto;
import com.nuguri.dealservice.dto.deal.DealUpdateDto;
import com.nuguri.dealservice.dto.response.ResponseDto;
import com.nuguri.dealservice.service.DealService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/app/deal")
public class DealController {

    private final DealService dealService;

    @ApiOperation(value = "비로그인시 중고거래 상세페이지 조회")
    @GetMapping("/{dealId}/detail")
    public ResponseEntity findDealDetail(@PathVariable Long dealId){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(HttpStatus.OK.value(),"비로그인시 중고거래 상세페이지", dealService.findDealDetail(dealId))
        );
    }

    @ApiOperation(value = "중고거래 등록")
    @PostMapping("/regist")
    public ResponseEntity dealRegist(@RequestPart DealRegistRequestDto dealRegistRequestDto,
                                     @RequestPart(value = "file", required = false) MultipartFile dealImage){
        dealService.dealRegist(dealRegistRequestDto, dealImage);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(HttpStatus.OK.value(), "중고거래 등록", "중고거래 등록 완료 !!")
        );
    }

    @ApiOperation(value = "등록된 중고거래 수정")
    @PutMapping
    public ResponseEntity updateDealDetail(@RequestPart DealUpdateDto dealUpdateDto,
                                           @RequestPart(value = "file", required = false) MultipartFile dealImage){
        dealService.updateDealDetail(dealUpdateDto, dealImage);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(HttpStatus.OK.value(), "중고거래 수정", "중고거래 수정 완료 !!")
        );
    }

    @ApiOperation(value = "중고거래 즐겨찾기 등록/해제")
    @PostMapping("/{memberId}/{dealId}/favorite")
    public ResponseEntity createOrModifyDealFavorite(@PathVariable Long memberId, @PathVariable Long dealId){
        dealService.createOrModifyDealFavorite(memberId, dealId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(HttpStatus.OK.value(), "중고거래 즐겨찾기 등록/해제", "중고거래 즐겨찾기 등록/해제 완료 !!")
        );
    }

    @ApiOperation(value = "중복 증가 방지된 중고거래 조회수 증가")
    @PostMapping("/{dealId}/hit")
    public ResponseEntity increaseHit(@PathVariable Long dealId, HttpServletRequest request, HttpServletResponse response){
        dealService.increaseHit(dealId, request, response);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(HttpStatus.OK.value(), "중복 증가 방지된 중고거래 조회수 증가", "중고거래 조회수 증가 완료 !!")
        );
    }

    @ApiOperation(value = "memberId로 dealListDto 목록 조회")
    @GetMapping("/{memberId}/list")
    public ResponseEntity findDealByMemberId(@PathVariable Long memberId){
        return ResponseEntity.status(HttpStatus.OK).body(dealService.findDealByMemberId(memberId));
    }

    @ApiOperation(value = "memberId로 판매완료된 중고거래 dealListDto 목록 조회")
    @GetMapping("/{memberId}/list/true")
    public ResponseEntity findDealByMemberIdAndIsDealTrue(@PathVariable Long memberId){
        return ResponseEntity.status(HttpStatus.OK).body(dealService.findDealByMemberIdAndIsDealTrue(memberId));
    }

    @ApiOperation(value = "memberId로 판매중인 중고거래 dealListDto 목록 조회")
    @GetMapping("/{memberId}/list/false")
    public ResponseEntity findDealByMemberIdAndIsDealFalse(@PathVariable Long memberId){
        return ResponseEntity.status(HttpStatus.OK).body(dealService.findDealByMemberIdAndIsDealFalse(memberId));
    }

    @ApiOperation(value = "memberId로 즐겨찾기한 중고거래 dealListDto 목록 조회")
    @GetMapping("/{memberId}/list/favorite")
    public ResponseEntity findDealByMemberIdAndIsFavorite(@PathVariable Long memberId){
        return ResponseEntity.status(HttpStatus.OK).body(dealService.findDealByMemberIdAndIsFavorite(memberId));
    }


}
