package nuguri.nuguri_apigateway.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // 401
    INVALID_ACCESS_TOKEN(401, "유효하지 않은 accessToken 입니다."),
    INVALID_ACCESS(401, "접근 권한이 없습니다.");

    private final int status;
    private final String message;

    }
