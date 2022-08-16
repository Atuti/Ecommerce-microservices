package org.atuti.mokaya.eshop.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import org.atuti.mokaya.eshop.entity.CartEntity;
import org.atuti.mokaya.eshop.entity.CartStatus;
import org.atuti.mokaya.eshop.entity.CustomerEntity;
import org.atuti.mokaya.eshop.model.Cart;
import org.atuti.mokaya.eshop.repository.CartRepository;
import org.atuti.mokaya.eshop.repository.CustomerRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class CartService {
    
    @Inject
    CartRepository repository;

    @Inject 
    CustomerRepository customerRepository;

    public Multi<Cart> findAll(){
        return repository.streamAll()
        .map(CartService::mapToDomain);
    }

    public Uni<Cart> findById(Long id){
        return repository.findById(id)
        .onItem().ifNotNull().transform(CartService::mapToDomain)
        .onItem().ifNull().failWith(() -> new WebApplicationException("Cart with id: "+ id + "does not exist", 404));
    }

    public Uni<Cart> findByCustomerId(Long customerId){
        return repository.find("customer_id", customerId).firstResult()
        .onItem().ifNotNull().transform(CartService::mapToDomain)
        .onItem().ifNull().failWith(() -> new WebApplicationException("Cart with customer id: "+ customerId + "does not exist", 404));
    }

    public void create(Long customerId){
        CustomerEntity customerEntity = customerRepository.findById(customerId).await().indefinitely();
        CartEntity entity = new CartEntity()
                            .setCustomer(customerEntity)
                            .setCartStatus(CartStatus.NEW);

        Panache.withTransaction(
            () -> repository.persist(entity)).await().indefinitely();
        
    }

    public Uni<Boolean> delete(Long id){
        return Panache.withTransaction(() -> repository.deleteById(id));
    }


    private static Cart mapToDomain(CartEntity entity){
        return new ObjectMapper().convertValue(entity, Cart.class);
    }

//     private static CartEntity mapToEntity(Cart cart){
//         return new ObjectMapper().convertValue(cart, CartEntity.class);
//     }
}
