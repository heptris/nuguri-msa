package nuguri.nuguri_auth.client;

import nuguri.nuguri_auth.dto.auth.MemberJoinDto;
import nuguri.nuguri_auth.dto.member.MemberEmailRequestDto;
import nuguri.nuguri_auth.dto.member.MemberEmailResponseDto;
import nuguri.nuguri_auth.dto.member.MemberIdRequestDto;
import nuguri.nuguri_auth.dto.member.MemberNicknameResponseDto;
import nuguri.nuguri_auth.dto.response.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "member-service")
public interface MemberServiceClient {

    @PostMapping("/app/member/login-info")
    Optional<MemberEmailResponseDto> getIdPasswordByEmail(@RequestBody MemberEmailRequestDto requestDto);

    @PostMapping("/app/member/nickname")
    MemberNicknameResponseDto getNicknameByEmail(@RequestBody MemberEmailRequestDto requestDto);

    @PostMapping("/app/member/member-id")
    MemberNicknameResponseDto getNicknameByMemberId(@RequestBody MemberIdRequestDto requestDto);

    @PostMapping("/app/member/signup")
    ResponseDto signup(@RequestBody MemberJoinDto requestDto);

}
