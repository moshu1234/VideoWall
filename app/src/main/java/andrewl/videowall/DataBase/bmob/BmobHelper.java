package andrewl.videowall.DataBase.bmob;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import andrewl.videowall.DataBase.UserInfo.UserInfoHelper;
import andrewl.videowall.DataBase.greendao.EventBusMessage;
import andrewl.videowall.Utils.FileUtils;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by liut1 on 8/11/16.
 */
public class BmobHelper {

    private String mObjId;
    public BmobHelper(){
        mObjId = new String();
    }
    public void initBmob(Context context){
        EventBus.getDefault().register(this);
        // 初始化 Bmob SDK
        // 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
        Bmob.initialize(context, "361261f0b3d501e23d63794752378ed7");
    }
    public void setmObjId(String objId){
        this.mObjId = objId;
    }
    public String getmObjId(){
        return mObjId;
    }
    public void initPerson(String account){

        BmobQuery<BmobPerson> query = new BmobQuery<BmobPerson>();
        query.addWhereEqualTo("account", account);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        query.findObjects(new FindListener<BmobPerson>() {
            @Override
            public void done(List<BmobPerson> object, BmobException e) {
                if(e==null){
                    //toast("查询成功：共"+object.size()+"条数据。");
                    UserInfoHelper userInfoHelper = new UserInfoHelper().getInstance();
                    for (BmobPerson bmobPerson : object) {
                        userInfoHelper.setNickNmae(bmobPerson.getNickNmae());
                        userInfoHelper.setAccount(bmobPerson.getAccount());
                        userInfoHelper.setPassword(bmobPerson.getPassword());
                        userInfoHelper.setAge(bmobPerson.getAge());
                        userInfoHelper.setSex(bmobPerson.getSex());
                        userInfoHelper.setBirthday(bmobPerson.getBirthday());
                        userInfoHelper.setRegisterDate(bmobPerson.getRegisterDate());
                        userInfoHelper.setAuthCode(bmobPerson.getAuthCode());
                        break;
                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
    //TODO:if Video bigger than 10M, don't upload to bmob
    //upload image or video to bmob
    public void updatePersonImage(final String ImgPath){
        Log.e("updatePersonImage","ImgPath:"+ImgPath);
        final BmobFile file = new BmobFile(new File(ImgPath));
        file.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    BmobPersonData data = new BmobPersonData();
                    data.setName(new UserInfoHelper().getInstance().getNickNmae());
                    data.setLocalPicAddr(ImgPath);
                    data.setRemotePicAddr(file);
                    data.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e == null){
                                Log.e("bmob save pic----------","success:"+s);
                                EventBus.getDefault().post(new EventBusMessage(21,s));
                            }else {
                                Log.e("bmob save failed",e.getMessage());
                            }
                        }
                    });
                }else {
                    Log.e("file upload failed",e.getMessage());
                }
            }
        });
    }
    //update remote image or video url
    private void updatePersonImageExt(String objId, String url){
        BmobPersonData bmobPersonData = new BmobPersonData();
        bmobPersonData.setRemotePicUrl(url);
        bmobPersonData.update(objId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    Log.e("updatePersonDataExt","success:");
                }else {
                    Log.e("updatePersonDataExt ","failed"+e.getMessage());
                }
            }
        });
    }
    //TODO:if Video bigger than 10M, don't upload to bmob
    //upload image or video to bmob
    public void updatePersonVideo(final String VideoPath){
        Log.e("updatePersonVideo","VideoPath:"+VideoPath+"    mObjId:"+mObjId);
        final BmobFile file = new BmobFile(new File(VideoPath));
        file.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    BmobPersonData data = new BmobPersonData();
                    data.setName(new UserInfoHelper().getInstance().getNickNmae());
                    data.setLocalVideoAddr(VideoPath);
                    data.setRemoteVideoAddr(file);
                    data.update(mObjId,new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                Log.e("bmob save video------","success:");
                                EventBus.getDefault().post(new EventBusMessage(22,mObjId));
                            }else {
                                Log.e("bmob save failed",e.getMessage());
                            }
                        }
                    });
                }else {
                    Log.e("file upload failed",e.getMessage());
                }
            }
        });
    }
    //update remote image or video url
    private void updatePersonVideoExt(String objId, String url){
        BmobPersonData bmobPersonData = new BmobPersonData();
        bmobPersonData.setRemoteVideoUrl(url);
        bmobPersonData.update(objId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    Log.e("updatePersonDataExt","success:");
                }else {
                    Log.e("updatePersonDataExt ","failed"+e.getMessage());
                }
            }
        });
    }
    /*
    * 21-pic
    * 22-video
    */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final EventBusMessage event) {
        switch (event.type){
            case 21:
                Log.e("======","save pic:"+event.message);
                mObjId = event.message;
                EventBus.getDefault().post(new EventBusMessage(13,mObjId));
                BmobQuery<BmobPersonData> query = new BmobQuery<BmobPersonData>();
                query.getObject(event.message, new QueryListener<BmobPersonData>() {
                    @Override
                    public void done(BmobPersonData bmobPersonData, BmobException e) {
                        if(e==null){
                            //toast("查询成功：共"+object.size()+"条数据。");
                            if(bmobPersonData.getRemotePicAddr() != null) {
                                //what's the difference between getfilename and geturl
//                                Log.e("getFilename",bmobPersonData.getRemotePicAddr().getFilename());
                                Log.e("getFileUrl",bmobPersonData.getRemotePicAddr().getFileUrl());
//                                Log.e("getUrl",bmobPersonData.getRemotePicAddr().getUrl());
//                                Log.e("getLocalFile",bmobPersonData.getRemotePicAddr().getLocalFile().getPath());
                                updatePersonImageExt(event.message, bmobPersonData.getRemotePicAddr().getFileUrl());
                            }
                        }else{
                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });
                break;
            case 22:
                Log.e("======","save video:"+event.message);
                BmobQuery<BmobPersonData> queryV = new BmobQuery<BmobPersonData>();
                queryV.getObject(event.message, new QueryListener<BmobPersonData>() {
                    @Override
                    public void done(BmobPersonData bmobPersonData, BmobException e) {
                        if(e==null){
                            //toast("查询成功：共"+object.size()+"条数据。");
                            if(bmobPersonData.getRemoteVideoAddr() != null) {
                                //what's the difference between getfilename and geturl
//                                Log.e("getFilename",bmobPersonData.getRemotePicAddr().getFilename());
                                Log.e("getFileUrl",bmobPersonData.getRemoteVideoAddr().getFileUrl());
//                                Log.e("getUrl",bmobPersonData.getRemotePicAddr().getUrl());
//                                Log.e("getLocalFile",bmobPersonData.getRemotePicAddr().getLocalFile().getPath());
                                updatePersonVideoExt(event.message, bmobPersonData.getRemoteVideoAddr().getFileUrl());
                            }
                        }else{
                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });
                break;
        }
    }
}
