package nuguri.nuguri_hobby.controller.hobby;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import nuguri.nuguri_hobby.domain.ApproveStatus;
import nuguri.nuguri_hobby.dto.*;
import nuguri.nuguri_hobby.dto.member.MemberIdRequestDto;
import nuguri.nuguri_hobby.dto.response.ResponseDto;
import nuguri.nuguri_hobby.service.hobby.HobbyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/hobby")
public class HobbyController {
    private final HobbyService hobbyService;

    @ApiOperation(value ="해당 지역과 카데고리에 대한 취미방 목록 조회, 카테고리가 null인 경우 지역에 대한 취미방만 조회")
    @PostMapping("/list")
    public ResponseEntity findLocalCategoryHobbyList(@RequestBody HobbyListRequestDto hobbyListRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(
                hobbyService.findLocalCategoryHobbyList(hobbyListRequestDto)
        );
    }
    @ApiOperation(value="취미방 상세페이지 조회")
    @GetMapping("/{hobbyId}/detail")
    public ResponseEntity findHobbyDetail(@PathVariable Long hobbyId){
        return ResponseEntity.status(HttpStatus.OK).body(
                hobbyService.findHobbyDetail(hobbyId)
        );
    }
    @ApiOperation(value="취미방 생성")
    @PostMapping("/regist")
    public ResponseEntity regist(@RequestPart HobbyCreateRequestDto hobbyCreateRequestDto,
                                 @RequestPart(value = "file", required = false) MultipartFile hobbyImage,
                                 Long memberId){
        // 취미방 생성
        hobbyService.createHobby(hobbyCreateRequestDto,hobbyImage,memberId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(HttpStatus.OK.value(), "취미방 생성","취미방 생성 완료")
        );
    }
    @ApiOperation(value="해당 멤버의 승인된 취미방")
    @PostMapping("/member/approve")
    public ResponseEntity findMembersApproveHobby(@RequestBody MemberIdRequestDto memberIdRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(
                hobbyService.findByMemberIdAndStatus(memberIdRequestDto.getMemberId(), ApproveStatus.APPROVE)
        );
    }
    @ApiOperation(value="해당 멤버의 대기중인 취미방")
    @PostMapping("/member/ready")
    public ResponseEntity findMembersReadyHobby(@RequestBody MemberIdRequestDto memberIdRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(
                hobbyService.findByMemberIdAndStatus(memberIdRequestDto.getMemberId(), ApproveStatus.READY)
        );
    }
    @ApiOperation(value="해당 멤버가 방장인 취미방")
    @PostMapping("/member/promoter")
    public ResponseEntity findMembersPromoterHobby(@RequestBody MemberIdRequestDto memberIdRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(
                hobbyService.findByMemberIdAndPromoter(memberIdRequestDto.getMemberId())
        );
    }
    @ApiOperation(value="해당 멤버가 즐겨찾기한 취미방")
    @PostMapping("/member/favorite")
    public ResponseEntity findMembersfavoriteHobby(@RequestBody MemberIdRequestDto memberIdRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(
                hobbyService.findByMemberIdAndFavorite(memberIdRequestDto.getMemberId())
        );
    }
}
