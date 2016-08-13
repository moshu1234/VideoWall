package andrewl.videowall.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by liut1 on 8/13/16.
 */
public class FileUtils {
    private String mImagePath;
    private String mVideoPath;
    private static FileUtils instance = null;
    public FileUtils getInstance(){
        if(instance == null){
            synchronized (FileUtils.class){
                if(instance == null){
                    instance = new FileUtils();
                }
            }
        }
        return instance;
    }
    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public String convertUriToPath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public void createVideoWallFolder(){
        // 创建一个文件夹对象，赋值为外部存储器的目录
        File sdcardDir = Environment.getExternalStorageDirectory();
        // 得到一个路径，内容是sdcard的文件夹路径和名字
        mImagePath = sdcardDir.getPath()
                + "/VideoWall/Images";
        File photofile = new File(mImagePath);
        if (!photofile.exists()) {
            // 若不存在，创建目录，可以在应用启动的时候创建
            photofile.mkdirs();
        }

        mVideoPath = sdcardDir.getPath()
                + "/VideoWall/Videos";
        photofile = new File(mVideoPath);
        if (!photofile.exists()) {
            // 若不存在，创建目录，可以在应用启动的时候创建
            photofile.mkdirs();
        }
    }

    public String getVideoWallImageFolderPath(){
        return this.mImagePath;
    }
    public String getVideoWallVideoFolderPath(){
        return this.mVideoPath;
    }
}
