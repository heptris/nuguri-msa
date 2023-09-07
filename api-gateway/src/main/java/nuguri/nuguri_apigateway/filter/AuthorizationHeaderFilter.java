package nuguri.nuguri_apigateway.filter;

import static nuguri.nuguri_apigateway.exception.custom.ErrorCode.*;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import nuguri.nuguri_apigateway.exception.custom.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> implements
    Ordered {

    @Value("${token.secret}")
    private String secret;

    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    @Override
    public int getOrder() {
        return -2;
    }

    public static class Config {}

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "");

            if(!isJwtValid(jwt)){
                throw new CustomException(INVALID_ACCESS_TOKEN);
            }

            return chain.filter(exchange);
        });
    }


    private boolean isJwtValid(String jwt){
        boolean returnValue = true;

        String subject = null;

        try {
            subject = Jwts.parserBuilder().setSigningKey(secret)
                    .build().parseClaimsJws(jwt).getBody()
                    .getSubject();
        } catch (Exception ex){
            returnValue = false;
        }

        if(subject == null || subject.isEmpty()){
            returnValue = false;
        }

        return returnValue;
    }
}
