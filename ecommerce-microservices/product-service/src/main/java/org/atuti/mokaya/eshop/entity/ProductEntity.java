package org.atuti.mokaya.eshop.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ProductEntity {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 
 @ManyToOne
 @MapsId
 private CategoryEntity category;

 @Enumerated(EnumType.STRING)
 private ProductStatus status;

 private String title;
 private String description;
 private String imageUrl;
 private BigDecimal price;
}
