package org.atuti.mokaya.eshop.repository;

import javax.enterprise.context.ApplicationScoped;

import org.atuti.mokaya.eshop.entity.CustomerEntity;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<CustomerEntity>{
    
}
