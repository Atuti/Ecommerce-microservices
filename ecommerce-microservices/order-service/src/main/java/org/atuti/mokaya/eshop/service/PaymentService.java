package org.atuti.mokaya.eshop.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import org.atuti.mokaya.eshop.entity.PaymentEntity;
import org.atuti.mokaya.eshop.model.Payment;
import org.atuti.mokaya.eshop.repository.PaymentRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class PaymentService {
    
    @Inject
    PaymentRepository paymentRepository;

    public Multi<Payment> findAll(){
        return paymentRepository.streamAll()
        .onItem().transform(PaymentService::mapToDomain);
    }

    public Uni<Payment> findById(Long id){
        return paymentRepository.findById(id)
        .onItem().ifNotNull().transform(PaymentService::mapToDomain)
        .onItem().ifNull().failWith(() -> new WebApplicationException("Payment with the id: "+ id +" does not exist", 404));
    }

    public Uni<Payment> create(Payment payment){
        return Panache.withTransaction(() -> paymentRepository.persistAndFlush(mapToEntity(payment)))
        .onItem().transform(PaymentService::mapToDomain);
    }

    public Uni<Boolean> delete(Long id){
        return Panache.withTransaction(() -> paymentRepository.deleteById(id));
    }

    private static Payment mapToDomain(PaymentEntity entity){
        return new ObjectMapper().convertValue(entity, Payment.class);
    }

    private static PaymentEntity mapToEntity(Payment payment){
        return new ObjectMapper().convertValue(payment, PaymentEntity.class);
    }
}
