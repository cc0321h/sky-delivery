package com.sky.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "shopping cart")
@RequestMapping("/user/shoppingCart/")
public class ShoppingCartController {

    @Autowired
    private ShoppingService shoppingService;

    /**
     * adding item in shoppingcart
     * @param shoppingCartDTO
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("add item in shoppingcart")
    public Result addShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        shoppingService.add(shoppingCartDTO);
        return Result.success();
    } 

    /**
     * get shopping_cart
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("get shopping_cart information")
    public Result<List<ShoppingCart>> list() {
        return Result.success(shoppingService.list());
    }

    /**
     * sub shopping_cart
     * @param shoppingCartDTO
     * @return
     */
    @PostMapping("/sub")
    @ApiOperation("sub shopping_cart")
    public Result subShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        shoppingService.subShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    /**
     * clean shopping_cart
     * @return
     */
    @DeleteMapping("/clean")
    @ApiOperation("clean shopping_cart")
    public Result cleanShoppingCart() {
        shoppingService.clean();
        return Result.success();
    }
}
