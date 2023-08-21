package nuguri.nuguri_hobby.controller.hobbyHistory;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import nuguri.nuguri_hobby.dto.ChangeStatusRequestDto;
import nuguri.nuguri_hobby.dto.HobbyHistoryRequestDto;
import nuguri.nuguri_hobby.dto.member.MemberIdRequestDto;
import nuguri.nuguri_hobby.dto.response.ResponseDto;
import nuguri.nuguri_hobby.service.hobbyHistory.HobbyHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/hobby/history")
public class HobbyHistoryController {

    private final HobbyHistoryService hobbyHistoryService;


    @ApiOperation(value = "취미방 참여 신청")
    @PostMapping("/join")
    public ResponseEntity join(Long hobbyId, Long memberId){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(HttpStatus.OK.value(), "취미방 참여", hobbyHistoryService.joinHobbyHistory(hobbyId, memberId))
        );
    }

    @ApiOperation(value = "취미방 참여자 리스트")
    @GetMapping("/{hobbyId}/participant")
    public ResponseEntity participant(@PathVariable Long hobbyId){
        return ResponseEntity.status(HttpStatus.OK).body(
                hobbyHistoryService.findParticipantList(hobbyId)
        );
    }

    @ApiOperation(value = "취미방 승인 대기자 리스트")
    @GetMapping("/{hobbyId}/waiting")
    public ResponseEntity waiting(@PathVariable Long hobbyId){
        return ResponseEntity.status(HttpStatus.OK).body(
                hobbyHistoryService.findWaitingMemberList(hobbyId)
        );
    }

    @ApiOperation(value = "신청자 승인 또는 거절")
    @PutMapping("/changeStatus")
    public ResponseEntity changeStatus(@RequestBody ChangeStatusRequestDto changeStatusRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(HttpStatus.OK.value(), "신청자 승인 또는 거절", hobbyHistoryService.changeStatus(changeStatusRequestDto))
        );
    }

    @ApiOperation(value="방장이 취미방 생성")
    @PostMapping("/regist")
    public ResponseEntity regist(@RequestBody HobbyHistoryRequestDto hobbyHistoryRequestDto){
        System.out.println("hello1@@@@@@@@@@@@@@@@@@@@@@@@@");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(HttpStatus.OK.value(),"취미방 생성 완료",hobbyHistoryService.createHobbyHistory(hobbyHistoryRequestDto))
        );
    }

    @ApiOperation(value="memberId로 hobbyHistoryResponseDto 찾기")
    @PostMapping("/memberId/list")
        public ResponseEntity hobbyHistoryList(@RequestBody MemberIdRequestDto memberIdRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(
            hobbyHistoryService.findHistoryList(memberIdRequestDto.getMemberId())
        );
    }



}
