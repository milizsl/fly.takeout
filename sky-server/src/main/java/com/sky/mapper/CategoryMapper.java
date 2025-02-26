package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface CategoryMapper {

    @AutoFill(value = OperationType.INSERT)
    void save(Category category);

    Page<Category> page(Category category);

    @AutoFill(value = OperationType.UPDATE)
    void update(Category category);


    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);


    List<Category> getByType(Integer type);
}
