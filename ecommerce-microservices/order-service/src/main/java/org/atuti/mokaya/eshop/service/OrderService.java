package org.atuti.mokaya.eshop.service;

import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import org.atuti.mokaya.eshop.entity.AddressEntity;
import org.atuti.mokaya.eshop.entity.OrderEntity;
import org.atuti.mokaya.eshop.entity.OrderItemEntity;
import org.atuti.mokaya.eshop.entity.PaymentEntity;
import org.atuti.mokaya.eshop.model.Order;
import org.atuti.mokaya.eshop.repository.AddressRepository;
import org.atuti.mokaya.eshop.repository.OrderItemRepository;
import org.atuti.mokaya.eshop.repository.OrderRepository;
import org.atuti.mokaya.eshop.repository.PaymentRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class OrderService {
    
    @Inject
    OrderRepository orderRepository;

    @Inject 
    AddressRepository addressRepository;

    @Inject
    PaymentRepository paymentRepository;

    @Inject
    OrderItemRepository orderItemRepository;

    public Multi<Order> findAll(){
        return orderRepository.streamAll()
        .onItem().transform(OrderService::mapToDomain);
    }

    public Uni<Order> findById(Long id){
        return orderRepository.findById(id)
        .onItem().ifNotNull().transform(OrderService::mapToDomain)
        .onItem().ifNull().failWith(() -> new WebApplicationException("An order with the id: "+ id +" does not exist", 404));
    }

    public void create (Order order){
        AddressEntity address = addressRepository.findById(order.getShipmentAddress().getId()).await().indefinitely();
        PaymentEntity payment = paymentRepository.findById(order.getPayment().getId()).await().indefinitely();
        Set<OrderItemEntity> orderItems = order.getOrderItems().stream()
        .map((o) -> {
            return orderItemRepository.findById(o.getId()).await().indefinitely();
        }).collect(Collectors.toSet());

        OrderEntity entity = mapToEntity(order);
        entity.setShipmentAddress(address);
        entity.setOrderItems(orderItems);
        entity.setPayment(payment); 

        Panache.withTransaction(() -> orderRepository.persist(entity)).await().indefinitely();
    }

    public Uni<Boolean> delete(Long id){
        return orderRepository.deleteById(id);
    }

    private static Order mapToDomain(OrderEntity entity){
        return new ObjectMapper().convertValue(entity, Order.class);
    }

    private static OrderEntity mapToEntity(Order order){
        return new ObjectMapper().convertValue(order, OrderEntity.class);
    }
}
