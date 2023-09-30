package nuguri.nuguri_member.service;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.List;
import nuguri.nuguri_member.dto.hobby.HobbyHistoryResponseDto;
import nuguri.nuguri_member.dto.member.MemberProfileDto;
import nuguri.nuguri_member.dto.member.MemberProfileModifyRequestDto;
import nuguri.nuguri_member.dto.member.MemberProfileModifyResponseDto;
import nuguri.nuguri_member.dto.member.MemberProfileRequestDto;
import nuguri.nuguri_member.exception.ex.CustomException;
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
import org.springframework.transaction.annotation.Transactional;

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

//        @DisplayName("사용자는 내 프로필을 조회할 수 있다.")
        @Test
        void 사용자는_내_프로필을_조회할_수_있다() {
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

//        @DisplayName("사용자는 다른 회원 프로필을 조회할 수 있다.")
        @Test
        void 사용자는_다른_회원_프로필을_조회할_수_있다(){
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

        @Transactional
        @DisplayName("사용자는 본인 프로필을 수정할 수 있다. - 프로필 이미지, 닉네임")
        @Test
        void modifyProfile_success() throws IOException {
            //given
            MockMultipartFile file = new MockMultipartFile("file",
                "image/test.png",
                    "image/png",
                    new FileInputStream("src/test/resources/image/test.png"));
            MemberProfileModifyRequestDto requestDto = createMemberProfileModifyRequestDto("testNick", "changed");
            String testToken = memberFactory.generateJwtToken("6");

            MemberProfileModifyResponseDto responseDto = MemberFactory.mockMemberProfileModifyResponseDto();

            //when
            MemberProfileModifyResponseDto profileModify = memberService.profileModify(file, requestDto, testToken);

            //then
            assertThat(profileModify.getNickname())
                    .isEqualTo(responseDto.getNickname());
        }

        @Transactional
        @DisplayName("사용자는 다른 회원 프로필을 수정할 수 없다.")
        @Test
        void modifyProfile_401failure() throws IOException{
            //given
            MockMultipartFile file = new MockMultipartFile("file",
                "image/test.png",
                "image/png",
                new FileInputStream("src/test/resources/image/test.png"));
            MemberProfileModifyRequestDto requestDto = createMemberProfileModifyRequestDto("AAA", "changed");
            String testToken = memberFactory.generateJwtToken("6");

            //then
            assertThatThrownBy(() -> memberService.profileModify(file, requestDto, testToken))
                .isInstanceOf(CustomException.class)
                .message();
        }
    }

    @DisplayName("profileHobbyReady")
    @Nested
    class describe_profileHobbyReady{

        @Test
        void 사용자는_본인의_대기중인_취미_모임방을_조회할_수_있다() {
            //given
            MemberProfileRequestDto requestDto = createMemberProfileRequestDto("testNick");
            String testToken = memberFactory.generateJwtToken("6");

            //when
            List<HobbyHistoryResponseDto> responseDto = memberService.profileHobbyReady(requestDto, testToken);

            //then
            assertThat(responseDto.get(0).getTitle()).isEqualTo("READY STATUS TEST HOBBY1 TITLE");
            assertThat(responseDto.get(1).getTitle()).isEqualTo("READY STATUS TEST HOBBY2 TITLE");
            assertThat(responseDto.get(2).getTitle()).isEqualTo("READY STATUS TEST HOBBY3 TITLE");
            assertThat(responseDto.size()).isEqualTo(3);
        }

        @Test
        void 사용자는_다른_회원의_대기중인_취미_모임방을_조회할_수_있다() {
            //given
            MemberProfileRequestDto requestDto = createMemberProfileRequestDto("testNick");
            String testToken = memberFactory.generateJwtToken("1");

            //when
            List<HobbyHistoryResponseDto> responseDto = memberService.profileHobbyReady(requestDto, testToken);

            //then
            assertThat(responseDto.get(0).getTitle()).isEqualTo("READY STATUS TEST HOBBY1 TITLE");
            assertThat(responseDto.get(1).getTitle()).isEqualTo("READY STATUS TEST HOBBY2 TITLE");
            assertThat(responseDto.get(2).getTitle()).isEqualTo("READY STATUS TEST HOBBY3 TITLE");
            assertThat(responseDto.size()).isEqualTo(3);
        }
    }

    @DisplayName("profileHobbyParticipation")
    @Nested
    class describe_profileHobbyParticipation{

        @Test
        void 사용자는_본인의_참여중인_취미_모임방을_조회할_수_있다() {
            //given
            MemberProfileRequestDto requestDto = createMemberProfileRequestDto("testNick");
            String testToken = memberFactory.generateJwtToken("6");

            //when
            List<HobbyHistoryResponseDto> responseDto = memberService.profileHobbyParticipation(requestDto, testToken);

            //then
            assertThat(responseDto.get(0).getTitle()).isEqualTo("PARTICIPATION STATUS TEST HOBBY1 TITLE");
            assertThat(responseDto.get(1).getTitle()).isEqualTo("PARTICIPATION STATUS TEST HOBBY2 TITLE");
            assertThat(responseDto.get(2).getTitle()).isEqualTo("PARTICIPATION STATUS TEST HOBBY3 TITLE");
            assertThat(responseDto.size()).isEqualTo(3);
        }

        @Test
        void 사용자는_다른_회원의_참여중인_취미_모임방을_조회할_수_있다() {
            //given
            MemberProfileRequestDto requestDto = createMemberProfileRequestDto("testNick");
            String testToken = memberFactory.generateJwtToken("1");

            //when
            List<HobbyHistoryResponseDto> responseDto = memberService.profileHobbyParticipation(requestDto, testToken);

            //then
            assertThat(responseDto.get(0).getTitle()).isEqualTo("PARTICIPATION STATUS TEST HOBBY1 TITLE");
            assertThat(responseDto.get(1).getTitle()).isEqualTo("PARTICIPATION STATUS TEST HOBBY2 TITLE");
            assertThat(responseDto.get(2).getTitle()).isEqualTo("PARTICIPATION STATUS TEST HOBBY3 TITLE");
            assertThat(responseDto.size()).isEqualTo(3);
        }
    }

    @DisplayName("profileHobbyManage")
    @Nested
    class describe_profileHobbyManage{

        @Test
        void 사용자는_본인의_운영중인_취미_모임방을_조회할_수_있다() {
            //given
            MemberProfileRequestDto requestDto = createMemberProfileRequestDto("testNick");
            String testToken = memberFactory.generateJwtToken("6");

            //when
            List<HobbyHistoryResponseDto> responseDto = memberService.profileHobbyManage(requestDto, testToken);

            //then
            assertThat(responseDto.get(0).getTitle()).isEqualTo("PROMOTER STATUS TEST HOBBY1 TITLE");
            assertThat(responseDto.get(1).getTitle()).isEqualTo("PROMOTER STATUS TEST HOBBY2 TITLE");
            assertThat(responseDto.get(2).getTitle()).isEqualTo("PROMOTER STATUS TEST HOBBY3 TITLE");
            assertThat(responseDto.size()).isEqualTo(3);
        }

        @Test
        void 사용자는_다른_회원의_운영중인_취미_모임방을_조회할_수_있다() {
            //given
            MemberProfileRequestDto requestDto = createMemberProfileRequestDto("testNick");
            String testToken = memberFactory.generateJwtToken("1");

            //when
            List<HobbyHistoryResponseDto> responseDto = memberService.profileHobbyManage(requestDto, testToken);

            //then
            assertThat(responseDto.get(0).getTitle()).isEqualTo("PROMOTER STATUS TEST HOBBY1 TITLE");
            assertThat(responseDto.get(1).getTitle()).isEqualTo("PROMOTER STATUS TEST HOBBY2 TITLE");
            assertThat(responseDto.get(2).getTitle()).isEqualTo("PROMOTER STATUS TEST HOBBY3 TITLE");
            assertThat(responseDto.size()).isEqualTo(3);
        }
    }

    @DisplayName("profileHobbyFavorite")
    @Nested
    class describe_profileHobbyFavorite{

        @Test
        void 사용자는_본인의_찜_중인_취미_모임방을_조회할_수_있다() {
            //given
            MemberProfileRequestDto requestDto = createMemberProfileRequestDto("testNick");
            String testToken = memberFactory.generateJwtToken("6");

            //when
            List<HobbyHistoryResponseDto> responseDto = memberService.profileHobbyFavorite(requestDto, testToken);

            //then
            assertThat(responseDto.get(0).getTitle()).isEqualTo("FAVORITE STATUS TEST HOBBY1 TITLE");
            assertThat(responseDto.get(1).getTitle()).isEqualTo("FAVORITE STATUS TEST HOBBY2 TITLE");
            assertThat(responseDto.get(2).getTitle()).isEqualTo("FAVORITE STATUS TEST HOBBY3 TITLE");
            assertThat(responseDto.size()).isEqualTo(3);
        }

        @Test
        void 사용자는_다른_회원의_찜_중인_취미_모임방을_조회할_수_없다_401() {
            //given
            MemberProfileRequestDto requestDto = createMemberProfileRequestDto("testNick");
            String testToken = memberFactory.generateJwtToken("1");

            //then
            assertThatThrownBy(() -> memberService.profileHobbyFavorite(requestDto, testToken))
                .isInstanceOf(CustomException.class)
                .message();
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