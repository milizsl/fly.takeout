package com.sky.aspect;


import com.sky.annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MemberSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 自定义切面类 用于自动填充公共字段
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill) ")
    public void autoFillPointCut() {}

    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始进行公共字段自动填充");
        // 获取当前被拦截的方法上的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();// 获取当前被拦截的方法签名
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);// 获取当前被拦截的方法上的注解
        OperationType operationType = autoFill.value();// 获取数据库操作类型
        Object[] args = joinPoint.getArgs();// 获取当前被拦截的方法的参数
        if (args.length == 0 || args== null) {
            return;
        }
        // 获取当前被拦截的方法的参数
        Object entity = args[0];
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        //根据不同操作类型，通过反射为对应的属性进行赋值
        if (operationType == OperationType.INSERT) {
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);
                Method setCreateTime = entity.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod("setCreateUser", Long.class);
                setCreateUser.invoke(entity,now);
                setCreateUser.invoke(entity,currentId);
                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,currentId);
            } catch (Exception e) {
                e.printStackTrace();}
        }else if(operationType == OperationType.UPDATE){
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);
                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

}
