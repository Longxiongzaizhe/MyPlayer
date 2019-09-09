package com.hjl.module_main.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hjl.commonlib.network.HttpHandler;
import com.hjl.commonlib.utils.LogUtils;
import com.hjl.commonlib.utils.StringUtils;
import com.hjl.module_main.R;
import com.hjl.module_main.constant.MediaConstant;
import com.hjl.module_main.daodb.MediaAlbumsEntity;
import com.hjl.module_main.daodb.MediaAlbumsManager;
import com.hjl.module_main.daodb.MediaDaoManager;
import com.hjl.module_main.daodb.MediaEntity;
import com.hjl.module_main.net.DoubanNetworkWrapper;
import com.hjl.module_main.net.NetworkWrapper;
import com.hjl.module_main.net.bean.SearchPicBean;
import com.hjl.module_main.net.bean.douban.MusicSearchBean;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MediaUtils {

    private static ExecutorService cacheThread = Executors.newCachedThreadPool();

    private static String TAG = MediaUtils.class.getSimpleName();
    //获取专辑封面的Uri
    private static final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");
    private static MediaDaoManager mediaDaoManager = MediaDaoManager.getInstance();

    public static List<MediaEntity> getAllMediaList(Context context, String selection) {

        Cursor cursor = null;
        List<MediaEntity> mediaList = new ArrayList<>();
        try {
            cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,
                    selection, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
            if(cursor == null) {
                Log.d(TAG, "The getMediaList cursor is null.");
                return mediaList;
            }
            int count= cursor.getCount();
            if(count <= 0) {
                Log.d(TAG, "The getMediaList cursor count is 0.");
                return mediaList;
            }
            mediaList = new ArrayList<>();
            MediaEntity mediaEntity;
            while (cursor.moveToNext()) {
                mediaEntity = new MediaEntity();

                mediaEntity.id = (long) cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                mediaEntity.title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                mediaEntity.display_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                mediaEntity.duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                mediaEntity.size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                mediaEntity.album_id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                mediaEntity.canGetCover = true;

                if(!checkIsMusic(mediaEntity.duration, mediaEntity.size)) {
                    continue;
                }
                mediaEntity.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                mediaEntity.path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                mediaList.add(mediaEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(cursor != null) {
                cursor.close();
            }
        }
        return mediaList;
    }


    public static boolean checkIsMusic(long time, long size) {
        if(time <= 0 || size <= 0) {
            return  false;
        }

        time /= 1000;
        long minute = time / 60;
        //	int hour = minute / 60;
        long second = time % 60;
        minute %= 60;
        if(minute <= 0 && second <= 30) {
            return  false;
        }
        if(size <= 1024 * 1024){
            return false;
        }
        return true;
    }

    /**
     * 获取默认专辑图片
     * @param context
     * @return
     */
    @SuppressLint("ResourceType")
    public static Bitmap getDefaultArtwork(Context context, boolean small) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        if(small){	//返回小图片
            return BitmapFactory.decodeStream(context.getResources().openRawResource(R.drawable.icon_dog), null, opts);
        }
        return BitmapFactory.decodeStream(context.getResources().openRawResource(R.drawable.icon_dog), null, opts);
    }


    /**
     * 从文件当中获取专辑封面位图
     * @param context
     * @param songid
     * @param albumid
     * @return
     */
    private static Bitmap getArtworkFromFile(Context context, long songid, long albumid){
        Bitmap bm = null;
        if(albumid < 0 && songid < 0) {
            throw new IllegalArgumentException("Must specify an album or a song id");
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            FileDescriptor fd = null;
            if(albumid < 0){
                Uri uri = Uri.parse("content://media/external/audio/media/"
                        + songid + "/albumart");
                ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                if(pfd != null) {
                    fd = pfd.getFileDescriptor();
                }
            } else {
                Uri uri = ContentUris.withAppendedId(albumArtUri, albumid);
                ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                if(pfd != null) {
                    fd = pfd.getFileDescriptor();
                }
            }
            options.inSampleSize = 1;
            // 只进行大小判断
            options.inJustDecodeBounds = true;
            // 调用此方法得到options得到图片大小
            BitmapFactory.decodeFileDescriptor(fd, null, options);
            // 我们的目标是在800pixel的画面上显示
            // 所以需要调用computeSampleSize得到图片缩放的比例
            options.inSampleSize = 100;
            // 我们得到了缩放的比例，现在开始正式读入Bitmap数据
            options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            //根据options参数，减少所需要的内存
            bm = BitmapFactory.decodeFileDescriptor(fd, null, options);
        } catch (FileNotFoundException e) {
          //  e.printStackTrace();
        }
        return bm;
    }

    public static Bitmap getArtWorkFormFile(ContentResolver resolver,int songid,int album){
        Bitmap bitmap = null;
        if(album <0 &&songid <0){
            throw new IllegalArgumentException("Must specify an album or song");
        }

        try{
            BitmapFactory.Options options = new BitmapFactory.Options();
            FileDescriptor fileDescriptor = null;
            if(album <0){
                Uri uri = Uri.parse("content://media/external/audio/media/"+songid+"/albumart");
                ParcelFileDescriptor parcelFileDescriptor = resolver.openFileDescriptor(uri,"r");
                if(parcelFileDescriptor !=null){
                    fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                }
            }else {
                Uri uri = ContentUris.withAppendedId(albumArtUri,album);
                ParcelFileDescriptor pfd = resolver.openFileDescriptor(uri,"r");
                if(pfd != null){
                    fileDescriptor = pfd.getFileDescriptor();
                }
            }

            options.inSampleSize = 1;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fileDescriptor,null,options);
            options.inSampleSize = calculateInSampleSize(options,50,50);
            options.inJustDecodeBounds =false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor,null,options);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return  bitmap;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap getArtwork(ContentResolver resolver,int songid,int albumid ,boolean allowdefalut,boolean samll){
        if(albumid <0 ){
            if(songid <0){
                Bitmap bitmap = getArtWorkFormFile(resolver,songid,albumid);
                if(bitmap !=null){
                    return bitmap;
                }
            }
            return null;
        }
        Uri uri = ContentUris.withAppendedId(albumArtUri,albumid);
        if(uri != null){
            InputStream inputStream = null;
            try{
                inputStream = resolver.openInputStream(uri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(inputStream,null,options);
                if(samll){
                    options.inSampleSize = calculateInSampleSize(options,50,50);
                }else {
                    options.inSampleSize = calculateInSampleSize(options,600,600);
                }
                options.inJustDecodeBounds= false;
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                inputStream = resolver.openInputStream(uri);
                return BitmapFactory.decodeStream(inputStream,null,options);


            }catch (FileNotFoundException e){
                Bitmap bitmap = getArtWorkFormFile(resolver,songid,albumid);
                if(bitmap != null){
                    if(bitmap.getConfig() == null){
                        bitmap = bitmap.copy(Bitmap.Config.RGB_565,false);
                        if(bitmap == null && allowdefalut){
                            return  null;
                        }
                    }
                }

                return bitmap;

            }
        }
        return null;
    }

    /**
     * 获取专辑封面位图对象
     * @param context
     * @param song_id
     * @param album_id
     * @param allowdefalut
     * @return
     */
    public static Bitmap getArtwork(Context context, long song_id, long album_id, boolean allowdefalut, boolean small){



        if(album_id < 0) {
            if(song_id < 0) {
                Bitmap bm = getArtworkFromFile(context, song_id, -1);
                if(bm != null) {
                    return bm;
                }
            }
            if(allowdefalut) {
                return getDefaultArtwork(context, small);
            }
            return null;
        }
        ContentResolver res = context.getContentResolver();
        Uri uri = ContentUris.withAppendedId(albumArtUri, album_id);
        if(uri != null) {
            InputStream in = null;
            try {
                in = res.openInputStream(uri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                //先制定原始大小
                options.inSampleSize = 1;
                //只进行大小判断
                options.inJustDecodeBounds = true;
                //调用此方法得到options得到图片的大小
                BitmapFactory.decodeStream(in, null, options);
                /** 我们的目标是在你N pixel的画面上显示。 所以需要调用computeSampleSize得到图片缩放的比例 **/
                /** 这里的target为800是根据默认专辑图片大小决定的，800只是测试数字但是试验后发现完美的结合 **/
                if(small){
                    options.inSampleSize = computeSampleSize(options, 40);
                } else{
                    options.inSampleSize = computeSampleSize(options, 600);
                }
                // 我们得到了缩放比例，现在开始正式读入Bitmap数据
                options.inJustDecodeBounds = false;
                options.inDither = false;
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                in = res.openInputStream(uri);
                return BitmapFactory.decodeStream(in, null, options);
            } catch (FileNotFoundException e) {
                Bitmap bm = getArtworkFromFile(context, song_id, album_id);
                if(bm != null) {
                    if(bm.getConfig() == null) {
                        bm = bm.copy(Bitmap.Config.RGB_565, false);
                        if(bm == null && allowdefalut) {
                            return getDefaultArtwork(context, small);
                        }
                    }
                } else if(allowdefalut) {
                    bm = getDefaultArtwork(context, small);
                }
                return bm;
            } finally {
                try {
                    if(in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 对图片进行合适的缩放
     * @param options
     * @param target
     * @return
     */
    public static int computeSampleSize(BitmapFactory.Options options, int target) {
        int w = options.outWidth;
        int h = options.outHeight;
        int candidateW = w / target;
        int candidateH = h / target;
        int candidate = Math.max(candidateW, candidateH);
        if(candidate == 0) {
            return 1;
        }
        if(candidate > 1) {
            if((w > target) && (w / candidate) < target) {
                candidate -= 1;
            }
        }
        if(candidate > 1) {
            if((h > target) && (h / candidate) < target) {
                candidate -= 1;
            }
        }
        return candidate;
    }

    public static MediaConstant.MusicMode getMusicMode(String mode){

        if (StringUtils.isEmpty(mode)) return null;

        MediaConstant.MusicMode musicMode = MediaConstant.MusicMode.SEQUENT;

        switch (mode){
            case "CIRCLE":musicMode = MediaConstant.MusicMode.CIRCLE;break;
            case "RANDOM":musicMode = MediaConstant.MusicMode.RANDOM;break;
            case "SINGLE":musicMode = MediaConstant.MusicMode.SINGLE;break;
            case "SEQUENT":musicMode = MediaConstant.MusicMode.SEQUENT;break;
        }

        return musicMode;
    }

    public static void setMusicCover(Context context, MediaEntity entity, ImageView imageView){
        DoubanNetworkWrapper.searchMusic(entity.getTitle(), "", "", "10", new HttpHandler<MusicSearchBean>() {
            @Override
            public void onSuccess(MusicSearchBean data) {
                if (data.getMusics().size() == 0) return;
                String url = data.getMusics().get(0).getImage();
                LogUtils.i("searchMusic","title is "+entity.getTitle() +" url is: " + url);
                Glide.with(context).load(url).into(imageView);
                entity.setCoverUrl(url);
                mediaDaoManager.update(entity);
            }
            @Override
            public void onFailure(String message,String response) {
                NetworkWrapper.searchPic(entity.title + entity.artist, new HttpHandler<SearchPicBean>() {
                    @Override
                    public void onSuccess(SearchPicBean data) {
                        if (data.getList() == null || data.getList().size() == 0) return;
                        String url = data.getList().get(0).get_thumb();
                        LogUtils.i("searchPic","title is "+entity.getTitle() +" url is: " + url);
                        Glide.with(context).load(url).into(imageView);
                        entity.setCoverUrl(url);
                        mediaDaoManager.update(entity);
                    }

                    @Override
                    public void onFailure(String message, String response) {

                    }
                });
            }
        });
    }

    public static void initMusicCover(int pageSize, int pageIndex){
        cacheThread.execute(() -> {
            List<MediaEntity> list = mediaDaoManager.loadAll(pageSize,pageIndex);
            for (MediaEntity entity : list){
                if (StringUtils.isEmpty(entity.coverUrl)){
                    DoubanNetworkWrapper.searchMusic(entity.getTitle(), "", "", "10", new HttpHandler<MusicSearchBean>() {
                        @Override
                        public void onSuccess(MusicSearchBean data) {
                            if (data.getMusics().size() == 0) return;
                            String url = data.getMusics().get(0).getImage();
                            entity.setCoverUrl(url);
                            mediaDaoManager.update(entity);
                        }

                        @Override
                        public void onFailure(String message, String response) {
                            Log.e("initMusicCover",message);
                        }
                    });
                }
            }
        });

    }

    public static void initAlbumCover(){
        List<MediaAlbumsEntity> list = MediaAlbumsManager.getInstance().loadAll();
        for (MediaAlbumsEntity albumsEntity : list){

            MediaEntity entity = mediaDaoManager.getMusicByAlbumId(albumsEntity.id);
            if (StringUtils.isEmpty(entity.getCoverUrl())){
                DoubanNetworkWrapper.searchMusic(entity.getTitle(), "", "", "10", new HttpHandler<MusicSearchBean>() {
                    @Override
                    public void onSuccess(MusicSearchBean data) {
                        if (data.getMusics().size() == 0) return;
                        String url = data.getMusics().get(0).getImage();
                        entity.setCoverUrl(url);
                        MediaDaoManager.getInstance().update(entity);
                        albumsEntity.setCoverUrl(url);
                        MediaAlbumsManager.getInstance().update(albumsEntity);
                    }

                    @Override
                    public void onFailure(String message, String response) {
                        Log.e("initAlbumCover",message);
                    }
                });
            }else {
                albumsEntity.setCoverUrl(entity.getCoverUrl());
                MediaAlbumsManager.getInstance().update(albumsEntity);
            }


        }
    }

}
