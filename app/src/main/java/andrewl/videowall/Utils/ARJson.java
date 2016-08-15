package andrewl.videowall.Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import andrewl.videowall.DataBase.UserInfo.ARInfo;

/**
 * Created by liut1 on 8/14/16.
 */
public class ARJson {

//    private static ARJson instance = null;
//    public ARJson getInstance(){
//        if(instance == null){
//            synchronized (ARJson.class){
//                if(instance == null){
//                    instance = new ARJson();
//                }
//            }
//        }
//        return instance;
//    }
    public void saveAsJsonFile(String group, List<ARInfo> arInfos) throws JSONException, IOException {

        String jsonDir = new FileUtils().getInstance().getVideoWallJsonFolderPath();
        Log.e("saveAsJsonFile","group:"+group+"     jsondir:"+jsonDir);
        Map<String,String> element = new HashMap<String, String>();
        JSONObject jsonObject = new JSONObject();
        for(ARInfo arInfo : arInfos){
            String s[] = arInfo.getLocalImgAddr().split("/");
            Log.e("saveAsJsonFile","getLocalImgAddr:"+s[s.length-1]);
            jsonObject.put("imageName",s[s.length-1]);
            Log.e("saveAsJsonFile","getRemoteImagUrl:"+arInfo.getRemoteImagUrl());
            jsonObject.put("imageUrl",arInfo.getRemoteImagUrl());
            String sv[] = arInfo.getLocalVideoADDR().split("/");
            Log.e("saveAsJsonFile","getLocalVideoADDR:"+sv[sv.length-1]);
            jsonObject.put("videoName",sv[sv.length-1]);
            Log.e("saveAsJsonFile","getRemoteVideoUrl:"+arInfo.getRemoteVideoUrl());
            jsonObject.put("videoUrl",arInfo.getRemoteVideoUrl());
        }
        String json = jsonObject.toString();
        Log.e("saveAsJsonFile","json:"+json);
        File jFile = new File(jsonDir+"/"+group+".json");
        if(jFile.exists()){
            //do nothing
        }else {
            FileOutputStream outStream = new FileOutputStream(jFile);
            //写入数据
            outStream.write(json.getBytes());
            //关闭输出流
            outStream.close();
        }
    }
}
