package wj.com.myplayer.Config;

import android.app.Application;

public class MainApplication extends Application {

    private static MainApplication sInst;
    private static final String TAG = "MainApplication";

    public static MainApplication get() {
        return sInst;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInst = this;
    }
}
