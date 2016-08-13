package andrewl.videowall.DataBase.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
/**
 * Created by liut1 on 8/8/16.
 */
public class VideoWallHelper {
    private static VideoWallHelper instance = null;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private WallDataDao dao;
    public VideoWallHelper getInstance(){
        if(instance == null){
            synchronized (VideoWallHelper.class){
                if(instance == null){
                    instance = new VideoWallHelper();
                }
            }
        }
        return instance;
    }
    public void initGreenDao(Context context, String dbName){

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, dbName, null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        dao = daoSession.getWallDataDao();
    }
    public WallDataDao getDao() {
        return dao;
    }
    public void addNote(WallData note){
        dao.insert(note);
    }
    public void delNote(WallData note){
        dao.delete(note);
    }
    public void searchNote(){

    }
}
