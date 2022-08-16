package org.atuti.mokaya.eshop.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.atuti.mokaya.eshop.model.Order;
import org.atuti.mokaya.eshop.service.AddressService;
import org.atuti.mokaya.eshop.service.OrderItemService;
import org.atuti.mokaya.eshop.service.OrderService;
import org.atuti.mokaya.eshop.service.PaymentService;
import org.jboss.resteasy.reactive.RestPath;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService service;

    @Inject
    OrderItemService orderItemService;

    @Inject
    PaymentService paymentService;

    @Inject
    AddressService addressService;

    @GET
    public Multi<Order> findAll() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public Uni<Order> findById(@RestPath Long id){
        return service.findById(id);
    }

    public Response create (Order order){
        service.create(order);
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(@RestPath Long id){
        return service.delete(id)
        .map(deleted -> deleted
        ? Response.status(Response.Status.NO_CONTENT).build()
        : Response.status(Response.Status.NOT_FOUND).build());
    }
}