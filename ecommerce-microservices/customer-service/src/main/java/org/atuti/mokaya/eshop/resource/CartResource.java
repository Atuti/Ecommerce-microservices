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

import org.atuti.mokaya.eshop.model.Cart;
import org.atuti.mokaya.eshop.model.Customer;
import org.atuti.mokaya.eshop.service.CartService;
import org.jboss.resteasy.reactive.RestPath;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {
    
    @Inject
    CartService service;

    @GET
    public Multi<Cart> findAll(){
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public Uni<Cart> findById(@RestPath Long id){
        return service.findById(id);
    }

    @GET
    @Path("/customer/{customerId}")
    public Uni<Cart> findByCustomerId(@RestPath Long customerId){
        return service.findByCustomerId(customerId);
    }

    @POST
    public Response create(Customer customer){
        service.create(customer.getId());
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
