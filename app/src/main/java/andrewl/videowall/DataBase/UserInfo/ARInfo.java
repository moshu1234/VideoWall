package andrewl.videowall.DataBase.UserInfo;

import android.util.Log;

/**
 * Created by liut1 on 8/14/16.
 */
public class ARInfo {
    private String localImgAddr;
    private String remoteImagUrl;
//    private String ImgName;

    private String localVideoADDR;
    private String remoteVideoUrl;

    public void setLocalImgAddr(String localImgAddr){
        this.localImgAddr = localImgAddr;
    }
    public String getLocalImgAddr(){
        Log.e("====","getLocalImgAddr:"+localImgAddr);
        return this.localImgAddr;
    }
    public void setRemoteImagUrl(String remoteImagUrl){
        this.remoteImagUrl = remoteImagUrl;
    }
    public String getRemoteImagUrl(){
        return this.remoteImagUrl;
    }

    public void setLocalVideoADDR(String localVideoADDR){
        this.localVideoADDR = localVideoADDR;
    }
    public String getLocalVideoADDR(){
        return this.localVideoADDR;
    }
    public void setRemoteVideoUrl(String remoteVideoUrl){
        this.remoteVideoUrl = remoteVideoUrl;
    }
    public String getRemoteVideoUrl(){
        return this.remoteVideoUrl;
    }
}
