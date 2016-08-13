package andrewl.videowall.DataBase.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table T_WallData.
 */
public class WallData {

    private Long id;
    private java.util.Date date;
    private String name;
    private String localPicAddr;
    private String remotePicAddr;
    private String localVideoAddr;
    private String remoteVideoAddr;

    public WallData() {
    }

    public WallData(Long id) {
        this.id = id;
    }

    public WallData(Long id, java.util.Date date, String name, String localPicAddr, String remotePicAddr, String localVideoAddr, String remoteVideoAddr) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.localPicAddr = localPicAddr;
        this.remotePicAddr = remotePicAddr;
        this.localVideoAddr = localVideoAddr;
        this.remoteVideoAddr = remoteVideoAddr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalPicAddr() {
        return localPicAddr;
    }

    public void setLocalPicAddr(String localPicAddr) {
        this.localPicAddr = localPicAddr;
    }

    public String getRemotePicAddr() {
        return remotePicAddr;
    }

    public void setRemotePicAddr(String remotePicAddr) {
        this.remotePicAddr = remotePicAddr;
    }

    public String getLocalVideoAddr() {
        return localVideoAddr;
    }

    public void setLocalVideoAddr(String localVideoAddr) {
        this.localVideoAddr = localVideoAddr;
    }

    public String getRemoteVideoAddr() {
        return remoteVideoAddr;
    }

    public void setRemoteVideoAddr(String remoteVideoAddr) {
        this.remoteVideoAddr = remoteVideoAddr;
    }

}
