package com.example.rxjava.frameworkdemo;

import com.jamlu.framework.base.AbstractFactory;

/**
 * Created by ljb on 2017/5/25.
 */

public class TestModelFactory extends AbstractFactory<TestModel> {
    @Override
    public TestModel create() {
        return new TestModelImpl();
    }
}
