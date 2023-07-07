package com.introidx.grpcservice.service;

import io.grpc.Metadata;

public interface TokenService {

    public String createToken();

    public boolean checkToken(Metadata metadata) throws Exception;


}