package com.imooc.sell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 购物车
 */
@Data
@AllArgsConstructor
public class CartDTO {

    private String productId;

    private Integer productQuantity;
}
