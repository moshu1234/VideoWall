package andrewl.videowall.DataBase.UserInfo;

/**
 * Created by liut1 on 8/13/16.
 */
public class UserInfoHelper {
    private String nickName;
    private String account;
    private String password;
    private String sex;
    private String birthday;
    private Integer age;
    private String registerDate;
    private String authCode;

    private static UserInfoHelper instance = null;
    public UserInfoHelper getInstance(){
        if(instance == null){
            synchronized (UserInfoHelper.class){
                if(instance == null){
                    instance = new UserInfoHelper();
                    init();
                }
            }
        }
        return instance;
    }
    //init name and generate authCode
    private void init(){
        //TODO:get name from remote bmob
        //TODO:generate an authCode
    }
    public void setNickName(String nickName){
        this.nickName = nickName;
    }
    public String getNickName(){
        return this.nickName;
    }
    public void setAccount(String account){
        this.account = account;
    }
    public String getAccount(){
        return this.account;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }
    public void setSex(String sex){
        this.sex = sex;
    }
    public String getSex(){
        return this.sex;
    }
    public void setBirthday(String birthday){
        this.birthday = birthday;
    }
    public String getBirthday(){
        return this.birthday;
    }
    public void setAge(Integer age){
        this.age = age;
    }
    public Integer getAge(){
        return this.age;
    }
    public void setRegisterDate(String registerDate){
        this.registerDate = registerDate;
    }
    public String getRegisterDate(){
        return this.registerDate;
    }
    public void setAuthCode(String authCode){
        this.authCode = authCode;
    }
    public String getAuthCode(){
        return this.authCode;
    }
}
