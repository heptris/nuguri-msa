package nuguri.nuguri_hobby.client;

import nuguri.nuguri_hobby.dto.member.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberClientTest {

    @Autowired
    MemberClient memberClient;

    @Test
    public void memberTest(){
        MemberInfoResponseDto memberInfoResponseDto = memberClient.getMemberInfoById(MemberIdRequestDto.builder()
                .memberId(1L).build()).getBody();
        System.out.println("memberInfoResponseDto = " + memberInfoResponseDto);
    }

    @Test
    public void getNicknameByMemberId(){
        MemberIdRequestDto memberIdRequestDto = MemberIdRequestDto.builder().memberId(1L).build();
        String nickname = memberClient.getNicknameByMemberId(memberIdRequestDto).getBody().getNickname();
        System.out.println(nickname+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }
    @Test
    public void profile(){
        MemberProfileRequestDto memberProfileRequestDto = MemberProfileRequestDto.builder().nickname("AAA").build();
        MemberProfileDto result = memberClient.profile(memberProfileRequestDto).getBody();
        System.out.println(result.getProfileImage()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}