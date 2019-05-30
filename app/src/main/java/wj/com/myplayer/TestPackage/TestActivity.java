package wj.com.myplayer.TestPackage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;

import wj.com.myplayer.Network.HttpHandler;
import wj.com.myplayer.Network.NetworkWrapper;
import wj.com.myplayer.R;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mTestBtn;
    /**
     * 123
     */
    private TextView mResultTv;
    /**
     * get
     */
    private Button mGet;
    /**
     * File
     */
    private Button mFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        mTestBtn = (Button) findViewById(R.id.test_btn);
        mTestBtn.setOnClickListener(this);
        mResultTv = (TextView) findViewById(R.id.result_tv);
        mGet = (Button) findViewById(R.id.get);
        mGet.setOnClickListener(this);
        mResultTv.setOnClickListener(this);
        mFile = (Button) findViewById(R.id.file);
        mFile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.test_btn:

                Logger.d("123");
                NetworkWrapper.face("gly001", new HttpHandler<String>() {
                    @Override
                    public void onSuccess(String data) {
                        mResultTv.setText(data);
                    }
                });
                break;
            case R.id.get:
                NetworkWrapper.getMsg("1", new HttpHandler<String>() {
                    @Override
                    public void onSuccess(String data) {
                        mResultTv.setText(data);
                    }
                });
                break;
            case R.id.result_tv:
                break;
            case R.id.file:
                File file = new File(getFilesDir(),"test.png");
                try {
                    file.createNewFile();
                    NetworkWrapper.filesUpload(file, new HttpHandler<String>() {
                        @Override
                        public void onSuccess(String data) {
                            mResultTv.setText(data);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
