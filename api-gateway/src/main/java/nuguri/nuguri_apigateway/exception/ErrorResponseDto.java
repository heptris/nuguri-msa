package nuguri.nuguri_apigateway.exception;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponseDto {
    private String message;
    private int status;

    public ErrorResponseDto(String message, int status){
        this.message = message;
        this.status = status;
    }
}
