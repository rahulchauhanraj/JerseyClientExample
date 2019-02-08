package com.rah.jersey.service;

import org.apache.commons.collections4.MapUtils;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class JerseyClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JerseyClientService.class);

    public Response getResponse() {

        LOGGER.debug("calling service to get response.");

        String serviceUrl = "http://www.google.com";
        String servicePath = "search";
        String serviceMediaType = "application/json";

        Map<String, Object> queryParams = getQueryParams();
        Client client = getClient();
        Response response = getResponse(client, queryParams, serviceUrl, servicePath, serviceMediaType);

        if (response == null ) {
            LOGGER.error("URL is invalid or service not found, got null response");
            return null;
        }

        if (response.getStatus() != 200) {
            LOGGER.error("URL is invalid or not found, got error code {}.", response.getStatus());
            return null;
        }

        return response;
    }

    private static Map<String, Object> getQueryParams() {
        Map<String, Object> queryParams = new HashMap<>();

        queryParams.put("test", "test");

        return queryParams;
    }

    private static Response getResponse(Client client, Map<String, Object> queryParams, String serviceUrl, String servicePath, String serviceMediaType) {
        WebTarget webTarget = client.target(serviceUrl).path(servicePath);

        if (MapUtils.isNotEmpty(queryParams)) {

            for(Map.Entry<String, Object> paramEntry : queryParams.entrySet()) {
                webTarget = webTarget.queryParam(paramEntry.getKey(), paramEntry.getValue());
            }
        }

        Invocation.Builder invocationBuilder =  webTarget.request(serviceMediaType);
        return invocationBuilder.get();
    }

    private static Client getClient() {
        ClientConfig clientConfig = new ClientConfig();

        String userName = "test";
        String secret = "test";

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(userName, secret);
        clientConfig.register( feature) ;

        return ClientBuilder.newClient( clientConfig );
    }
}
