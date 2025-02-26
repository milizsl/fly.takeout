package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface SetMealMapper {

    @Select("select count(1) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);
}
