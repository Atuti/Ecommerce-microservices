package org.atuti.mokaya.eshop.entity;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Entity
@Table(name = "orders")
@Getter
@Setter
@Accessors(chain = true)
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Long cartId;

    @OneToOne
    // @MapsId
    private AddressEntity shipmentAddress;

    @OneToOne
    // @MapsId
    private PaymentEntity payment;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name =  "order_id")
    private Set<OrderItemEntity> orderItems;
    private BigDecimal totalPrice;
}
