package nuguri.nuguri_member.service;

import com.netflix.discovery.converters.Auto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Optional;
import nuguri.nuguri_member.domain.Member;
import nuguri.nuguri_member.dto.deal.DealListDto;
import nuguri.nuguri_member.dto.member.MemberProfileDto;
import nuguri.nuguri_member.dto.member.MemberProfileRequestDto;
import nuguri.nuguri_member.exception.ex.CustomException;
import nuguri.nuguri_member.factory.MemberFactory;
import nuguri.nuguri_member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class MemberServiceTest {

//    @InjectMocks
    @Autowired
    private MemberService memberService;
//    @Mock
    @Mock
    private MemberRepository memberRepository;
//    @Mock
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