package org.atuti.mokaya.eshop.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.atuti.mokaya.eshop.model.Customer;
import org.atuti.mokaya.eshop.service.CustomerService;
import org.jboss.resteasy.reactive.RestPath;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    CustomerService service;

    @GET
    public Multi<Customer> findAll(){
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public Uni<Customer> findById(@RestPath Long id){
        return service.findById(id);
    }

    @POST
    public Uni<Customer> create(Customer customer){
        return service.create(customer);
    }

    @PUT
    public Uni<Customer> update(Customer customer){
        return service.update(customer);
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