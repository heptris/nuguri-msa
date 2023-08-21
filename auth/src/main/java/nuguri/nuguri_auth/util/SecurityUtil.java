package nuguri.nuguri_auth.util;

import lombok.extern.slf4j.Slf4j;
import nuguri.nuguri_auth.exception.ex.CustomException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static nuguri.nuguri_auth.exception.ex.ErrorCode.INVALID_ACCESS_TOKEN;

@Slf4j
public class SecurityUtil {

    private SecurityUtil() { }

    // SecurityContext 에 유저 정보가 저장되는 시점
    // Request 가 들어올 때 JwtFilter 의 doFilter 에서 저장
    public static Long getCurrentMemberId() throws NumberFormatException{
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw  new CustomException(INVALID_ACCESS_TOKEN);
        }

        return Long.parseLong(authentication.getName());
    }
}