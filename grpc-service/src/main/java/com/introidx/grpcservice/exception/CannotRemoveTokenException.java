package com.introidx.grpcservice.exception;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import javax.annotation.Nullable;

public class CannotRemoveTokenException extends StatusRuntimeException {


    public CannotRemoveTokenException(Status status) {
        super(status);
    }

    public CannotRemoveTokenException(Status status, @Nullable Metadata trailers) {
        super(status, trailers);
    }
}
