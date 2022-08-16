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

import org.atuti.mokaya.eshop.model.Category;
import org.atuti.mokaya.eshop.service.CategoryService;
import org.jboss.resteasy.reactive.RestPath;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {
    
    @Inject
    CategoryService service;

    @GET
    @Path("/{id}")
    public Uni<Category> findById(@RestPath Long id){
        return service.findById(id);
    }

    @GET
    public Multi<Category> findAll(){
        return service.findAll();
    }

    @POST
    public Uni<Category> create(Category category){
        return service.create(category);
    }

    @DELETE
    public Uni<Response> delete(Long id){
        return service.delete(id)
        .onItem().transform(deleted -> deleted
        ? Response.status(Response.Status.NO_CONTENT).build()
        : Response.status(Response.Status.NOT_FOUND).build());
    }
}
