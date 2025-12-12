package com.bx.utils;

import cn.hutool.core.collection.CollUtil;
import com.bx.exception.BsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author lili
 * @version 1.0
 * @date 2025/12/12 9:28
 * @description 修改时关联数据校验工具类
 */
public class RelationUtil {
    /**
     * 校验关联对象的ID
     * @param newList 前端传入的列表
     * @param existList 数据库中的列表
     * @param idGetter ID获取方法
     * @param entityName 实体名称（用于异常提示）
     */
    public static <T> void validate(List<T> newList, List<T> existList, Function<T, Long> idGetter, String entityName) {
        //前端没有传入关联对象，不用校验直接返回
        if (CollUtil.isEmpty(newList)) {
            return;
        }

        //获取关联对象不为空的ID
        Set<Long> newIdSet = newList.stream().map(idGetter).filter(Objects::nonNull).collect(Collectors.toSet());

        //关联对象的ID为空说明是新增，不用校验直接返回
        if (CollUtil.isEmpty(newIdSet)) {
            return;
        }

        //数据库中无关联对象，前端有关联对象ID，说明携带了别人的数据，修改失败
        if (CollUtil.isEmpty(existList)) {
            throw new BsException(entityName + "校验异常，修改失败！");
        }

        //数据库中和前端传入的都有关联对象ID，则统计校验前端是否有传入非数据库中的关联对象ID
        Set<Long> existIdSet = existList.stream().map(idGetter).collect(Collectors.toSet());

        //前端有传入非数据库中的关联对象ID，修改失败
        if (!existIdSet.containsAll(newIdSet)) {
            throw new BsException(entityName + "校验异常，修改失败！");
        }
    }

    /**
     * 批量校验关联对象的ID
     */
    public static Builder check() {
        return new Builder();
    }

    // 任务构建器静态内部类，要使用该静态内部类的方法，需要在外部类中创建该内部类对象。
    // 外部类方法check()中创建了该内部类对象，然后用内部类对象调用方法relation()
    // 在relation()方法中又通过构造器创建了该内部类自己的静态内部类对象，并调用自己的内部类对象调用方法execute()
    // 在execute()方法中调用外部类的静态方法RelationUtil.validate()。

    //同时还运用了生成器模式，Builder是具体的建造者负责建造产品，tasks是产品，relation()创建产品的组件，validate()使用或返回产品。
    public static class Builder {
        private final List<Task<?>> tasks = new ArrayList<>();

        /**
         * 创建校验任务并添加到任务列表中
         * @param newList 前端传入的列表
         * @param existList 数据库中的列表
         * @param idGetter ID获取方法
         * @param entityName 实体名称（用于异常提示）
         */
        public <T> Builder relation(List<T> newList, List<T> existList, Function<T, Long> idGetter, String entityName) {
            //创建校验任务并添加到任务列表中
            tasks.add(new Task<>(newList, existList, idGetter, entityName));
            return this;
        }

        //批量执行任务列表中的校验
        public void validate() {
            for (Task<?> task : tasks) {
                task.execute();
            }
        }

        //任务静态内部类
        private static class Task<T> {
            private final List<T> newList;
            private final List<T> existList;
            private final Function<T, Long> idGetter;
            private final String entityName;

            /**
             * 构造器
             * @param newList 前端传入的列表
             * @param existList 数据库中的列表
             * @param idGetter ID获取方法
             * @param entityName 实体名称（用于异常提示）
             */
            public Task(List<T> newList, List<T> existList, Function<T, Long> idGetter, String entityName) {
                this.newList = newList;
                this.existList = existList;
                this.idGetter = idGetter;
                this.entityName = entityName;
            }

            //执行任务
            public void execute() {
                RelationUtil.validate(newList, existList, idGetter, entityName);
            }
        }
    }
}

