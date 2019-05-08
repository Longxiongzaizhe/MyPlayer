package wj.com.myplayer.TestPackage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.test_btn:
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
        }
    }
}
