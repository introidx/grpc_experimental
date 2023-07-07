package com.introidx.grpcservice.exception;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcAdvice
public class GrpcExceptionAdvice {

    @GrpcExceptionHandler
    public Status handleInvalidArgument(IllegalArgumentException e){
        return Status.INVALID_ARGUMENT.withDescription("Invalid Argument Exception").withCause(e);
    }

    @GrpcExceptionHandler(InvalidTokenException.class)
    public StatusRuntimeException handleInvalidTokenException(InvalidTokenException ex){
        return Status.INVALID_ARGUMENT.withDescription(ex.getMessage()).withCause(ex).asRuntimeException();
    }

    @GrpcExceptionHandler(TokenNotFoundException.class)
    public StatusRuntimeException handleTokenNotFoundException(TokenNotFoundException ex){
        return Status.NOT_FOUND.withDescription(ex.getMessage()).withCause(ex).asRuntimeException();
    }

    @GrpcExceptionHandler(CannotRemoveTokenException.class)
    public StatusRuntimeException handleCannotDeleteTokenException(CannotRemoveTokenException ex){
        return Status.INTERNAL.withDescription(ex.getMessage()).withCause(ex).asRuntimeException();
    }

    @GrpcExceptionHandler(TokenGenerationException.class)
    public StatusRuntimeException handleTokenGenerationException(TokenGenerationException ex){
        return Status.INTERNAL.withDescription(ex.getMessage()).withCause(ex).asRuntimeException();
    }
}
