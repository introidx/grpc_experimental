package com.introidx.grpcservice.service.grpc;

import com.introidx.grpcservice.Business;
import com.introidx.grpcservice.BusinessServiceGrpc;
import com.introidx.grpcservice.exception.CannotRemoveTokenException;
import com.introidx.grpcservice.exception.InvalidTokenException;
import com.introidx.grpcservice.exception.TokenNotFoundException;
import com.introidx.grpcservice.service.IdempotencyService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class BusinessServiceImpl extends BusinessServiceGrpc.BusinessServiceImplBase {

    @Autowired
    IdempotencyService idempotencyService;

    List<Business.UserRequest> users = new ArrayList<>();

    @Override
    public void createUser(Business.UserRequest request, StreamObserver<Business.UserResponse> responseObserver) {
        try {
            String token = idempotencyService.checkIdempotency(request.toString());
            Thread.sleep(20000);
            users.add(request);
            idempotencyService.removeToken(token);
            
            Business.UserResponse userResponse = Business.UserResponse.newBuilder()
                            .setName(request.getName())
                            .setAddress(request.getAddress())
                            .setPhone(request.getPhone())
                            .build();

            responseObserver.onNext(userResponse);
            responseObserver.onCompleted();
        }catch (TokenNotFoundException ex){
            responseObserver.onError(ex);
        }catch (InvalidTokenException ex) {
            responseObserver.onError(ex);
        }catch (CannotRemoveTokenException ex){
            responseObserver.onError(ex);
        }catch (Exception ex){
            responseObserver.onError(ex);
        }
    }


//    @Autowired
//    private TokenService tokenService;
//
//    List<String> names = new ArrayList<>();
//
//    @Override
//    public void getToken(Business.Empty request, StreamObserver<Business.TokenResponse> responseObserver) {
//        String token = tokenService.createToken();
//        System.out.println("Token" + token);
//
//        Business.TokenResponse tokenResponse = Business.TokenResponse.newBuilder()
//                .setToken(token)
//                        .build();
//
//        responseObserver.onNext(tokenResponse);
//        responseObserver.onCompleted();
//    }
//
//    @Override
//    public void testPostWithIdempotency(Business.NameRequest request, StreamObserver<Business.NameResponse> responseObserver) {
//        try {
//
//
//            names.add(request.getName());
//
//            Business.NameResponse nameResponse = Business.NameResponse.newBuilder()
//                    .setName(request.getName())
//                    .build();
//
//            responseObserver.onNext(nameResponse);
//            responseObserver.onCompleted();
//
//        } catch (InvalidTokenException ex) {
//            responseObserver.onError(ex);
//
//
////            Business.ErrorResponse errorResponse = Business.ErrorResponse.newBuilder()
////                    .setLocalDateTime(LocalDateTime.now().toString())
////                            .setMessage(ex.getMessage())
////                            .setDetails("")
////                            .build();
////
////            responseObserver.onError(errorResponse);
//
//        }catch (TokenNotFoundException ex){
//            responseObserver.onError(ex);
//        }
//    }
}
