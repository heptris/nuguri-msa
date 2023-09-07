package nuguri.nuguri_member.service;

import java.io.FileInputStream;
import java.io.IOException;

import nuguri.nuguri_member.dto.member.MemberProfileDto;
import nuguri.nuguri_member.dto.member.MemberProfileModifyRequestDto;
import nuguri.nuguri_member.dto.member.MemberProfileRequestDto;
import nuguri.nuguri_member.factory.MemberFactory;
import nuguri.nuguri_member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    @Autowired
    private MemberFactory memberFactory;

    @DisplayName("profile")
    @Nested
    class describe_profile {

        @DisplayName("사용자는 내 프로필을 조회할 수 있다.")
        @Test
        void getMyProfile_Success() {
            //given
            MemberProfileRequestDto requestDto = createMemberProfileRequestDto("testNick");
            String testToken = memberFactory.generateJwtToken("6");

            MemberProfileDto responseDto = MemberFactory.mockMemberProfileDto();

            //when
            MemberProfileDto profile = memberService.profile(requestDto, testToken);

            //then
            assertThat(profile)
                .usingRecursiveComparison()
                .isEqualTo(responseDto);
        }

        @DisplayName("사용자는 다른 회원 프로필을 조회할 수 있다.")
        @Test
        void getOtherProfile_Success(){
            //given
            MemberProfileRequestDto requestDto = createMemberProfileRequestDto("testNick");
            String testToken = memberFactory.generateJwtToken("1");

            MemberProfileDto responseDto = MemberFactory.mockMemberProfileDto();

            //when
            MemberProfileDto profile = memberService.profile(requestDto, testToken);

            //then
            assertThat(profile)
                .usingRecursiveComparison()
                .isEqualTo(responseDto);
        }
    }

    @DisplayName("profileModify")
    @Nested
    class describe_profileModify{

        @DisplayName("사용자는 본인 프로필을 수정할 수 있다. - 프로필 이미지, 닉네임")
        @Test
        void modifyProfile_success() throws IOException {
            //given
            MockMultipartFile file = new MockMultipartFile("file",
                    "image/test.png",
                    "image/png",
                    new FileInputStream("classpath:image/test.png"));
            MemberProfileModifyRequestDto requestDto = createMemberProfileModifyRequestDto("testNick", "changed");
            String testToken = memberFactory.generateJwtToken("6");

            MemberProfileDto responseDto = MemberFactory.mockMemberProfileDto();

            //when
            MemberProfileDto profile = memberService.profile(requestDto, testToken);

            //then
            assertThat(profile)
                    .usingRecursiveComparison()
                    .isEqualTo(responseDto);
        }

        @DisplayName("사용자는 다른 회원 프로필을 수정할 수 없다.")
        @Test
        void modifyProfile_401failure(){

        }
    }


    private MemberProfileRequestDto createMemberProfileRequestDto(String nickname){
        return MemberProfileRequestDto.builder()
            .nickname(nickname)
            .build();
    }

    private MemberProfileModifyRequestDto createMemberProfileModifyRequestDto(String beforeNickname, String afterNickname){
        return MemberProfileModifyRequestDto.builder()
            .beforeNickname(beforeNickname)
            .afterNickname(afterNickname)
            .build();
    }
}