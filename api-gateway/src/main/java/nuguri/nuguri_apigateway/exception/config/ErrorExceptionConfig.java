package nuguri.nuguri_apigateway.exception.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nuguri.nuguri_apigateway.exception.GlobalExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ErrorExceptionConfig {

    private final ObjectMapper objectMapper;
    @Bean
    public ErrorWebExceptionHandler globalExceptionHandler(){
        return new GlobalExceptionHandler(objectMapper);
    }
}
