package andrewl.videowall.DataBase.bmob;

/**
 * Created by liut1 on 8/13/16.
 */
public class BmobPerson {
    private String nickNmae;
    private String account;
    private String password;
    private String sex;
    private String birthday;
    private Integer age;
    private String registerDate;
    private String authCode;

    public void setNickNmae(String nickNmae){
        this.nickNmae = nickNmae;
    }
    public String getNickNmae(){
        return this.nickNmae;
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
