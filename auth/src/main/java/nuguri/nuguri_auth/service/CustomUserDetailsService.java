package nuguri.nuguri_auth.service;

import nuguri.nuguri_auth.client.MemberServiceClient;
import nuguri.nuguri_auth.dto.member.MemberEmailRequestDto;
import nuguri.nuguri_auth.dto.member.MemberEmailResponseDto;
import nuguri.nuguri_auth.exception.ex.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static nuguri.nuguri_auth.exception.ex.ErrorCode.MEMBER_EMAIL_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberServiceClient memberServiceClient;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberServiceClient.getIdPasswordByEmail(MemberEmailRequestDto.builder().email(username).build())
                .map(this::createUserDetails)
                .orElseThrow(() -> new CustomException(MEMBER_EMAIL_NOT_FOUND));

    }

    // DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(MemberEmailResponseDto member) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");
        return new User(
                String.valueOf(member.getId()),
                member.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
}