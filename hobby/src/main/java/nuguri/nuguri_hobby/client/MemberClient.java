package nuguri.nuguri_hobby.client;

import nuguri.nuguri_hobby.dto.member.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="MEMBER-SERVICE",path="/app/member")
public interface MemberClient {

    @PostMapping("/to/hobby/constraints")
    ResponseEntity<MemberInfoResponseDto> getMemberInfoById(@RequestBody MemberIdRequestDto requestDto);

    // memberId -> nickname
    @PostMapping("/member-id")
    public ResponseEntity<MemberNicknameResponseDto> getNicknameByMemberId(@RequestBody MemberIdRequestDto requestDto);

    // nickname -> profile
    @PostMapping
    public ResponseEntity<MemberProfileDto> profile(@RequestBody MemberProfileRequestDto requestDto);

}
