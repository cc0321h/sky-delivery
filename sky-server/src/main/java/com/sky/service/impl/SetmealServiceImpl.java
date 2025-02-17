package com.sky.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;

@Service
public class SetmealServiceImpl implements SetmealService {
 
    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

        /**
         * 分页查询套餐信息
         * @param setmealPageQueryDTO
         * @return
         */
        public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
            PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
            Page<SetmealVO> pages = setmealMapper.pageQuery(setmealPageQueryDTO);
            return new PageResult(pages.getTotal(), pages.getResult());
        }
    
        /**
         * 保存套餐信息
         * @param setmealDTO
         */
        @Transactional
        public void saveWithDish(SetmealDTO setmealDTO) {
            Setmeal setmeal = new Setmeal();
            BeanUtils.copyProperties(setmealDTO, setmeal);
            setmealMapper.insert(setmeal);
            
            Long id = setmeal.getId();
            List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
            if(setmealDishes.size() > 0 && setmealDishes != null) {
                for(SetmealDish setmealDish : setmealDishes) {
                    setmealDish.setSetmealId(id);
                }
            }
            // 保存套餐与菜品关联关系
            setmealDishMapper.insertBatch(setmealDishes);
    }

        /**
         * 根据id启用和禁用套餐
         * @param status
         * @param id
         */
        public void startOrStop(Integer status, Long id) {
            Setmeal setmeal = new Setmeal();
            setmeal.setId(id);
            setmeal.setStatus(status);
            setmealMapper.update(setmeal);
        }

        /**
         * 获取套餐信息
         * @param id
         * @return
         */
        public SetmealVO getById(Long id) {
            Setmeal setmeal = setmealMapper.getById(id);
            List<SetmealDish> setmealDish = setmealDishMapper.getSetmealDishById(id);
            SetmealVO setmealVO = new SetmealVO();
            BeanUtils.copyProperties(setmeal, setmealVO);
            setmealVO.setSetmealDishes(setmealDish);
            return setmealVO;
        }

        /**
         * 更新套餐信息
         * @param setmealDTO
         */
        @Transactional
        public void update(SetmealDTO setmealDTO) {
            //修改setmeal表的数据
            Setmeal setmeal = new Setmeal();
            BeanUtils.copyProperties(setmealDTO, setmeal);
            setmealMapper.update(setmeal);
            //删除setmeal_dish表的数据
            setmealDishMapper.deleteBySetmealId(setmealDTO.getId());
            
            //添加setmeal_dish表的数据
            //获取关联菜品列表
            List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
            //添加套餐id
            Long setmealId = setmealDTO.getId();
            for (SetmealDish setmealDish : setmealDishes) {
                setmealDish.setSetmealId(setmealId);
            }
            setmealDishMapper.insertBatch(setmealDTO.getSetmealDishes());
        }

        /**
         * 批量删除套餐
         * @param ids
         */
        public void deleteBatch(List<Long> ids) {
            for (Long id : ids) {
                //是否为起售状态，起售状态不能删除
                if (setmealMapper.getById(id).getStatus().equals(StatusConstant.ENABLE)) {
                    throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
                }
                //删除setmeal表的数据
                setmealMapper.deleteById(id);
                //删除setmeal_dish表的数据
                setmealDishMapper.deleteBySetmealId(id);
                
            }
        }
    
    

}
