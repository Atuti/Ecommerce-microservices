package org.atuti.mokaya.eshop.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Long id;
    private Category category;
    private String status;
    private String title;
    private String descrption;
    private String imageUrl;
    private BigDecimal price;
}
