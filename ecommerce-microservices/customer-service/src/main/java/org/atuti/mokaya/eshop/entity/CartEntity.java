package org.atuti.mokaya.eshop.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "carts")
@Getter
@Setter
@Accessors(chain = true)
public class CartEntity {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private CartStatus cartStatus;

@OneToOne
@MapsId
private CustomerEntity customer;
    
}
