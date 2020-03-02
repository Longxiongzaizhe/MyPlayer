package com.hjl.testmodule.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hjl.module_main.net.NetworkWrapper;
import com.hjl.module_main.utils.FileUtils;
import com.hjl.testmodule.R;

import java.io.File;
import java.io.FileNotFoundException;

public class OkhttpTestActivity extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_test2);

        btn = findViewById(R.id.btn1);
        btn.setOnClickListener(v -> {

            File file = new File(FileUtils.getInternalStorageCachePath(),"testfile.png");
            try {
                FileUtils.saveDrawableInFile(getDrawable(R.drawable.ic_launcher),file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            NetWorkWrapper.filesUpload(file, new HttpHandler<String>() {
                @Override
                public void onSuccess(ServerTip serverTip, String s) {
                    Log.d("TAG", "onSuccess: " + s);
                }

                @Override
                public void onFailure(ServerTip serverTip) {
                    super.onFailure(serverTip);
                    Log.d("TAG", "onFailure: " + serverTip.message);
                }
            });
        });
    }
}
