package andrewl.videowall.UI.FrameLayouts;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import andrewl.videowall.DataBase.bmob.BmobHelper;
import andrewl.videowall.R;
import andrewl.videowall.Utils.FileUtils;

/**
 * Created by liut1 on 8/25/16.
 */
public class FrameGroupSelect extends Fragment{
    private View mView;
    private List<String> groupList;
    private Spinner mSpinner;
    private FileUtils fileUtils = new FileUtils().getInstance();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.frame_group_select, container, false);
        setPicThumbnail(fileUtils.getmImageFile());
        setVidThumbnail(fileUtils.getmVideoFile());
        initSpinner();
        return mView;
    }
    private void initSpinner(){
        groupList = new ArrayList<>();
        groupList.add("默认分组");
        mSpinner = (Spinner) mView.findViewById(R.id.group_spinner);
        new BmobHelper().acquireGroup(groupList);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, groupList);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(stringArrayAdapter);
//        spinner.setPrompt("ceshi");
        mSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        //设置默认值
        mSpinner.setVisibility(View.VISIBLE);
        stringArrayAdapter.notifyDataSetChanged();
    }
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//            view.setText("你的血型是："+m[arg2]);
            //TODO here need to save gender and age
//            myToast("spinner clicked");
            Log.e("SpinnerSelectedListener","selected:"+groupList.get(arg2));
        }

        public void onNothingSelected(AdapterView<?> arg0) {
            Log.e("onNothingSelected","selected:null");
        }
    }
    public String getSelectedGroup(){
        return mSpinner.getSelectedItem().toString();
    }
    public void setPicThumbnail(String imgPath){
        if(TextUtils.isEmpty(imgPath)){
            return;
        }
        Log.e("setPicThumbnail",imgPath);
        ImageView pic = (ImageView)mView.findViewById(R.id.pic_thumbnail);

        ViewGroup.LayoutParams para;
        para = pic.getLayoutParams();

        Bitmap bitmap = new FileUtils().getInstance().getImageThumbnail(imgPath,para.width,para.height);
        pic.setImageBitmap(bitmap);
    }
    public void setVidThumbnail(String vidPath){
        if(TextUtils.isEmpty(vidPath)){
            return;
        }
        Log.e("setVidThumbnail",vidPath);
        ImageView vid = (ImageView)mView.findViewById(R.id.vid_thumbnail);

        ViewGroup.LayoutParams para;
        para = vid.getLayoutParams();

        Bitmap bitmap = new FileUtils().getInstance().generateVideoThumbnail(vidPath,para.width,para.height, MediaStore.Images.Thumbnails.MICRO_KIND);
        vid.setImageBitmap(bitmap);
    }
}
