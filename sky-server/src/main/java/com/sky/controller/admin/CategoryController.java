package com.sky.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    /**
     * 添加分类
     * @param categoryDTO
     */
    @PostMapping
    @ApiOperation("添加分类")
    public Result add(@RequestBody CategoryDTO categoryDTO) {
        log.info("添加分类：{}", categoryDTO);

        categoryService.add(categoryDTO);

        return Result.success();
    }

    /**
     * 分页查询分类信息
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询菜品")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分页查询：{}", categoryPageQueryDTO);

        PageResult pageQuery = categoryService.pageQuery(categoryPageQueryDTO);

        return Result.success(pageQuery);
    }

    /**
     * 修改分类信息
     * @param categoryDTO
     */
    @PutMapping
    @ApiOperation("修改分类信息")
    public Result update(@RequestBody CategoryDTO categorydDto) {
        log.info("修改分类信息：{}", categorydDto);

        categoryService.update(categorydDto);

        return Result.success();
    }

    /**
     * 根据id启用和禁用分类
     * @param id
     * @param status
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用和禁用分类")
    public Result startOrStop (Long id, @PathVariable Integer status) {
        log.info("启用和禁用分类：id={}, status={}", id, status);

        categoryService.startOrStop(id, status);

        return Result.success();
    }

    /**
     * 删除分类
     * @param id
     */
    @DeleteMapping
    @ApiOperation("删除分类")
    public Result delete(Long id) {
        log.info("删除分类：id={}", id);

        categoryService.delete(id);

        return Result.success();
    }

    /**
     * 根据类型查询分类
     * @param type
     */
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> list(Integer type) {
        log.info("根据类型查询分类：type={}", type);

        return Result.success(categoryService.list(type));
    }
}
