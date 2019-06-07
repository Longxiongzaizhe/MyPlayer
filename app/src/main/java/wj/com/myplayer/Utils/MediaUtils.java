package wj.com.myplayer.Utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import wj.com.myplayer.DaoDB.MediaEntity;

public class MediaUtils {


    private static String TAG = MediaUtils.class.getSimpleName();

    public static List<MediaEntity> getAllMediaList(Context context, String selection) {
        Cursor cursor = null;
        List<MediaEntity> mediaList = new ArrayList<MediaEntity>();
        try {
            cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    new String[] {
                            MediaStore.Audio.Media._ID,
                            MediaStore.Audio.Media.TITLE,
                            MediaStore.Audio.Media.DISPLAY_NAME,
                            MediaStore.Audio.Media.DURATION,
                            MediaStore.Audio.Media.ARTIST,
                            MediaStore.Audio.Media.DATA,
                            MediaStore.Audio.Media.SIZE},
                    selection, null, MediaStore.Audio.Media.DATE_ADDED + " DESC");
            if(cursor == null) {
                Log.d(TAG, "The getMediaList cursor is null.");
                return mediaList;
            }
            int count= cursor.getCount();
            if(count <= 0) {
                Log.d(TAG, "The getMediaList cursor count is 0.");
                return mediaList;
            }
            mediaList = new ArrayList<MediaEntity>();
            MediaEntity mediaEntity = null;
//			String[] columns = cursor.getColumnNames();
            while (cursor.moveToNext()) {
                mediaEntity = new MediaEntity();
                mediaEntity.id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                mediaEntity.title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                mediaEntity.display_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                mediaEntity.duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                mediaEntity.size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
               // mediaEntity.durationStr = longToStrTime(mediaEntity.duration);

                if(!checkIsMusic(mediaEntity.duration, mediaEntity.size)) {
                    continue;
                }
                mediaEntity.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                mediaEntity.path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                mediaList.add(mediaEntity);
            }
        } catch (Exception e) {

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



}
