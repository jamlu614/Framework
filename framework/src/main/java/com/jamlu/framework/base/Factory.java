package com.jamlu.framework.base;

/**
 * Created by ljb on 2017/5/25.
 * 工厂接口
 */

public interface Factory<V>{
    /**
     * 创建对象
     * @return
     */
    V create();
}
