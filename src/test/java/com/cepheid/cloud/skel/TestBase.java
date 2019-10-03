package com.cepheid.cloud.skel;

import java.net.URI;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {
        SkelApplication.class})
public class TestBase {
    private String mServerUri;

    protected Client mClient;

    protected String pathPrefix;

    @Value("${server.port}")
    protected int mPort;

    @PostConstruct
    public void postConstruct() {
        pathPrefix = "/app/api/1.0";
        mServerUri = "http://localhost:" + mPort + pathPrefix;
        mClient = createClient();
    }

    public Builder getBuilder(String path, Object... values) {
        URI uri = UriBuilder.fromUri(mServerUri + path).build(values);

        WebTarget webTarget = mClient.target(uri);
        webTarget = webTarget.register(MultiPartFeature.class);

        return webTarget.request(MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_OCTET_STREAM_TYPE);
    }

    protected Client createClient() {
        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        return clientBuilder.build();
    }
}
