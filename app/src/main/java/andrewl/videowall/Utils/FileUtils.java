package andrewl.videowall.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by liut1 on 8/13/16.
 */
public class FileUtils {
    private String mImagePath;
    private String mVideoPath;
    private String mJsonPath;
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

        mJsonPath = sdcardDir.getPath()
                + "/VideoWall/Json";
        photofile = new File(mJsonPath);
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

    public String getVideoWallJsonFolderPath(){
        return this.mJsonPath;
    }

    public void downloadImage(String path,String name) throws Exception {

            //new一个URL对象
            URL url = new URL(path);
            //打开链接
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒
            // conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream inStream=null;
            try{
                inStream = conn.getInputStream();
            }catch (Exception e) {
                Log.e("downloadImage",e.getMessage());
            }
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = readInputStream(inStream);
            //new一个文件对象用来保存图片，默认保存当前工程根目录
            File imageFile = new File(name);
            //创建输出流
            FileOutputStream outStream = new FileOutputStream(imageFile);
            //写入数据
            outStream.write(data);
            //关闭输出流
            outStream.close();
    }
    public byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }
}
