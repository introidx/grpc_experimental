package com.introidx.grpcclient.config;

import com.introidx.grpcservice.BusinessServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BusinessServiceClient {

    @Value("business-service.address")
    String address;

    private ManagedChannel getManagedChannel() {
        return ManagedChannelBuilder
                .forAddress("localhost", 8078)
                .usePlaintext()
                .build();
    }


    @Bean
    public BusinessServiceGrpc.BusinessServiceStub getAsyncClient(){
        return BusinessServiceGrpc.newStub(getManagedChannel());
    }

    @Bean
    public BusinessServiceGrpc.BusinessServiceBlockingStub getBlockingClient(){
        return BusinessServiceGrpc.newBlockingStub(getManagedChannel());
    }
}
