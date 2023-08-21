package nuguri.nuguri_member.client;

import nuguri.nuguri_member.dto.hobby.HobbyHistoryResponseDto;
import nuguri.nuguri_member.dto.member.MemberIdRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "hobby-service")
public interface HobbyServiceClient {

    @PostMapping("/app/hobby/member/approve")
    List<HobbyHistoryResponseDto> findMembersApproveHobby(@RequestBody MemberIdRequestDto memberIdRequestDto);

    @PostMapping("/app/hobby/member/ready")
    List<HobbyHistoryResponseDto> findMembersReadyHobby(@RequestBody MemberIdRequestDto memberIdRequestDto);

    @PostMapping("/app/hobby/member/promoter")
    List<HobbyHistoryResponseDto> findMembersPromoterHobby(@RequestBody MemberIdRequestDto memberIdRequestDto);

    @PostMapping("/app/hobby/member/favorite")
    List<HobbyHistoryResponseDto> findMembersfavoriteHobby(@RequestBody MemberIdRequestDto memberIdRequestDto);
}
