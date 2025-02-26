package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.*;
import com.sky.entity.Category;
import com.sky.exception.BaseException;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private SetMealMapper  setMealMapper;
    @Autowired
    private DishMapper     dishMapper;


    /**
     * 新增分类
     * @param categoryDTO
     */
    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setStatus(StatusConstant.DISABLE);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.save(category);
    }

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Category category = new Category();
        BeanUtils.copyProperties(categoryPageQueryDTO, category);
        Page<Category> page =categoryMapper.page(category);
        long total = page.getTotal();
        List<Category> records = page.getResult();
        PageResult pageResult=new PageResult();
        pageResult.setTotal(total);
        pageResult.setRecords(records);
        return pageResult;
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        Category category = Category.builder()
                .id(id)
                .status(status)
                .build();
        categoryMapper.update(category);

    }

    @Override
    public void deleteById(Long id) {
        Integer count = dishMapper.countByCategoryId(id);
        if (count > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
         count = setMealMapper.countByCategoryId(id);
        if (count > 0) {
            throw new BaseException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        categoryMapper.deleteById(id);
    }

    @Override
    public List<Category> list(Integer type) {
        List<Category> list  =categoryMapper.getByType(type);
        return list;
    }

    @Override
    public void update(CategoryDTO categoryDTO) {

        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.update(category);
    }
}
