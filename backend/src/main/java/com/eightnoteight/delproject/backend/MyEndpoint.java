package com.eightnoteight.delproject.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;


@Api(name = "snipapi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.delproject.eightnoteight.com",
                ownerName = "backend.delproject.eightnoteight.com",
                packagePath = ""))
public class MyEndpoint {
    @ApiMethod(name="sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setMyData("Hi " + name);
        return response;
    }
}
