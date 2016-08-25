package andrewl.videowall.DataBase.UserInfo;

import android.graphics.Bitmap;

import andrewl.videowall.DataBase.bmob.BmobPersonData;

/**
 * Created by liut1 on 8/22/16.
 */
public class MgrDisplayData extends BmobPersonData {
    private int type;
    //bitmap 60X60
    private Bitmap picBitmap;
    private Bitmap vidBitmap;
    public void setType(int type){
        this.type =type;
    }
    public int getType(){
        return this.type;
    }
    public void setPicBitmap(Bitmap picBitmap){
        this.picBitmap =picBitmap;
    }
    public Bitmap getPicBitmap(){
        return this.picBitmap;
    }
    public void setVidBitmap(Bitmap vidBitmap){
        this.vidBitmap =vidBitmap;
    }
    public Bitmap getVidBitmap(){
        return this.vidBitmap;
    }
}
