package andrewl.videowall.Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import andrewl.videowall.DataBase.UserInfo.ARInfo;

/**
 * Created by liut1 on 8/14/16.
 */
public class ARJson {
    public void saveAsJsonFile(String group, ArrayList<ARInfo> arInfos) throws JSONException, IOException {

        String jsonDir = new FileUtils().getInstance().getVideoWallJsonFolderPath();
        Log.e("saveAsJsonFile","group:"+group+"     jsondir:"+jsonDir+"     :"+arInfos.size());
        Map<String,String> element = new HashMap<String, String>();

        JSONArray jsonArray = new JSONArray();
        for(ARInfo arInfo : arInfos){
            JSONObject jsonObject = new JSONObject();
            String s[] = arInfo.getLocalImgAddr().split("/");

            Log.e("saveAsJsonFile","getLocalImgAddr:"+s[s.length-1]);
            jsonObject.put("imageName",s[s.length-1]);

            Log.e("saveAsJsonFile","getRemoteImagUrl:"+arInfo.getLocalImgAddr());
            jsonObject.put("imageUrl",arInfo.getLocalImgAddr());


            if(arInfo.getLocalVideoADDR() != null) {
                String sv[] = arInfo.getLocalVideoADDR().split("/");
                Log.e("saveAsJsonFile","getLocalVideoADDR:"+sv[sv.length-1]);
                jsonObject.put("videoName",sv[sv.length-1]);
                Log.e("saveAsJsonFile", "getLocalVideoADDR:" + arInfo.getLocalVideoADDR());
                jsonObject.put("videoUrl", arInfo.getLocalVideoADDR());
            }else {
                String sv[] = arInfo.getRemoteVideoUrl().split("/");
                Log.e("saveAsJsonFile","getRemoteVideoUrl:"+sv[sv.length-1]);
                jsonObject.put("videoName",sv[sv.length-1]);
                Log.e("saveAsJsonFile", "getRemoteVideoUrl:" + arInfo.getRemoteVideoUrl());
                jsonObject.put("videoUrl", arInfo.getRemoteVideoUrl());
            }

            jsonArray.put(jsonObject);
        }
        String json = jsonArray.toString();
        Log.e("saveAsJsonFile","json:"+json);
        File jFile = new File(jsonDir+"/"+group+".json");
        if(jFile.exists()){
            //do nothing
            jFile.delete();
        }
            FileOutputStream outStream = new FileOutputStream(jFile);
            //写入数据
            outStream.write(json.getBytes());
            //关闭输出流
            outStream.close();
    }

    public ArrayList<ARInfo> parseARJson(String group) {
        FileUtils fileUtils = new FileUtils().getInstance();
        ArrayList<ARInfo> arInfos = new ArrayList<>();
        String jsonDir = fileUtils.getVideoWallJsonFolderPath();
        File jFile = new File(jsonDir+"/"+group+".json");
        Log.e("jsonfile",jsonDir+"/"+group+".json");
        if(jFile.exists()){
            String json = null;
            try {
                json = fileUtils.readJsonFile(jsonDir+"/"+group+".json");
            } catch (IOException e) {
                e.printStackTrace();
            }
//            Log.e("parseARJson","json:"+json);
            try {
                JSONArray jsonArray = new JSONArray(json);
//                Log.e("jsonArray",jsonArray.toString()+"        -----------         ");
                Log.e("jsonArray","length:"+jsonArray.length());
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    ARInfo arInfo = new ARInfo();
                    Log.e("imagename",jsonObject.getString("imageName"));
                    String s[] = jsonObject.getString("imageName").split("\\.");
                    Log.e("imagename",s[0]);
                    arInfo.setLocalImgAddr(s[0]);
                    Log.e("imageUrl",jsonObject.getString("imageUrl"));
                    arInfo.setRemoteImagUrl(jsonObject.getString("imageUrl"));
                    Log.e("videoName",jsonObject.getString("videoName"));
                    arInfo.setLocalVideoADDR(jsonObject.getString("videoName"));
                    Log.e("videoUrl",jsonObject.getString("videoUrl"));
                    arInfo.setRemoteVideoUrl(jsonObject.getString("videoUrl"));
                    arInfos.add(arInfo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            Log.e("file","not exit");
            return null;
        }
        Log.e("file","arInfos exit="+arInfos.size());
        return arInfos;
    }
}
