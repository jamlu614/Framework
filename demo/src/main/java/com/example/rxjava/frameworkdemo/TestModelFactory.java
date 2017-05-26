package com.example.rxjava.frameworkdemo;

import com.jamlu.framework.base.Factory;

/**
 * Created by ljb on 2017/5/25.
 */

public class TestModelFactory implements Factory<TestModel> {
    @Override
    public TestModel create() {
        return new TestModelImpl();
    }
}
