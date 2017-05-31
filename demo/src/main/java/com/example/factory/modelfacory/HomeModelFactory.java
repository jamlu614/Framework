package com.example.factory.modelfacory;

import com.jamlu.framework.base.Factory;
import com.example.model.HomeModel;
import com.example.model.impl.HomeModelImpl;

/**
 * Created by ljb on 2017/5/25.
 */

public class HomeModelFactory implements Factory<HomeModel> {
    @Override
    public HomeModel create() {
        return new HomeModelImpl();
    }
}
