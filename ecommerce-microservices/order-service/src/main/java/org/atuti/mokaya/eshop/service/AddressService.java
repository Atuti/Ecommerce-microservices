package org.atuti.mokaya.eshop.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import org.atuti.mokaya.eshop.entity.AddressEntity;
import org.atuti.mokaya.eshop.model.Address;
import org.atuti.mokaya.eshop.repository.AddressRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class AddressService {
    
    @Inject
    AddressRepository addressRepository;

    public Multi<Address> findAll(){
        return addressRepository.streamAll()
        .onItem().transform(AddressService::mapToDomain);
    }

    public Uni<Address> findById(Long id){
        return addressRepository.findById(id)
        .onItem().ifNotNull().transform(AddressService::mapToDomain)
        .onItem().ifNull().failWith(() -> new WebApplicationException("An address with the id: "+ id +" does not exist", 404));
    }

    public Uni<Address> create (Address address){
        return addressRepository.persistAndFlush(mapToEntity(address))
        .onItem().transform(AddressService::mapToDomain);
    }

    public Uni<Boolean> delete(Long id){
        return addressRepository.deleteById(id);
    }

    private static Address mapToDomain(AddressEntity entity){
        return new ObjectMapper().convertValue(entity, Address.class);
    }

    private static AddressEntity mapToEntity(Address address){
        return new ObjectMapper().convertValue(address, AddressEntity.class);
    }
}
