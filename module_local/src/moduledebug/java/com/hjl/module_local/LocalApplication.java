package com.hjl.module_local;

import com.hjl.commonlib.base.BaseApplication;
import com.hjl.module_main.module.ILocalModuleAppImpl;

public class LocalApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();

        ILocalModuleAppImpl iLocalModuleApplication = new ILocalModuleAppImpl();
        iLocalModuleApplication.init(this);

    }


}
