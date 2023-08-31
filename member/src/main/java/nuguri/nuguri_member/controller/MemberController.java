package nuguri.nuguri_member.controller;

import nuguri.nuguri_member.dto.member.*;
import nuguri.nuguri_member.dto.response.ResponseDto;
import nuguri.nuguri_member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/app/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * id, 나이, 성별, 위치
     * @return MemberInfoResponseDto
     */
    @PostMapping("/to/hobby/constraints")
    public ResponseEntity getMemberInfoById(@RequestBody MemberIdRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.OK).body(
                memberService.getMemberInfoById(requestDto)
        );
    }

    @PostMapping("/login-info")
    public ResponseEntity getIdPasswordByEmail(@RequestBody MemberEmailRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.OK).body(
                memberService.getIdPasswordByEmail(requestDto)
        );
    }

    @PostMapping("/nickname")
    public ResponseEntity getNicknameByEmail(@RequestBody MemberEmailRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.OK).body(
                memberService.getNicknameByEmail(requestDto)
        );
    }

    @PostMapping("/member-id")
    public ResponseEntity getNicknameByMemberId(@RequestBody MemberIdRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.OK).body(
                memberService.getNicknameByMemberId(requestDto)
        );
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody MemberJoinDto requestDto){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto<>(HttpStatus.OK.value(), "회원 가입", memberService.signup(requestDto))
        );
    }

    /**
     * 프로필 조회
     */
    @PostMapping
    public ResponseEntity profile(@RequestBody MemberProfileRequestDto requestDto, @RequestHeader("Authorization") String token){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto<>(HttpStatus.OK.value(), "회원 프로필 조회", memberService.profile(requestDto, token))
        );
    }

    @PostMapping("/modify")
    public ResponseEntity profileModify(@RequestPart(value = "file", required = false) MultipartFile profileImage,
                                        @RequestPart(required = false) MemberProfileModifyRequestDto requestDto,
                                        @RequestHeader("Authorization") String token){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto<>(HttpStatus.OK.value(), "회원 프로필 수정", memberService.profileModify(profileImage, requestDto, token))
        );
    }

    @PostMapping("/deal/on-sale")
    public ResponseEntity profileDealOnSale(@RequestBody MemberProfileRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto<>(HttpStatus.OK.value(), "회원 중고 거래(판매 중) 조회", memberService.profileDealOnSale(requestDto))
        );
    }

    @PostMapping("/deal/sold-out")
    public ResponseEntity profileDealSoldOut(@RequestBody MemberProfileRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto<>(HttpStatus.OK.value(), "회원 중고 거래(판매 완료) 조회", memberService.profileDealSoldOut(requestDto))
        );
    }
    @PostMapping("/deal/purchase")
    public ResponseEntity profileDealPurchase(@RequestBody MemberProfileRequestDto requestDto, @RequestHeader("Authorization") String token){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto<>(HttpStatus.OK.value(), "회원 중고 거래(구매 완료) 조회", memberService.profileDealPurchase(requestDto, token))
        );
    }
    @PostMapping("/deal/favorite")
    public ResponseEntity profileDealFavorite(@RequestBody MemberProfileRequestDto requestDto, @RequestHeader("Authorization") String token){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto<>(HttpStatus.OK.value(), "회원 중고 거래(찜) 조회", memberService.profileDealFavorite(requestDto, token))
        );
    }

    @PostMapping("/hobby/ready")
    public ResponseEntity profileHobbyReady(@RequestBody MemberProfileRequestDto requestDto, @RequestHeader("Authorization") String token){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto<>(HttpStatus.OK.value(), "회원 취미 모임방(대기 중) 조회", memberService.profileHobbyReady(requestDto, token))
        );
    }

    @PostMapping("/hobby/participation")
    public ResponseEntity profileHobbyParticipation(@RequestBody MemberProfileRequestDto requestDto, @RequestHeader("Authorization") String token){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto<>(HttpStatus.OK.value(), "회원 취미 모임방(참여 중) 조회", memberService.profileHobbyParticipation(requestDto, token))
        );
    }
    @PostMapping("/hobby/manage")
    public ResponseEntity profileHobbyManage(@RequestBody MemberProfileRequestDto requestDto, @RequestHeader("Authorization") String token){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto<>(HttpStatus.OK.value(), "회원 취미 모임방(운영 중) 조회", memberService.profileHobbyManage(requestDto, token))
        );
    }
    @PostMapping("/hobby/favorite")
    public ResponseEntity profileHobbyFavorite(@RequestBody MemberProfileRequestDto requestDto, @RequestHeader("Authorization") String token){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto<>(HttpStatus.OK.value(), "회원 취미 모임방(찜) 조회", memberService.profileHobbyFavorite(requestDto, token))
        );
    }
}
