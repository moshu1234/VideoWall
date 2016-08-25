package andrewl.videowall.DataBase.bmob;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import andrewl.videowall.DataBase.UserInfo.ARInfo;
import andrewl.videowall.DataBase.UserInfo.UserInfoHelper;
import andrewl.videowall.DataBase.greendao.EventBusMessage;
import andrewl.videowall.Utils.ARJson;
import andrewl.videowall.Utils.FileUtils;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by liut1 on 8/11/16.
 */
public class BmobHelper {

    private String mObjId;
    private String mVideoPath;
    private String mImagePath;
    public void initBmob(Context context){
        EventBus.getDefault().register(this);
        // 初始化 Bmob SDK
        // 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
        Bmob.initialize(context, "361261f0b3d501e23d63794752378ed7");
        initPerson("liutao");
    }
    public void initPerson(String account){
        UserInfoHelper userInfoHelper = new UserInfoHelper().getInstance();
        userInfoHelper.setNickName("andrew");
        userInfoHelper.setAccount("liutao");
        userInfoHelper.setPassword("123456");
        userInfoHelper.setAge(11);
        userInfoHelper.setSex("male");
        userInfoHelper.setBirthday("2012-11-12");
        userInfoHelper.setRegisterDate("2016-08-14");
        userInfoHelper.setAuthCode("1234");


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
                        userInfoHelper.setNickName(bmobPerson.getNickNmae());
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
        //create ar.json with image and video url
        createARJson("default",account);
    }
    private void createARJson(final String group, String account){
        final ArrayList<ARInfo> data = new ArrayList<>();
        BmobQuery<BmobPersonData> eq1 = new BmobQuery<BmobPersonData>();
        eq1.addWhereEqualTo("account",account);
        BmobQuery<BmobPersonData> eq2 = new BmobQuery<BmobPersonData>();
        eq2.addWhereEqualTo("group",group);
        List<BmobQuery<BmobPersonData>>andQuerys = new ArrayList<BmobQuery<BmobPersonData>>();
        andQuerys.add(eq1);
        andQuerys.add(eq2);
        BmobQuery<BmobPersonData> query = new BmobQuery<BmobPersonData>();
        query.and(andQuerys);
        query.setLimit(100);
        query.findObjects(new FindListener<BmobPersonData>() {
            @Override
            public void done(List<BmobPersonData> list, BmobException e) {
                FileUtils fileUtils = new FileUtils().getInstance();
                String imagePath = fileUtils.getVideoWallImageFolderPath();
                String videoPath = fileUtils.getVideoWallVideoFolderPath();
                //save to json
                if(e==null){
                    ArrayList<ARInfo> arInfos = new ArrayList<ARInfo>();
                    for (BmobPersonData bmobPersonData : list){
                        ARInfo arInfo = new ARInfo();

                        //we have the local file
                        if(bmobPersonData.getLocalPicAddr() != null && new File(bmobPersonData.getLocalPicAddr()).exists()){
                            //save local file path into json
                            arInfo.setLocalImgAddr(bmobPersonData.getLocalPicAddr());

                            Log.e("local adddr","++++:"+bmobPersonData.getLocalPicAddr());
                        }else {
                            Log.e("======","1");
                            //download the image
                            try {
                                //have remote addr
                                if(bmobPersonData.getRemotePicAddr()!=null ){
                                    Log.e("======","2");
                                    //have downloaded
                                    if(new File(imagePath+"/"+bmobPersonData.getRemotePicAddr().getFilename()).exists()){
                                        arInfo.setLocalImgAddr(imagePath+"/"+bmobPersonData.getRemotePicAddr().getFilename());
                                    }else {
                                        Log.e("======","3");
//                                        fileDownload();
                                        fileDownload(bmobPersonData.getRemotePicUrl(),imagePath+"/"+bmobPersonData.getRemotePicAddr().getFilename());
                                        arInfo.setLocalImgAddr(imagePath+"/"+bmobPersonData.getRemotePicAddr().getFilename());
                                    }
                                }else {
                                    Log.e("======","4");
                                    continue;
                                }
                            } catch (Exception e1) {
                                Log.e("======","5"+e1.getMessage()+e1.toString());
                                e1.printStackTrace();
                            }
                        }
                        if(bmobPersonData.getRemotePicUrl()!=null) {
                            arInfo.setRemoteImagUrl(bmobPersonData.getRemotePicUrl());
                        }else {
                            continue;
                        }

                        if(bmobPersonData.getLocalVideoAddr()!=null&&new File(bmobPersonData.getLocalVideoAddr()).exists()){
                            arInfo.setLocalVideoADDR(bmobPersonData.getLocalVideoAddr());
                        }else {
                            //file has downloaded else download now
                            if(bmobPersonData.getRemoteVideoAddr()!=null){
                                if(bmobPersonData.getRemoteVideoUrl()!=null) {
                                    arInfo.setRemoteVideoUrl(bmobPersonData.getRemoteVideoUrl());
                                }else {
                                    continue;
                                }
                            }else {
                                continue;
                            }
                        }
                        if(bmobPersonData.getRemoteVideoUrl()!=null) {
                            arInfo.setRemoteVideoUrl(bmobPersonData.getRemoteVideoUrl());
                        }else {
                            continue;
                        }
                        Log.e("local adddr","-----:"+arInfo.getLocalImgAddr());
                        arInfos.add(arInfo);
                    }

                    try {
                        new ARJson().saveAsJsonFile(group,arInfos);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }else {
                    Log.e("query failed","createARJson"+e.getMessage()+e.toString());
                }
            }
        });
    }

    //TODO:if Video bigger than 10M, don't upload to bmob
    //upload image or video to bmob
    public void updatePersonData(final String[] path){
        BmobFile.uploadBatch(path, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                Log.e("update success","file size:"+files.size()+"   url size:"+urls.size());
                if(urls.size() == path.length) {
                    BmobPersonData data = new BmobPersonData();
                    data.setGroup("default");
                    data.setAccount(new UserInfoHelper().getInstance().getAccount());
                    data.setLocalPicAddr(path[0]);
                    data.setRemotePicAddr(files.get(0));
                    data.setRemotePicUrl(urls.get(0));
                    data.setLocalVideoAddr(path[1]);
                    data.setRemoteVideoAddr(files.get(1));
                    data.setRemoteVideoUrl(urls.get(1));
                    data.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Log.e("updatePersonData", "success:" + s);
                                EventBus.getDefault().post(new EventBusMessage(16, "success"));
                                createARJson("default",new UserInfoHelper().getInstance().getAccount());
                            } else {
                                Log.e("updatePersonData", "failed:" + e.getMessage());
                                EventBus.getDefault().post(new EventBusMessage(16, "fail"));
                            }
                        }
                    });
                }
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {

                Log.e("onProgress","curIndex:"+curIndex+"   curPercent:"+curPercent+"   total:"+total+"  totalPercent:"+totalPercent);
                EventBus.getDefault().post(new EventBusMessage(17,curPercent*totalPercent/100+"%"));
            }

            @Override
            public void onError(int i, String s) {
                Log.e("updatePersonData",i+":failed:"+s);
                EventBus.getDefault().post(new EventBusMessage(16,"fail"));
            }
        });
    }

    public void fileDownload(String url, String savePath){
        Log.e("fileDownload","start:"+url+"["+savePath+"]");
        BmobFile file = new BmobFile("aa","",url);
        File saveFile = new  File(savePath);
        file.download(saveFile, new DownloadFileListener() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    Log.e("fileDownload","done:"+s);
                }else {
                    Log.e("fileDownload","exception:"+e.toString());
                }
            }

            @Override
            public void onProgress(Integer integer, long l) {

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
                break;
            case 22:
                break;
        }
    }
}
