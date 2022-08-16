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

import org.atuti.mokaya.eshop.model.OrderItem;
import org.atuti.mokaya.eshop.service.OrderItemService;
import org.jboss.resteasy.reactive.RestPath;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/order_item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderItemResource {
    
    @Inject
    OrderItemService service;

    @GET
    public Multi<OrderItem> findAll(){
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public Uni<OrderItem> findById(@RestPath Long id){
        return service.findById(id);
    }

    @POST
    public Uni<OrderItem> create(OrderItem orderItem){
        return service.create(orderItem);
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
