package com.sky.service;

import com.sky.dto.*;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {

    /**
     * 新增分类
     * @param categoryDTO
     */
    void save(CategoryDTO categoryDTO);


    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 启用和禁用分类
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Long id);

    List<Category> list(Integer type);

    void update(CategoryDTO categoryDTO);
}
