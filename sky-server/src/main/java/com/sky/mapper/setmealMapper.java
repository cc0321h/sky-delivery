package com.sky.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;

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

    /**
     * 分页查询套餐信息
     * @param setmealPageQueryDTO
     * @return
     */
    public Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 保存套餐信息
     * @param setmeal
     */
    @AutoFill(value = OperationType.INSERT)
    public void insert(Setmeal setmeal);

    /**
     * 更新套餐信息
     * @param setmeal
     */
    @AutoFill(value = OperationType.UPDATE)
    public void update(Setmeal setmeal);

    /**
     * 查询套餐信息
     * @param id
     */
    @Select("select * from setmeal where id = #{id}")
    public Setmeal getById(Long id);

    /**
     * 根据id删除套餐
     * @param id
     */
    @Delete("delete from setmeal where id = #{id}")
    public void deleteById(Long id);
}
