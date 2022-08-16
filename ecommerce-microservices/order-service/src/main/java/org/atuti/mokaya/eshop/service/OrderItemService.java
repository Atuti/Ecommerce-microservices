package org.atuti.mokaya.eshop.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import org.atuti.mokaya.eshop.entity.OrderItemEntity;
import org.atuti.mokaya.eshop.model.OrderItem;
import org.atuti.mokaya.eshop.repository.OrderItemRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class OrderItemService {
    
    @Inject
    OrderItemRepository orderItemRepository;

    public Multi<OrderItem> findAll(){
        return orderItemRepository.streamAll()
        .onItem().transform(OrderItemService::mapToDomain);
    }

    public Uni<OrderItem> findById(Long id){
        return orderItemRepository.findById(id)
        .onItem().ifNotNull().transform(OrderItemService::mapToDomain)
        .onItem().ifNull().failWith(() -> new WebApplicationException("Order item with id: "+ id +" does not exist", 404));
    }

    public Uni<OrderItem> create(OrderItem orderItem){
        return Panache.withTransaction(() -> orderItemRepository.persistAndFlush(mapToEntity(orderItem)))
        .onItem().transform(OrderItemService::mapToDomain);
    }

    public Uni<Boolean> delete(Long id){
        return orderItemRepository.deleteById(id);
    }

    private static OrderItem mapToDomain(OrderItemEntity entity){
        return new ObjectMapper().convertValue(entity, OrderItem.class);
    }

    private static OrderItemEntity mapToEntity(OrderItem orderItem){
        return new ObjectMapper().convertValue(orderItem, OrderItemEntity.class);
    }
}
