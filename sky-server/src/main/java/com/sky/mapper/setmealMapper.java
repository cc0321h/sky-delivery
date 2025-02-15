package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐数量
     * @param id
     * @return
     */
    @Select("select count(*) from setmeal where category_id = #{id}")
    public Integer countByCategoryId(Long id);

    /**
     * 根据菜品id查询套餐数量
     * @param id
     * @return
     */
    @Select("select count(*) from setmeal_dish where dish_id = #{id}")
    public Integer countByDishId(Long id);
}
