package com.cepheid.cloud.skel;

import java.net.URI;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.cepheid.cloud.skel.model.User;
import com.cepheid.cloud.skel.repository.DescriptionRepository;
import com.cepheid.cloud.skel.repository.ItemRepository;
import com.cepheid.cloud.skel.repository.UserRepository;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    protected ItemRepository itemRepository;

    @Autowired
    protected DescriptionRepository descriptionRepository;

    @Autowired
    protected UserRepository userRepository;

    @Before
    public void cleanDb(){
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Before
    public void createTestUser(){
        userRepository.save(new User("test_user", "pass"));
    }

    @PostConstruct
    public void postConstruct() {
        pathPrefix = "/app/api/1.0";
        mServerUri = "http://localhost:" + mPort + pathPrefix;
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("test_user", "pass");
        mClient = createClient();
        mClient.register(feature);
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
