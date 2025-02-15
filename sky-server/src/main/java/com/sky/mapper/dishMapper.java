package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param id
     * @return
     */
    @Select("select count(1) from dish where category_id = #{id}")
    public Integer countByCategoryId(Long id);

    Page<Dish> pageQuery(DishPageQueryDTO dishPageQueryDTO);

}
