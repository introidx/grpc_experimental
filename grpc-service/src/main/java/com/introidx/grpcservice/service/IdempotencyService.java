package com.introidx.grpcservice.service;

import com.introidx.grpcservice.exception.CannotRemoveTokenException;
import com.introidx.grpcservice.exception.InvalidTokenException;
import com.introidx.grpcservice.exception.TokenGenerationException;
import com.introidx.grpcservice.redis.RedisUtil;
import io.grpc.Status;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdempotencyService {

    @Autowired
    RedisUtil redisService;

    public <T> String checkIdempotency(T object) throws Exception {
        long startTime = System.currentTimeMillis();
        String shaKey = "Service1_" + DigestUtils.sha256Hex(object.toString());

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Execution Time: " + executionTime + " milliseconds");

        System.out.println("Shakey " + shaKey);

        try {
            if (redisService.exists(shaKey)){
                throw new InvalidTokenException(Status.INVALID_ARGUMENT.withDescription("Idempotency-Key is invalid, please create a new key"));
            }
            redisService.setKey(shaKey, shaKey, 1000);
            return shaKey;
        }catch (Exception e){
            throw new TokenGenerationException(Status.INTERNAL.withDescription("Exception While Generating token"));
        }
    }

    public boolean removeToken(String token) {
        if (!redisService.exists(token)) {
            throw new InvalidTokenException(Status.INVALID_ARGUMENT.withDescription("Idempotency-Key is invalid, please create a new key"));
        }
        boolean remove = redisService.remove(token);
        if (!remove) {
            throw new CannotRemoveTokenException(Status.INTERNAL.withDescription("Error while deleting Idempotency-Key"));
        }
        return true;
    }


}
