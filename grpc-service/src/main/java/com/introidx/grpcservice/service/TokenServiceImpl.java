package com.introidx.grpcservice.service;

import com.introidx.grpcservice.exception.CannotRemoveTokenException;
import com.introidx.grpcservice.exception.InvalidTokenException;
import com.introidx.grpcservice.exception.TokenNotFoundException;
import com.introidx.grpcservice.redis.RedisUtil;
import io.grpc.Metadata;
import io.grpc.Status;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService{

    final RedisUtil redisService;
    public static String TOKEN_NAME = "Idempotency-Key";

    public TokenServiceImpl(RedisUtil redisUtil) {
        this.redisService = redisUtil;
    }

    @Override
    public String createToken() {
        String str = UUID.randomUUID().toString().replace("-", "");
        StringBuilder token = new StringBuilder(str);
        try {
            redisService.setKey(token.toString(), token.toString(), 1000L);
            boolean empty = StringUtils.isEmpty(token.toString());
            if (!empty) {
                return token.toString();
            }
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public boolean checkToken(Metadata metadata) throws Exception {
        String token = metadata.get(Metadata.Key.of(TOKEN_NAME, Metadata.ASCII_STRING_MARSHALLER));
        if (StringUtils.isEmpty(token)){
            throw new TokenNotFoundException(Status.NOT_FOUND.withDescription("Idempotency-Key is not provided in request header"));
        }
        if (!redisService.exists(token)){
            throw new InvalidTokenException(Status.INVALID_ARGUMENT.withDescription("Idempotency-Key is invalid, please create a new key"));
        }
        boolean remove = redisService.remove(token);
        if (!remove){
            throw new CannotRemoveTokenException(Status.INTERNAL.withDescription("Error while deleting Idempotency-Key"));
        }
        return true;
    }
}
