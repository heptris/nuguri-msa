package com.nuguri.dealservice.service;

import com.nuguri.dealservice.client.BasicClient;
import com.nuguri.dealservice.client.MemberClient;
import com.nuguri.dealservice.domain.Deal;
import com.nuguri.dealservice.domain.DealFavorite;
import com.nuguri.dealservice.domain.s3.AwsS3;
import com.nuguri.dealservice.dto.baseaddress.BaseAddressDto;
import com.nuguri.dealservice.dto.baseaddress.BaseAddressIdRequestDto;
import com.nuguri.dealservice.dto.baseaddress.BaseAddressSidoGugunDongDto;
import com.nuguri.dealservice.dto.category.CategoryListDto;
import com.nuguri.dealservice.dto.deal.*;
import com.nuguri.dealservice.dto.member.MemberIdRequestDto;
import com.nuguri.dealservice.dto.member.MemberInfoResponseDto;
import com.nuguri.dealservice.dto.member.MemberNicknameResponseDto;
import com.nuguri.dealservice.exception.ex.CustomException;
import com.nuguri.dealservice.messagequeue.KafkaProducer;
import com.nuguri.dealservice.repository.DealFavoriteRepository;
import com.nuguri.dealservice.repository.DealRepository;
import com.nuguri.dealservice.service.s3.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.nuguri.dealservice.exception.ex.ErrorCode.DEAL_FAVORITE_NOT_FOUND;
import static com.nuguri.dealservice.exception.ex.ErrorCode.DEAL_NOT_FOUND;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DealService {

    private final DealRepository dealRepository;
    private final DealFavoriteRepository dealFavoriteRepository;
    private final AwsS3Service awsS3Service;
    private final BasicClient basicClient;
    private final MemberClient memberClient;
    private final KafkaProducer kafkaProducer;

    public List<BaseAddressDto> getAllBaseaddress(){
        return basicClient.getAllBaseaddress();
    }
    public List<CategoryListDto> getAllCategory(){
        return basicClient.getAllCategory();
    }

    public DealDetailDto findDealDetail(Long dealId){
        DealDetailExceptDongDto dealDetailExceptDongDto = dealRepository.dealDetail(dealId).orElseThrow(() -> new CustomException(DEAL_NOT_FOUND));

        Deal deal = dealRepository.findById(dealId).orElseThrow(() -> new CustomException(DEAL_NOT_FOUND));
        BaseAddressSidoGugunDongDto sidoGugunDongDto = basicClient.findByLocalId(new BaseAddressIdRequestDto(deal.getLocalId()));

        Long memberId = dealDetailExceptDongDto.getSellerId();
        MemberNicknameResponseDto nicknameResponseDto = memberClient.getNicknameByMemberId(new MemberIdRequestDto(memberId));

        return DealDetailDto.builder()
                .isDeal(dealDetailExceptDongDto.isDeal())
                .dealId(dealDetailExceptDongDto.getDealId())
                .dealImage(dealDetailExceptDongDto.getDealImage())
                .description(dealDetailExceptDongDto.getDescription())
                .dong(sidoGugunDongDto.getDong())
                .hit(dealDetailExceptDongDto.getHit())
                .price(dealDetailExceptDongDto.getPrice())
                .sellerId(memberId)
                .sellerNickname(nicknameResponseDto.getNickname())
                .build();
    }

    public DealLoginDetailDto findLoginDealDetail(Long memberId, Long dealId){
        DealDetailExceptDongDto dealDetailExceptDongDto = dealRepository.dealDetail(dealId)
                .orElseThrow(() -> new CustomException(DEAL_NOT_FOUND));

        boolean isFavorite = dealFavoriteRepository.findIsFavoriteByMemberIdAndDealId(memberId, dealId);

        Deal deal = dealRepository.findById(dealId).orElseThrow(() -> new CustomException(DEAL_NOT_FOUND));
        BaseAddressSidoGugunDongDto sidoGugunDongDto = basicClient.findByLocalId(new BaseAddressIdRequestDto(deal.getLocalId()));

        Long sellerId = dealDetailExceptDongDto.getSellerId();
        MemberNicknameResponseDto nicknameResponseDto = memberClient.getNicknameByMemberId(new MemberIdRequestDto(sellerId));

        return DealLoginDetailDto.builder()
                .dealId(dealDetailExceptDongDto.getDealId())
                .title(dealDetailExceptDongDto.getTitle())
                .description(dealDetailExceptDongDto.getDescription())
                .price(dealDetailExceptDongDto.getPrice())
                .hit(dealDetailExceptDongDto.getHit())
                .isDeal(dealDetailExceptDongDto.isDeal())
                .dealImage(dealDetailExceptDongDto.getDealImage())
                .dong(sidoGugunDongDto.getDong())
                .isFavorite(isFavorite)
                .sellerId(sellerId)
                .sellerNickname(nicknameResponseDto.getNickname())
                .build();
    }

    @Transactional
    public void dealRegist(DealRegistRequestDto dealRegistRequestDto, MultipartFile dealImage){

        Deal deal = dealRegistRequestDto.toDeal();

        // Member로 부터 localId 데이터 필요
//        Long localId = 1L;
        MemberInfoResponseDto memberInfoById = memberClient.getMemberInfoById(MemberIdRequestDto.builder()
                .memberId(dealRegistRequestDto.getMemberId())
                .build()).getBody();
        Long localId = memberInfoById.getLocalId();

        // 중고거래 이미지
        AwsS3 awsS3 = new AwsS3();
        try {
            awsS3 = awsS3Service.upload(dealImage, "dealImage");
        }catch (IOException e){
            System.out.println(e);
        }
        String dealImageUrl = awsS3.getPath();

        deal.registDeal(localId, dealImageUrl);
        dealRepository.save(deal);
    }

    @Transactional
    public void createOrModifyDealFavorite(Long memberId, Long dealId){
        DealFavorite dealFavorite = dealFavoriteRepository.findByMemberIdAndDealId(memberId, dealId)
                .orElseThrow(() -> new CustomException(DEAL_FAVORITE_NOT_FOUND));
        if(dealFavorite == null) {
            Deal deal = dealRepository.findById(dealId).orElseThrow(()-> new CustomException(DEAL_NOT_FOUND));
            DealFavorite newDealFavorite = DealFavorite.builder()
                    .memberId(memberId)
                    .deal(deal)
                    .isFavorite(true)
                    .build();
            dealFavoriteRepository.save(newDealFavorite);
        }else{
            dealFavorite.changeFavorite();
        }
    }

    @Transactional
    public void updateDealDetail(DealUpdateDto dealUpdateDto, MultipartFile dealImage){
        Deal deal = dealRepository.findById(dealUpdateDto.getDealId())
                .orElseThrow(() -> new CustomException(DEAL_NOT_FOUND));

        // 중고거래 이미지
        AwsS3 awsS3 = new AwsS3();
        try {
            awsS3 = awsS3Service.upload(dealImage, "dealImage");
        }catch (IOException e){
            System.out.println(e);
        }
        String dealImageUrl = awsS3.getPath();

        deal.updateDeal(dealUpdateDto.getTitle(), dealUpdateDto.getDescription(), dealUpdateDto.getPrice(), dealImageUrl);
    }

    private final static String VIEWCOOKIENAME = "alreadyViewCookie";

    @Transactional
    public void increaseHit(Long dealId, HttpServletRequest request, HttpServletResponse response){
        Deal deal = dealRepository.findById(dealId).orElseThrow(()-> new CustomException(DEAL_NOT_FOUND));
        Cookie[] cookies = request.getCookies();
        boolean checkCookie = false;
        if(cookies != null){
            for (Cookie cookie : cookies)
            {
                // 이미 조회를 한 경우 체크
                if (cookie.getName().equals(VIEWCOOKIENAME+dealId)) checkCookie = true;

            }
            if(!checkCookie){
                Cookie newCookie = createCookieForForNotOverlap(dealId);
                response.addCookie(newCookie);
                deal.increaseHit();
            }
        } else {
            Cookie newCookie = createCookieForForNotOverlap(dealId);
            response.addCookie(newCookie);
            deal.increaseHit();
        }
    }

    /*
     * 조회수 중복 방지를 위한 쿠키 생성 메소드
     * @param cookie
     * @return
     * */
    private Cookie createCookieForForNotOverlap(Long dealId) {
        Cookie cookie = new Cookie(VIEWCOOKIENAME+dealId, String.valueOf(dealId));
        cookie.setComment("조회수 중복 증가 방지 쿠키");	// 쿠키 용도 설명 기재
        cookie.setMaxAge(getRemainSecondForTommorow()); 	// 하루를 준다.
        cookie.setHttpOnly(true);				// 서버에서만 조작 가능
        return cookie;
    }

    // 다음 날 정각까지 남은 시간(초)
    private int getRemainSecondForTommorow() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tommorow = LocalDateTime.now().plusDays(1L).truncatedTo(ChronoUnit.DAYS);
        return (int) now.until(tommorow, ChronoUnit.SECONDS);
    }

    public List<DealListDto> findDealByMemberId(Long memberId){
        return dealRepository.findDealByMemberId(memberId);
    }

    public List<DealListDto> findDealByMemberIdAndIsDealTrue(Long memberId){
        return dealRepository.findDealByMemberIdAndIsDealTrue(memberId);
    }

    public List<DealListDto> findDealByMemberIdAndIsDealFalse(Long memberId){
        return dealRepository.findDealByMemberIdAndIsDealFalse(memberId);
    }

    public List<DealListDto> findDealByMemberIdAndIsFavorite(Long memberId){
        return dealFavoriteRepository.findDealByMemberIdAndIsFavorite(memberId);
    }

}
