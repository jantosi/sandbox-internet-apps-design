package pl.lodz.p.ftims.pai.web.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.lodz.p.ftims.pai.repository.*;

@Endpoint
public class SynchronizationUsersProcessor {
    private static final String NAMESPACE_URI = "http://osemka.com";

    @Autowired
    private UserRepository userRepository;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "synchronizationUsersRequest")
    @ResponsePayload
    public SynchronizationUsersResponse synchronize(@RequestPayload SynchronizationUsersRequest request) {
        SynchronizationUsersResponse response = new SynchronizationUsersResponse();

        response.getUser().addAll(userRepository.findAll());

        return response;
    }
}
