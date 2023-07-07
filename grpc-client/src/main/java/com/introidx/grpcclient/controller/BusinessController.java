package com.introidx.grpcclient.controller;

import com.google.protobuf.Descriptors;
import com.introidx.grpcclient.service.BusinessClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    BusinessClientService businessClientService;

    @GetMapping("/get")
    public Map<Descriptors.FieldDescriptor, Object> getToken(){
        return businessClientService.getToken();
    }

    @PostMapping("/post")
    public Map<Descriptors.FieldDescriptor , Object> testPostMethod(@RequestBody String name) throws InterruptedException {
        return businessClientService.getName(name);
    }
}
