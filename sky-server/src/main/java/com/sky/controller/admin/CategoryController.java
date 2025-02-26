package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 分类管理
 */
@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类管理")
public class CategoryController {

     @Autowired
     private CategoryService categoryService;



     @ApiOperation("新增分类")
     @PostMapping()
     public Result save(@RequestBody CategoryDTO categoryDTO){
        log.info("新增分类：{}",categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
     }


     @ApiOperation("分页查询")
     @GetMapping("/page")
     public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
         log.info("分页查询：{}",categoryPageQueryDTO);
         PageResult pageResult=categoryService.pageQuery(categoryPageQueryDTO);
         return Result.success(pageResult);
     }

     @ApiOperation("启用禁用分类")
     @PostMapping("/status/{status}")
     public Result startOrStop(@PathVariable Integer status,Long id){
         log.info("启用禁用分类：{},{}",status,id);
         categoryService.startOrStop(status,id);
         return Result.success();
     }

     @ApiOperation("删除分类")
     @DeleteMapping()
     public Result deleteById(Long id){
         log.info("删除分类：{}",id);
         categoryService.deleteById(id);
         return Result.success();
     }

     @ApiOperation("修改分类")
     @PutMapping()
     public Result update(@RequestBody CategoryDTO categoryDTO){
         log.info("修改分类：{}",categoryDTO);
         categoryService.update(categoryDTO);
         return Result.success();
     }

     @ApiOperation("根据类型查询分类")
     @GetMapping("/list")
     public Result<List<Category>> list(Integer type){
         log.info("根据类型查询分类：{}",type);
         List<Category> categoryList=categoryService.list(type);
         return Result.success(categoryList);
     }



}
