package com.hjl.testmodule;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;


import java.util.ArrayList;
import java.util.List;



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
    private ImageView mResultIv;
    private List<Music> musicList = new ArrayList<>();

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
        mResultIv = (ImageView) findViewById(R.id.result_iv);

        mResultTv.setText(musicList.size() + "albumId " + musicList.get(0).albumId);
        mResultIv.setImageBitmap(musicList.get(0).thumbBitmap);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.test_btn) {
            Logger.d("123");
        } else if (i == R.id.get) {

        } else if (i == R.id.result_tv) {

        } else if (i == R.id.file) {

        }
    }
}
