package com.sky.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.checkerframework.checker.units.qual.s;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestBody;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingService;

@Service
public class ShoppingServiceImpl implements ShoppingService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    /**
     * adding item in shoppingcart
     * @param shoppingCartDTO
     * @return
     */
    public void add(ShoppingCartDTO shoppingCartDTO) {
        //item exit in shopping_cart or not
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        
        if(list != null && list.size() > 0) {
            //exit
            ShoppingCart shoppingCartGood = list.get(0);
            shoppingCartGood.setNumber(shoppingCartGood.getNumber() + 1);
            shoppingCartMapper.update(shoppingCartGood);
        }else{
            //no exit
            //item is dish or setmeal,set number
            if(shoppingCartDTO.getDishId() != null){
                //dish
                Dish dish = dishMapper.getById(shoppingCartDTO.getDishId());
                shoppingCart.setAmount(dish.getPrice());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setName(dish.getName());
            }else{
                //setmeal
                Setmeal setmeal = setmealMapper.getById(shoppingCart.getSetmealId());
                shoppingCart.setAmount(setmeal.getPrice());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setName(setmeal.getName());
            }
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCart.setNumber(1);
            //insert
            shoppingCartMapper.insert(shoppingCart);
        }
        
    }
    
    /**
     * get shopping_cart information
     * @return
     */
    public List<ShoppingCart> list() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(BaseContext.getCurrentId());
        return shoppingCartMapper.list(shoppingCart);
    }

    /**
     * sub shopping_cart
     * @param shoppingCartDTO
     */
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
       
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        ShoppingCart shoppingCartGood = list.get(0);
        shoppingCartGood.setNumber(shoppingCartGood.getNumber() - 1);

        if(shoppingCartGood.getNumber() > 0) {
            shoppingCartMapper.update(shoppingCartGood);
        }else{
                shoppingCartMapper.delete(shoppingCartGood);
        }
    }

    /**
     * clean shopping_cart
     * @return
     */
    public void clean() {
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.deleteByUserId(userId);
    }
}
