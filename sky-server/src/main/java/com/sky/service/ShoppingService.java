package com.sky.service;

import java.util.List;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

public interface ShoppingService {

    /**
     * adding item in shoppingcart
     * @param shoppingCartDTO
     * @return
     */
    public void add(ShoppingCartDTO shoppingCartDTO);

    /**
     * get shopping_cart information
     */
    public List<ShoppingCart> list();

    /**
     * sub shopping_cart
     * @param shoppingCartDTO
     */
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO);
    
    /**
     * clean shopping_cart
     * @return
     */
    public void clean();
}
