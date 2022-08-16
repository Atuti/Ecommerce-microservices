package org.atuti.mokaya.eshop.repository;

import javax.enterprise.context.ApplicationScoped;

import org.atuti.mokaya.eshop.entity.ProductEntity;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<ProductEntity>{
    
}
