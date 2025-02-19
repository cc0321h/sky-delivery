package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import com.sky.entity.ShoppingCart;

@Mapper
public interface ShoppingCartMapper {

    /**
     * get item in shopping_cart by id
     * @param shoppingCartDTO
     * @return
     */
    public List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * update information
     * @param shoppingCart
     */
    public void update(ShoppingCart shoppingCart);

    /**
     * insert
     * @param shoppingCart
     */
    public void insert(ShoppingCart shoppingCart);

    /**
     * delete
     * @param shoppingCart
     */
    public void delete(ShoppingCart shoppingCart);

    /**
     * clean shopping_cart
     * @return
     */
    @Delete("delete from shopping_cart where user_id = #{userId}")
    public void deleteByUserId(Long userId);
}
