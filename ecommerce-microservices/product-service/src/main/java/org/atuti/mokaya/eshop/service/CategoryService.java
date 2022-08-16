package org.atuti.mokaya.eshop.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import org.atuti.mokaya.eshop.entity.CategoryEntity;
import org.atuti.mokaya.eshop.model.Category;
import org.atuti.mokaya.eshop.repository.CategoryRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class CategoryService {
    
    @Inject
    CategoryRepository categoryRepository;

    public Multi<Category> findAll(){
        return categoryRepository.streamAll()
        .onItem().transform(CategoryService::mapToDomain);
    }

    public Uni<Category> findById(Long id){
        return categoryRepository.findById(id)
        .onItem().ifNotNull().transform(CategoryService::mapToDomain)
        .onItem().ifNull().failWith(() -> new WebApplicationException("Category with the id:"+ id +" does not exist", 404));
    }

    public Uni<Category> create(Category category){
        return Panache.withTransaction(() -> categoryRepository.persistAndFlush(mapToEntity(category)))
        .onItem().transform(CategoryService::mapToDomain);
    }

    public Uni<Boolean> delete (Long id){
        return Panache.withTransaction(() -> categoryRepository.deleteById(id));
    }

    private static Category mapToDomain(CategoryEntity entity){
        return new ObjectMapper().convertValue(entity, Category.class);
    }

    private static CategoryEntity mapToEntity(Category category){
        return new ObjectMapper().convertValue(category, CategoryEntity.class);
    }
}
