package com.introidx.grpcclient.service;


import com.google.protobuf.Descriptors;
import com.introidx.grpcservice.Business;
import com.introidx.grpcservice.BusinessServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class BusinessClientService {


    @Autowired
    BusinessServiceGrpc.BusinessServiceStub asyncClient;

    @Autowired
    BusinessServiceGrpc.BusinessServiceBlockingStub syncClient;

    public Map<Descriptors.FieldDescriptor, Object> getToken(){
        Business.Empty be = Business.Empty.newBuilder().build();
        Map<Descriptors.FieldDescriptor, Object> map = syncClient.getToken(be).getAllFields();
        return map;
    }

    public Map<Descriptors.FieldDescriptor, Object> getName(String name) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        Business.NameRequest request = Business.NameRequest.newBuilder()
                .setName(name)
                .build();

        // todo add Idempotency key as metadata

        Map<Descriptors.FieldDescriptor, Object> map = new HashMap<>();
        asyncClient.testPostWithIdempotency(request, new StreamObserver<Business.NameResponse>() {
            @Override
            public void onNext(Business.NameResponse nameResponse) {
                map.putAll(nameResponse.getAllFields());
            }

            @Override
            public void onError(Throwable throwable) {
                throw new RuntimeException(throwable.getMessage(), throwable.getCause());
            }

            @Override
            public void onCompleted() {
                //countDownLatch.countDown();
            }
        });
        return map;

    }

}
