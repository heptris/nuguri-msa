package nuguri.nuguri_apigateway.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuguri.nuguri_apigateway.exception.custom.CustomException;
import nuguri.nuguri_apigateway.exception.custom.ErrorCode;
import org.apache.http.HttpHeaders;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Order(-1)
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if(response.isCommitted()){
            return Mono.error(ex);
        }

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        if(ex instanceof ResponseStatusException){
            response.setStatusCode(((ResponseStatusException) ex).getStatus());
        }

        CustomException customException = new CustomException(ErrorCode.INVALID_ACCESS_TOKEN);

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(customException.getErrorCode().getMessage(), customException.getErrorCode()
            .getStatus());

        String error = "Gateway Error";

        try {
            error = objectMapper.writeValueAsString(errorResponseDto);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException : " + e.getMessage());
        }

        byte[] bytes = error.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }
}
