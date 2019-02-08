package com.rah.jersey.service;

import org.junit.Test;

import javax.ws.rs.core.Response;

public class JerseyClientServiceTest {

    @Test
    public void testGetResponse() {

        JerseyClientService service = new JerseyClientService();
        Response response = service.getResponse();
        System.out.println(response);
    }

}
