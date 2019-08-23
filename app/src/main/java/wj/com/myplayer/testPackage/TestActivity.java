package wj.com.myplayer.testPackage;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.commonlib.network.HttpHandler;
import com.example.commonlib.network.NetworkWrapper;
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
        getData();
        mResultTv.setText(musicList.size() + "albumId " + musicList.get(0).albumId);
        mResultIv.setImageBitmap(musicList.get(0).thumbBitmap);

    }

    private void getData(){
        ContentResolver resolver =getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        do {
            Music m = new Music();
            m.name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            m.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            m.album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            m.path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            m.length = Integer.parseInt(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
            //获取专辑ID
            int albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            //根据专辑ID获取到专辑封面图
            m.thumbBitmap = getAlbumArt(albumId);

            musicList.add(m);
        } while (cursor.moveToNext());
        cursor.close();
    }

    private Bitmap getAlbumArt(int album_id) {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[]{"album_art"};
        Cursor cur = getContentResolver().query(Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)), projection, null, null, null);
        String album_art = null;
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
        }
        cur.close();
        Bitmap bm = null;
        if (album_art != null) {
            bm = BitmapFactory.decodeFile(album_art);
        } else {
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.icon_clock);
        }
        return bm;
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
                File file = new File(getFilesDir(), "test.png");
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
