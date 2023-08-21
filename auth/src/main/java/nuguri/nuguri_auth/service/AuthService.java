package nuguri.nuguri_auth.service;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import nuguri.nuguri_auth.client.MemberServiceClient;
import nuguri.nuguri_auth.config.jwt.TokenProvider;
import nuguri.nuguri_auth.config.redis.RedisService;
import nuguri.nuguri_auth.dto.auth.MemberJoinDto;
import nuguri.nuguri_auth.dto.auth.MemberJoinResponseDto;
import nuguri.nuguri_auth.dto.auth.MemberLoginDto;
import nuguri.nuguri_auth.dto.member.MemberEmailRequestDto;
import nuguri.nuguri_auth.dto.member.MemberIdRequestDto;
import nuguri.nuguri_auth.dto.member.MemberNicknameResponseDto;
import nuguri.nuguri_auth.dto.response.ResponseDto;
import nuguri.nuguri_auth.dto.token.TokenDto;
import nuguri.nuguri_auth.dto.token.TokenRequestDto;
import nuguri.nuguri_auth.exception.ex.CustomException;
import nuguri.nuguri_auth.service.s3.AwsS3Service;
import lombok.RequiredArgsConstructor;
import nuguri.nuguri_auth.exception.ex.ErrorCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static nuguri.nuguri_auth.exception.ex.ErrorCode.INVALID_REFRESH_TOKEN;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AwsS3Service awsS3Service;
    private final MemberServiceClient memberServiceClient;

    private final RedisService redisService;

    /**
     * 회원가입
     */
    @Transactional
    public MemberJoinResponseDto signup(MemberJoinDto memberJoinDto) {

        ResponseDto signupSuccess = memberServiceClient.signup(memberJoinDto);

        return new MemberJoinResponseDto(signupSuccess.getData().toString());
    }

    /**
     * 로그인
     */
    @Transactional
    public TokenDto login(MemberLoginDto memberLoginDto){
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberLoginDto.toAuthentication();

        MemberEmailRequestDto requestDto = MemberEmailRequestDto.builder()
                .email(memberLoginDto.getEmail())
                .build();

        MemberNicknameResponseDto member = null;
        try{
            member = memberServiceClient.getNicknameByEmail(requestDto);
        } catch (FeignException ex){
            log.error(ex.getMessage());
        }

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        tokenDto.setNickname(member.getNickname());
        // 5. 토큰 발급
        return tokenDto;
    }

    /**
     * 토큰 재발행
     */
    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto){
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new CustomException(INVALID_REFRESH_TOKEN);
        }


        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getRefreshToken());

        // redis에 있는 refreshToken과 비교
        tokenProvider.checkRefreshToken(authentication.getName(), tokenRequestDto.getRefreshToken());

        MemberIdRequestDto requestDto = MemberIdRequestDto.builder()
                .id(Long.parseLong(authentication.getName()))
                .build();

        MemberNicknameResponseDto member = memberServiceClient.getNicknameByMemberId(requestDto);

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        tokenDto.setNickname(member.getNickname());

        // 토큰 발급
        return tokenDto;
    }

    /**
     * 로그아웃
     */
    @Transactional
    public void logout(String accessToken) {
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        tokenProvider.logout(authentication.getName(), accessToken);
    }

}
