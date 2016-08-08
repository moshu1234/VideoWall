package andrewl.videowall.DataBase.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table T_WallData.
 */
public class WallData {

    private Long id;
    private java.util.Date date;
    private Integer type;
    private String name;
    private String sex;
    private java.util.Date birthday;
    private Integer age;
    private java.util.Date registerDate;
    private String authCode;
    private String localPicAddr;
    private String remotePicAddr;
    private String localVideoAddr;
    private String remoteVideoAddr;

    public WallData() {
    }

    public WallData(Long id) {
        this.id = id;
    }

    public WallData(Long id, java.util.Date date, Integer type, String name, String sex, java.util.Date birthday, Integer age, java.util.Date registerDate, String authCode, String localPicAddr, String remotePicAddr, String localVideoAddr, String remoteVideoAddr) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.age = age;
        this.registerDate = registerDate;
        this.authCode = authCode;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public java.util.Date getBirthday() {
        return birthday;
    }

    public void setBirthday(java.util.Date birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public java.util.Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(java.util.Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
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
