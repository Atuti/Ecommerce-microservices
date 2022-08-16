package org.atuti.mokaya.eshop.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import org.atuti.mokaya.eshop.entity.CustomerEntity;
import org.atuti.mokaya.eshop.model.Customer;
import org.atuti.mokaya.eshop.repository.CustomerRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class CustomerService {
    
    @Inject
    CustomerRepository repository;

    public Multi<Customer> findAll(){
        return repository.streamAll()
        .onItem().transform(CustomerService::mapToDomain);
    }

    public Uni<Customer> findById(Long id){
        return repository.findById(id)
        .onItem().ifNotNull().transform(CustomerService::mapToDomain)
        .onItem().ifNotNull().failWith(() -> new WebApplicationException("A customer with the id: "+ id + "does not exist", 404));
    }

    public Uni<Customer> create(Customer customer){
       return Panache.withTransaction(() -> repository.persist(mapToEntity(customer)))
       .onItem()
       .transform(CustomerService::mapToDomain);
    }

    public Uni<Boolean> delete(Long id){
        return Panache.withTransaction(() -> repository.deleteById(id));
    }

    public Uni<Customer> update(Customer customer){
        return Panache.withTransaction(() -> findById(customer.getId())
        .onItem().transform(entity -> {
            entity.setFirstName(customer.getFirstName());
            entity.setLastName(customer.getLastName());
            entity.setEmail(customer.getEmail());
            entity.setTelephone(customer.getTelephone());
            return entity;
        }));
    }

    private static Customer mapToDomain (CustomerEntity entity){
        return new ObjectMapper().convertValue(entity, Customer.class);
    }

    private static CustomerEntity mapToEntity(Customer customer){
        return new ObjectMapper().convertValue(customer, CustomerEntity.class);
    }
}
