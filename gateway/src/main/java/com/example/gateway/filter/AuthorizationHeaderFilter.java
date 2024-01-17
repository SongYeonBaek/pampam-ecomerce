package com.example.gateway.filter;

import com.example.gateway.util.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private final JwtTokenProvider jwtTokenProvider;

    public static class Config {

    }

    public AuthorizationHeaderFilter() {
        super(Config.class);
        jwtTokenProvider = new JwtTokenProvider();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            String token = exchange.getRequest().getHeaders().get("Authorization").get(0).substring(7);
            String userInfo = jwtTokenProvider.parseMemberIdFromToken(token);

            addAuthorizationHeaders(exchange.getRequest(), userInfo);

            return chain.filter(exchange);
        };
    }

    private void addAuthorizationHeaders(ServerHttpRequest request, String userInfo) {
        request.mutate()
                .header("X-Authorization-Id", userInfo)
                .build();
    }

    @Bean
    public ErrorWebExceptionHandler tokenValidation() {
        return new JwtTokenExceptionHandler();
    }
    // 실제 토큰이 null, 만료 등 예외 상황에 따른 예외처리
    public class JwtTokenExceptionHandler implements ErrorWebExceptionHandler {
        private String getErrorCode(int errorCode) {
            return "{\"errorCode\":" + errorCode + "}";
        }

        @Override
        public Mono<Void> handle(
                ServerWebExchange exchange, Throwable ex) {
            int errorCode = 500;
            if (ex.getClass() == NullPointerException.class) {
                errorCode = 100;
            } else if (ex.getClass() == ExpiredJwtException.class) {
                errorCode = 200;
            }

            byte[] bytes = getErrorCode(errorCode).getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Flux.just(buffer));
        }
    }

}
