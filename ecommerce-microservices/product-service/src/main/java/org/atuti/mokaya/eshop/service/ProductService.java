package org.atuti.mokaya.eshop.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import org.atuti.mokaya.eshop.entity.CategoryEntity;
import org.atuti.mokaya.eshop.entity.ProductEntity;
import org.atuti.mokaya.eshop.model.Product;
import org.atuti.mokaya.eshop.repository.CategoryRepository;
import org.atuti.mokaya.eshop.repository.ProductRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class ProductService {
    
    @Inject
    ProductRepository productRepository;

    @Inject
    CategoryRepository categoryRepository;

    public Multi<Product> findAll(){
        return productRepository.streamAll()
        .onItem().transform(ProductService::mapToDomain);
        
    }

    public Uni<Product> findById(Long id){
        return productRepository.findById(id)
        .onItem().ifNotNull().transform(ProductService::mapToDomain)
        .onItem().ifNull().failWith(() -> new WebApplicationException("Product with id: "+ id +" does not exist", 404));
    }

    public void create (Product product){
        CategoryEntity categoryEntity = categoryRepository.findById(product.getCategory().getId()).await().indefinitely();
        ProductEntity entity = mapToEntity(product);
        entity.setCategory(categoryEntity);

        Panache.withTransaction(() -> productRepository.persist(entity)).await().indefinitely();

    }

    public Uni<Boolean> delete (Long id){
        return Panache.withTransaction(() -> productRepository.deleteById(id));
    }

    private static Product mapToDomain(ProductEntity entity){
        return new ObjectMapper().convertValue(entity, Product.class);
    }

    private static ProductEntity mapToEntity(Product product){
        return new ObjectMapper().convertValue(product, ProductEntity.class);
    }
}
