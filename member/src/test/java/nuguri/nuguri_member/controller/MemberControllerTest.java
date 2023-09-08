package nuguri.nuguri_member.controller;

import nuguri.nuguri_member.dto.member.MemberProfileDto;
import nuguri.nuguri_member.dto.member.MemberProfileRequestDto;
import nuguri.nuguri_member.exception.ex.CustomException;
import nuguri.nuguri_member.factory.MemberFactory;
import nuguri.nuguri_member.repository.MemberRepository;
import nuguri.nuguri_member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MemberControllerTest {

    @Autowired
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    @Autowired
    private MemberFactory memberFactory;

    @DisplayName("profile")
    @Nested
    class describe_profile {

        @DisplayName("로그인 되어있을 때")
        @Nested
        class Context_Login {

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

        @DisplayName("비로그인 상태일 때")
        @Nested
        class Context_NonLogged{

            @DisplayName("사용자는 프로필을 조회할 수 없다.")
            @Test
            void getProfile_Failure_401(){

                //given
                MemberProfileRequestDto requestDto = createMemberProfileRequestDto("testNick");

                //then
                assertThatThrownBy(() -> memberService.profile(requestDto, null))
                        .isInstanceOf(CustomException.class);
//                    .hasFieldOrPropertyWithValue("message", "유효하지 않은 accessToken 입니다.")
//                    .hasFieldOrPropertyWithValue("status", HttpStatus.UNAUTHORIZED);
//                    .hasMessage("sdf");
            }
        }
    }

    @DisplayName("profileModify")
    @Nested
    class describe_profileModify{

        @DisplayName("로그인 되어있을 때")
        @Nested
        class Context_Login{

            @DisplayName("사용자는 본인 프로필을 수정할 수 있다. - 프로필 이미지, 닉네임")
            @Test
            void modifyProfile_success(){

            }
        }
    }


    private MemberProfileRequestDto createMemberProfileRequestDto(String nickname){
        return MemberProfileRequestDto.builder()
                .nickname(nickname)
                .build();
    }
}
