package nuguri.nuguri_member.service;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import nuguri.nuguri_member.client.AuthServiceClient;
import nuguri.nuguri_member.client.BaseAddressServiceClient;
import nuguri.nuguri_member.client.DealServiceClient;
import nuguri.nuguri_member.client.HobbyServiceClient;
import nuguri.nuguri_member.config.redis.RedisService;
import nuguri.nuguri_member.domain.Member;
import nuguri.nuguri_member.domain.s3.AwsS3;
import nuguri.nuguri_member.dto.baseaddress.BaseAddressIdRequestDto;
import nuguri.nuguri_member.dto.baseaddress.BaseAddressSidoGugunDongDto;
import nuguri.nuguri_member.dto.deal.DealListDto;
import nuguri.nuguri_member.dto.hobby.ApproveStatus;
import nuguri.nuguri_member.dto.hobby.HobbyHistoryResponseDto;
import nuguri.nuguri_member.dto.member.*;
import nuguri.nuguri_member.exception.ex.CustomException;
import nuguri.nuguri_member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import nuguri.nuguri_member.exception.ex.ErrorCode;
import nuguri.nuguri_member.service.s3.AwsS3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static nuguri.nuguri_member.exception.ex.ErrorCode.INVALID_ACCESS;
import static nuguri.nuguri_member.exception.ex.ErrorCode.MEMBER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final AwsS3Service awsS3Service;
    private final BaseAddressServiceClient baseAddressServiceClient;
    private final AuthServiceClient authServiceClient;
    private final DealServiceClient dealServiceClient;
    private final HobbyServiceClient hobbyServiceClient;

    private final RedisService redisService;

    @Value("${token.secret}")
    private String secret;

    /**
     * 회원 프로필 조회
     */
    @Transactional
    public MemberProfileDto profile(MemberProfileRequestDto requestDto, String token){
        MemberProfileDto memberProfileDto;

        Member member = getMemberFromToken(token);

        // 다른 회원 프로필 조회
        if(!requestDto.getNickname().equals(member.getNickname())){
            Member other = memberRepository.findByNickname(requestDto.getNickname()).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

            BaseAddressIdRequestDto baseAddressIdRequestDto = new BaseAddressIdRequestDto(other.getLocalId());

            BaseAddressSidoGugunDongDto baseAddressDto = baseAddressServiceClient.findByLocalId(baseAddressIdRequestDto);

            memberProfileDto = profileCreate(other, baseAddressDto);
        }

        // 본인 프로필 조회
        else {
            BaseAddressIdRequestDto baseAddressIdRequestDto = new BaseAddressIdRequestDto(member.getLocalId());

            BaseAddressSidoGugunDongDto baseAddressDto = baseAddressServiceClient.findByLocalId(baseAddressIdRequestDto);

            memberProfileDto = profileCreate(member, baseAddressDto);
        }

        return memberProfileDto;
    }


    @Transactional
    public MemberProfileModifyResponseDto profileModify(MultipartFile profileImage, MemberProfileModifyRequestDto requestDto, String token){

        Member member = getMemberFromToken(token);

        if(!requestDto.getBeforeNickname().equals(member.getNickname())){
            throw new CustomException(INVALID_ACCESS);
        }

        String profileImageUrl;
        String nickname;

        if(profileImage == null){
            profileImageUrl = member.getProfileImage();
        }else{
            AwsS3 awsS3 = new AwsS3();
            try {
                awsS3 = awsS3Service.upload(profileImage, "profileImage");
            }catch (IOException e){
                System.out.println(e);
            }
            profileImageUrl = awsS3.getPath();
        }

        if(requestDto == null){
            nickname = member.getNickname();
        } else {
            nickname = requestDto.getAfterNickname();
        }

        member.profileModify(profileImageUrl, nickname);
        redisService.setValues(String.valueOf(member.getId()) + ".", nickname);
        return new MemberProfileModifyResponseDto(profileImageUrl, nickname);
    }

//    @Transactional
//    public void profileFeed(){
//        return;
//    }

    /**
     * 취미 모임방 (대기 중)
     */
    @Transactional
    public List<HobbyHistoryResponseDto> profileHobbyReady(MemberProfileRequestDto requestDto, String token){
        List<HobbyHistoryResponseDto> hobbyHistoryResponseDtoList;

        Member member = getMemberFromToken(token);

        if (!requestDto.getNickname().equals(member.getNickname())) {
            Member other = memberRepository.findByNickname(requestDto.getNickname()).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
            Long otherId = other.getId();

            MemberIdRequestDto memberIdRequestDto = new MemberIdRequestDto(otherId);

            hobbyHistoryResponseDtoList = hobbyServiceClient.findMembersReadyHobby(memberIdRequestDto);

        } else {
            MemberIdRequestDto memberIdRequestDto = new MemberIdRequestDto(member.getId());

            hobbyHistoryResponseDtoList = hobbyServiceClient.findMembersReadyHobby(memberIdRequestDto);
        }
        return hobbyHistoryResponseDtoList;
    }

    /**
     * 취미 모임방 (참여 중)
     */
    @Transactional
    public List<HobbyHistoryResponseDto> profileHobbyParticipation(MemberProfileRequestDto requestDto, String token){
        List<HobbyHistoryResponseDto> hobbyHistoryResponseDtoList;

        Member member = getMemberFromToken(token);

        if (!requestDto.getNickname().equals(member.getNickname())) {
            Member other = memberRepository.findByNickname(requestDto.getNickname()).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
            Long otherId = other.getId();

            MemberIdRequestDto memberIdRequestDto = new MemberIdRequestDto(otherId);

            hobbyHistoryResponseDtoList = hobbyServiceClient.findMembersApproveHobby(memberIdRequestDto);

        } else {
            MemberIdRequestDto memberIdRequestDto = new MemberIdRequestDto(member.getId());

            hobbyHistoryResponseDtoList = hobbyServiceClient.findMembersApproveHobby(memberIdRequestDto);
        }
        return hobbyHistoryResponseDtoList;
    }

    /**
     * 취미 모임방 (운영 중)
     */
    @Transactional
    public List<HobbyHistoryResponseDto> profileHobbyManage(MemberProfileRequestDto requestDto, String token){
        List<HobbyHistoryResponseDto> hobbyHistoryResponseDtoList;

        Member member = getMemberFromToken(token);

        if (!requestDto.getNickname().equals(member.getNickname())) {
            Member other = memberRepository.findByNickname(requestDto.getNickname()).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
            Long otherId = other.getId();

            MemberIdRequestDto memberIdRequestDto = new MemberIdRequestDto(otherId);

            hobbyHistoryResponseDtoList = hobbyServiceClient.findMembersPromoterHobby(memberIdRequestDto);
        } else {
            MemberIdRequestDto memberIdRequestDto = new MemberIdRequestDto(member.getId());

            hobbyHistoryResponseDtoList = hobbyServiceClient.findMembersPromoterHobby(memberIdRequestDto);
        }
        return hobbyHistoryResponseDtoList;
    }

    /**
     * 취미 모임방 (찜)
     */
    @Transactional
    public List<HobbyHistoryResponseDto> profileHobbyFavorite(MemberProfileRequestDto requestDto, String token){
        List<HobbyHistoryResponseDto> hobbyHistoryResponseDtoList;

        Member member = getMemberFromToken(token);

        if (!requestDto.getNickname().equals(member.getNickname())) {
            throw new CustomException(INVALID_ACCESS);
        }

        MemberIdRequestDto memberIdRequestDto = new MemberIdRequestDto(member.getId());

        hobbyHistoryResponseDtoList = hobbyServiceClient.findMembersfavoriteHobby(memberIdRequestDto);

        return hobbyHistoryResponseDtoList;
    }


    /**
     * 중고 거래 (판매 중)
     */
    @Transactional
    public List<DealListDto> profileDealOnSale(MemberProfileRequestDto requestDto){
        List<DealListDto> dtoList;

        // 다른 회원 프로필 조회
        if(requestDto.getNickname() != null){
            Member member = memberRepository.findByNickname(requestDto.getNickname()).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

            Long memberId = member.getId();

            dtoList = dealServiceClient.findDealByMemberIdAndIsDealFalse(memberId);
        }
        // 본인 프로필 조회
        else {
            Long memberId = authServiceClient.getMemberIdBySecurityUtil();

            dtoList = dealServiceClient.findDealByMemberIdAndIsDealFalse(memberId);
        }
        return dtoList;
    }

    /**
     * 중고 거래 (판매 완료)
     */
    @Transactional
    public List<DealListDto> profileDealSoldOut(MemberProfileRequestDto requestDto){
        List<DealListDto> dtoList;

        // 다른 회원 프로필 조회
        if(requestDto.getNickname() != null){
            Member member = memberRepository.findByNickname(requestDto.getNickname()).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
            Long memberId = member.getId();

            dtoList = dealServiceClient.findDealByMemberIdAndIsDealTrue(memberId);

        }

        // 본인 프로필 조회
        else {
            Long memberId = authServiceClient.getMemberIdBySecurityUtil();

            dtoList = dealServiceClient.findDealByMemberIdAndIsDealTrue(memberId);

        }
        return dtoList;
    }
    /**
     * 중고 거래 (구매 완료)
     */
    @Transactional
    public List<DealListDto> profileDealPurchase(MemberProfileRequestDto requestDto, String token){
        List<DealListDto> dtoList;

        Member member = getMemberFromToken(token);

        // 다른 회원 프로필 조회
        if(!requestDto.getNickname().equals(member.getNickname())){
            throw new CustomException(INVALID_ACCESS);
        }

        // 본인 프로필 조회
        dtoList = dealServiceClient.findDealByMemberIdAndBuyer(member.getId());

        return dtoList;
    }
    /**
     * 중고 거래 (찜)
     */
    @Transactional
    public List<DealListDto> profileDealFavorite(MemberProfileRequestDto requestDto, String token){
        List<DealListDto> dtoList;

        Member member = getMemberFromToken(token);

        // 다른 회원 프로필 조회
        if(!requestDto.getNickname().equals(member.getNickname())){
            throw new CustomException(INVALID_ACCESS);
        }

        // 본인 프로필 조회
        dtoList = dealServiceClient.findDealByMemberIdAndIsFavorite(member.getId());

        return dtoList;
    }


    /**
     * 회원 성별, 나이, 지역 조회 (find by Id)
     */
    public MemberInfoResponseDto getMemberInfoById(MemberIdRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId()).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        MemberInfoResponseDto responseDto = MemberInfoResponseDto.builder()
                .age(member.getAge())
                .sex(member.getSex())
                .localId(member.getLocalId())
                .build();
        return  responseDto;
    }

    /**
     * 회원 ID, Password 조회 (find by email)
     */
    public Optional<MemberEmailResponseDto> getIdPasswordByEmail(MemberEmailRequestDto requestDto){
        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        Optional<MemberEmailResponseDto> responseDto;
        responseDto = Optional.ofNullable(MemberEmailResponseDto.builder()
                .Id(member.getId())
                .password(member.getPassword())
                .build());

        return responseDto;
    }

    /**
     * 회원 닉네임 조회 (find by email)
     */
    public MemberNicknameResponseDto getNicknameByEmail(MemberEmailRequestDto requestDto){
        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        MemberNicknameResponseDto responseDto = MemberNicknameResponseDto.builder()
                .nickname(member.getNickname())
                .build();

        return responseDto;
    }

    /**
     * 회원 닉네임 조회 (find by Id)
     */
    public MemberNicknameResponseDto getNicknameByMemberId(MemberIdRequestDto requestDto){
        Member member = memberRepository.findById(requestDto.getMemberId()).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        MemberNicknameResponseDto responseDto = MemberNicknameResponseDto.builder()
                .nickname(member.getNickname())
                .build();

        return responseDto;
    }

    /**
     * 회원 가입
     */
    public MemberJoinResponseDto signup(MemberJoinDto memberJoinDto){
        System.out.println("member-service signup : memberJoinDto = " + memberJoinDto);
        if(memberRepository.existsByEmail(memberJoinDto.getEmail())){
            throw new CustomException(ErrorCode.ALREADY_SAVED_MEMBER);
        }

        String[] memberAddress = memberJoinDto.getBaseAddress().split(" ");

        BaseAddressSidoGugunDongDto requestDto = BaseAddressSidoGugunDongDto.builder()
                .sido(memberAddress[0])
                .gugun(memberAddress[1])
                .dong(memberAddress[2])
                .build();

        Long localId = baseAddressServiceClient.findBySidoGugunDong(requestDto);

        Member member = memberJoinDto.toMember(passwordEncoder);
        member.changeProfileImage(randomProfileImage());
        member.changeLocalId(localId);
        member.changeTemperature(36.5);
        memberRepository.save(member);

        redisService.setValues(String.valueOf(member.getId()) + ".", member.getNickname());
        return new MemberJoinResponseDto(member.getEmail());
    }
//    @Transactional
//    public 공동구매

    private HobbyHistoryResponseDto createHobbyHistoryResponseDto(){
        return HobbyHistoryResponseDto.builder()
                .categoryId(1l)
                .title("Test Title")
                .endDate(LocalDateTime.now())
                .curNum(10)
                .maxNum(20)
                .wishlistNum(30l)
                .chatNum(40)
                .imageurl("S3 Image Url")
                .approveStatus(ApproveStatus.READY)
                .build();
    }

    private MemberProfileDto profileCreate(Member member, BaseAddressSidoGugunDongDto baseAddressDto){
        MemberProfileDto memberProfileDto = MemberProfileDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .description(member.getDescription())
                .temperature(member.getTemperature())
                .profileImage(member.getProfileImage())
                .baseAddress(baseAddressDto.getSido() + " " + baseAddressDto.getGugun() + " " + baseAddressDto.getDong())
                .localId(member.getLocalId())
                .build();
        return memberProfileDto;
    }


    @Transactional
    public boolean existsByNickname(String nickName){
        return memberRepository.existsByNickname(nickName);
    }

    /**
     * 랜덤 프로필 이미지
     */
    public String randomProfileImage(){
        int random = (int) (Math.random() * 6) + 1;
        String path = "member/default/" + random + ".jpg";
        String thumbnailPath = awsS3Service.getThumbnailPath(path);
        return thumbnailPath;
    }

    /**
     * token 복호화 -> Member
     */
    private Member getMemberFromToken(String token) {
        String jwt = token.replace("Bearer ", "");
        Long memberId = Long.parseLong(getMemberIdFromJwt(jwt));
        return memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
    }

    /**
     * jwt 토큰 복호화
     */
    public String getMemberIdFromJwt(String jwt){
        return Jwts.parserBuilder().setSigningKey(secret)
            .build().parseClaimsJws(jwt).getBody()
            .getSubject();
    }


    @PostConstruct
    public void init() {
        List<Member> memberList = memberRepository.findAll();
        memberList.forEach(member -> {
            redisService.setValues(String.valueOf(member.getId()) + ".", member.getNickname());
        });
    }

}
