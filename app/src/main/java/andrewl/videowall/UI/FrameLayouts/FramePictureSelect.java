package andrewl.videowall.UI.FrameLayouts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import andrewl.videowall.DataBase.bmob.BmobHelper;
import andrewl.videowall.DataBase.bmob.BmobPersonData;
import andrewl.videowall.DataBase.greendao.EventBusMessage;
import andrewl.videowall.DataBase.greendao.VideoWallHelper;
import andrewl.videowall.DataBase.greendao.WallData;
import andrewl.videowall.R;
import andrewl.videowall.Utils.FileUtils;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by liut1 on 8/9/16.
 */
public class FramePictureSelect extends Fragment implements View.OnClickListener{
    private View mView;
    private Uri mImageUri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.frame_picture_select, container, false);

        initButtons();
        return mView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select_picture:
                openPicture();
                break;
            case R.id.take_photo:
                openCamera();
                break;
        }
    }

    private void initButtons(){
        Button button = (Button)mView.findViewById(R.id.select_picture);
        button.setOnClickListener(this);
        button = (Button)mView.findViewById(R.id.take_photo);
        button.setOnClickListener(this);
    }
    private void openCamera(){
        // 指定照相机拍照后图片的存储路径，这里存储在自己定义的文件夹下
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            File photofile = new File(new FileUtils().getInstance().getVideoWallImageFolderPath());
            mImageUri = Uri.fromFile(new File(photofile, getHeadPictureName()));
            // 拍照我们用Action为MediaStore.ACTION_IMAGE_CAPTURE，
            // 有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            // 保存照片在自定义的文件夹下面
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
            startActivityForResult(intent, 1);

            //send to activty for saving path
            EventBus.getDefault().post(new EventBusMessage(11,new FileUtils().getInstance().convertUriToPath(getContext(),mImageUri)));

        } else {
//            UiUtil.showToast(context, "SD卡不可用");
            return;

        }
    }
    private void openPicture(){
        try {
            // 选择照片的时候也一样，我们用Action为Intent.ACTION_GET_CONTENT，
            // 有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 2);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public String getHeadPictureName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";

    }
}
