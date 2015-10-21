package pl.lodz.p.ftims.pai.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import pl.lodz.p.ftims.pai.controllers.DeliveryManController;

import javax.ws.rs.ApplicationPath;

/**
 * @author alisowsk
 */
@Component
@ApplicationPath("/")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(DeliveryManController.class);
    }
}
