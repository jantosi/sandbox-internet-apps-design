package pl.lodz.p.ftims.pai.controllers;

import org.springframework.stereotype.Component;
import pl.lodz.p.ftims.pai.model.DeliveryMan;
import pl.lodz.p.ftims.pai.repository.DeliveryManRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author alisowsk
 */
@Component
@Path("/deliveryman")
public class DeliveryManController {

    @Inject
    private DeliveryManRepository deliveryManRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        return Response.ok(deliveryManRepository.findAll()).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DeliveryMan findOne(@PathParam("id") Long id) {
        return deliveryManRepository.findOne(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(DeliveryMan deliveryMan) {
        deliveryManRepository.save(deliveryMan);
        return Response.status(201).entity("Success").build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id) {
        deliveryManRepository.delete(id);
        return Response.accepted().build();
    }

}
