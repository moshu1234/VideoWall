package andrewl.videowall.DataBase.greendao;

/**
 * @param type
 * 1-10  for main activity
 * 11-20  for video connect activity
 * 21-30  for bmob
 * 31-40  for greendao
 * Created by liut1 on 8/11/16.
 */
public class EventBusMessage {
    public final Integer type;
    public final String message;
    public EventBusMessage(Integer type, String message){
        this.type = type;
        this.message = message;
    }
}
