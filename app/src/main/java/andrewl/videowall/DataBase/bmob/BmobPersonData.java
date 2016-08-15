package andrewl.videowall.DataBase.bmob;

import java.io.File;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by liut1 on 8/11/16.
 */

/*
        wallData.addIdProperty();
        wallData.addDateProperty("date");
        wallData.addIntProperty("type");
        wallData.addStringProperty("name");
        wallData.addStringProperty("sex");
        wallData.addDateProperty("birthday");
        wallData.addIntProperty("age");
        wallData.addDateProperty("registerDate");
        wallData.addStringProperty("authCode");
        wallData.addStringProperty("localPicAddr");
        wallData.addStringProperty("remotePicAddr");
        wallData.addStringProperty("localVideoAddr");
        wallData.addStringProperty("remoteVideoAddr");
*/
public class BmobPersonData extends BmobObject {
    private String account;
    private String group;
    private String localPicAddr;
    private BmobFile remotePicAddr;
    private String remotePicUrl;
    private String localVideoAddr;
    private BmobFile remoteVideoAddr;
    private String remoteVideoUrl;

    public void setAccount(String account){
        this.account = account;
    }
    public String getAccount(){
        return this.account;
    }
    public void setGroup(String group){
        this.group = group;
    }
    public String getGroup(){
        return this.group;
    }
    public void setLocalPicAddr(String localPicAddr){
        this.localPicAddr = localPicAddr;
    }
    public String getLocalPicAddr(){
        return this.localPicAddr;
    }
    public void setRemotePicAddr(BmobFile remotePicAddr){
        this.remotePicAddr = remotePicAddr;
    }
    public BmobFile getRemotePicAddr(){
        return this.remotePicAddr;
    }
    public void setRemotePicUrl(String remotePicUrl){
        this.remotePicUrl = remotePicUrl;
    }
    public String getRemotePicUrl(){
        return this.remotePicUrl;
    }
    public void setLocalVideoAddr(String localVideoAddr){
        this.localVideoAddr = localVideoAddr;
    }
    public String getLocalVideoAddr(){
        return this.localVideoAddr;
    }
    public void setRemoteVideoAddr(BmobFile remoteVideoAddr){
        this.remoteVideoAddr = remoteVideoAddr;
    }
    public BmobFile getRemoteVideoAddr(){
        return this.remoteVideoAddr;
    }
    public void setRemoteVideoUrl(String remoteVideoUrl){
        this.remoteVideoUrl = remoteVideoUrl;
    }
    public String getRemoteVideoUrl(){
        return this.remoteVideoUrl;
    }
}
