package com.introidx.grpcservice.exception;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import javax.annotation.Nullable;

public class TokenNotFoundException extends StatusRuntimeException {


    public TokenNotFoundException(Status status) {
        super(status);
    }

    public TokenNotFoundException(Status status, @Nullable Metadata trailers) {
        super(status, trailers);
    }
}
