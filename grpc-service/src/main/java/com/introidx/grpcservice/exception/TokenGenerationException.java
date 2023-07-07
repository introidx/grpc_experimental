package com.introidx.grpcservice.exception;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import javax.annotation.Nullable;

public class TokenGenerationException extends StatusRuntimeException {
    public TokenGenerationException(Status status) {
        super(status);
    }

    public TokenGenerationException(Status status, @Nullable Metadata trailers) {
        super(status, trailers);
    }
}
