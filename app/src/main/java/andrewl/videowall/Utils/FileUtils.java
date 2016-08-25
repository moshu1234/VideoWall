package andrewl.videowall.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private String mTmpPath;
    private String mImageFile;
    private String mVideoFile;
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
    public void setmImageFile(String file){
        mImageFile = file;
    }
    public String getmImageFile(){
        return mImageFile;
    }
    public void setmVideoFile(String file){
        mVideoFile = file;
    }
    public String getmVideoFile(){
        return mVideoFile;
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
    public String getMicroMsgPath(){
        // 创建一个文件夹对象，赋值为外部存储器的目录
        File sdcardDir = Environment.getExternalStorageDirectory();
        // 得到一个路径，内容是sdcard的文件夹路径和名字
        String path = sdcardDir.getPath()
                + "/tencent/MicroMsg/03f4f84ab7a3fed488fe2439847f5bff/video";

        return path;
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

        mTmpPath = sdcardDir.getPath()
                + "/VideoWall/Tmp";
        photofile = new File(mTmpPath);
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
        Log.e("downloadImage","path:"+path+"      name:"+name);
            //new一个URL对象
            URL url = new URL(path);
            //打开链接
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        Log.e("downloadImage","1");
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
        Log.e("downloadImage","2");
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = readInputStream(inStream);
            //new一个文件对象用来保存图片，默认保存当前工程根目录
            File imageFile = new File(name);
            //创建输出流
            FileOutputStream outStream = new FileOutputStream(imageFile);
            //写入数据
        Log.e("downloadImage","3");
            outStream.write(data);
            //关闭输出流
            outStream.close();
        Log.e("downloadImage","4");
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

    public String readJsonFile(String fileName) throws IOException {
        String res = "";
        try {

            FileInputStream fin = new FileInputStream(fileName);
            int length = fin.available();

            byte[] buffer = new byte[length];
            fin.read(buffer);
            res = new String(buffer,"UTF-8");
//            res = EncodingUtils.getString(buffer, "UTF-8");

            fin.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public Bitmap resize(Bitmap bitmap,float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale,scale); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }
    public String compressPicture(String path){
        Log.e("compressPicture","path:"+path);
        String filename = "tmp.png";
        Float scale = 0.1f;
        if(path != null){
            String s[] = path.split("/");
            if(s != null && s.length > 0){
                filename = s[s.length-1];
            }
        }
        Log.e("compressPicture","filename:"+filename);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if(bitmap.getWidth() > 300 || bitmap.getHeight() > 300){
            if(bitmap.getWidth() > bitmap.getHeight()){
                scale = (float) 300/bitmap.getWidth();
            }else {
                scale = (float) 300/bitmap.getHeight();
            }
        }
        Log.e("compressPicture","scale:"+scale);
        bitmap = resize(bitmap,scale);

        File file = new File(mTmpPath+"/"+filename);
        if(file.exists()){
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, out);
            out.flush();
            out.close();
            Log.i("compressPicture", "已经保存");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            Log.e("compressPicture","FileNotFoundException:"+e.toString());
            e.printStackTrace();
            return path;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("compressPicture","IOException:"+e.toString());
            return path;
        }
        Log.e("compressPicture","final filename:"+mTmpPath+"/"+filename);
        return mTmpPath+"/"+filename;
    }
    public Bitmap getImageThumbnail(String imagePath,int width, int height){
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(imagePath,options);
        options.inJustDecodeBounds = false;
        int h = bitmap.getHeight();
        int w = bitmap.getWidth();
        int beWidth = w/width;
        int beHeight = h/height;
        int be = 1;
        if(beWidth < beHeight){
            be = beWidth;
        }else {
            be = beHeight;
        }
        if(be <= 0){
            be = 1;
        }
        options.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(imagePath,options);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap,width,height,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

        return  bitmap;
    }
    public Bitmap generateVideoThumbnail(String videoPath,int width,int height, int kind){
        Bitmap bitmap = null;
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath,kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap,width,height,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }
}
