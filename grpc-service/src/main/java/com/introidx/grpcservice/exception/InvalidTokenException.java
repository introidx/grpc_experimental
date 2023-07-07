package com.introidx.grpcservice.exception;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import javax.annotation.Nullable;

public class InvalidTokenException extends StatusRuntimeException {


    public InvalidTokenException(Status status) {
        super(status);
    }

    public InvalidTokenException(Status status, @Nullable Metadata trailers) {
        super(status, trailers);
    }
}
