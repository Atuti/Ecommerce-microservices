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

import org.atuti.mokaya.eshop.model.Product;
import org.atuti.mokaya.eshop.service.ProductService;
import org.jboss.resteasy.reactive.RestPath;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService service;

    @GET
    public Multi<Product> findAll(){
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public Uni<Product> findById(@RestPath Long id){
        return service.findById(id);
    }

    @POST
    public Response create(Product product){
        service.create(product);
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