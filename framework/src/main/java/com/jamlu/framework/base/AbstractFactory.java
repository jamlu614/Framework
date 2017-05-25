package com.jamlu.framework.base;

/**
 * Created by ljb on 2017/5/25.
 * 工厂抽象类
 */

public abstract class AbstractFactory<T> {
    /**
     * 创建对象
     *
     * @return
     */
    public abstract T create();
}
