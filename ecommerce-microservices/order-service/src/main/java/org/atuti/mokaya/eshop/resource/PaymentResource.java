package org.atuti.mokaya.eshop.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.atuti.mokaya.eshop.model.Payment;
import org.atuti.mokaya.eshop.service.PaymentService;
import org.jboss.resteasy.reactive.RestPath;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentResource {

    @Inject
    PaymentService service;
    
    @GET
    public Multi<Payment> findAll() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public Uni<Payment> findById(@RestPath Long id){
        return service.findById(id);
    }

    @POST
    public Uni<Payment> create(Payment payment){
        return service.create(payment);
    }

    @DELETE
    public Uni<Response> delete(Long id){
        return service.delete(id)
        .map(deleted -> deleted
        ? Response.status(Response.Status.NO_CONTENT).build()
        : Response.status(Response.Status.NOT_FOUND).build());
    }
}
