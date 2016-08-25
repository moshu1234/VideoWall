package andrewl.videowall.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;

import andrewl.videowall.DataBase.bmob.BmobHelper;
import andrewl.videowall.DataBase.greendao.EventBusMessage;
import andrewl.videowall.R;
import andrewl.videowall.UI.FrameLayouts.FrameGroupSelect;
import andrewl.videowall.UI.FrameLayouts.FramePictureSelect;
import andrewl.videowall.UI.FrameLayouts.FrameVideoConnectIntroduce;
import andrewl.videowall.UI.FrameLayouts.FrameVideoSelect;
import andrewl.videowall.UI.MyWidget.WidgetTopBar;
import andrewl.videowall.Utils.FileUtils;
import andrewl.videowall.Utils.ProgressUtils;
import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.TabItemBuilder;
import me.majiajie.pagerbottomtabstrip.TabLayoutMode;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

public class VideoConnectActivity extends AppCompatActivity  implements View.OnClickListener  {
    private List<Fragment> mFragments;
    private FrameVideoConnectIntroduce introduceFragment;
    private FramePictureSelect picSelectFragment;
    private FrameVideoSelect vidSelectFragment;
    private FrameGroupSelect groupSelectFragment;
    private Integer currentFragment;
    private TabItemBuilder mTabItemBuilder1;
    private TabItemBuilder mTabItemBuilder2;

    private String[] mPath = new String[2];
    private BmobHelper mBmobHelper;
    private ProgressUtils progressUtils;
    private FileUtils fileUtils = new FileUtils().getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_connect);
        EventBus.getDefault().register(this);
        ActionBar actionBar = getSupportActionBar();//高版本可以换成 ActionBar actionBar = getActionBar();
        actionBar.hide();
        initTopbar();
        BottomTabTest();
        initFrameLayouts();
        mBmobHelper = new BmobHelper();
    }
    @Override
    protected void onDestroy(){
        Log.e("on destroy","aha");
        super.onDestroy();
    }
    private void initFrameLayouts(){
        mFragments = new ArrayList<>();

        introduceFragment = new FrameVideoConnectIntroduce();
        picSelectFragment = new FramePictureSelect();
        vidSelectFragment = new FrameVideoSelect();
        groupSelectFragment = new FrameGroupSelect();


//        mFragments.add(introduceFragment);
        mFragments.add(picSelectFragment);
        mFragments.add(vidSelectFragment);
        mFragments.add(groupSelectFragment);
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        currentFragment = 0;
        // transaction.setCustomAnimations(R.anim.push_up_in,R.anim.push_up_out);
        transaction.add(R.id.frameLayout,mFragments.get(currentFragment));

        transaction.commit();
    }
    private void initTopbar(){
        WidgetTopBar wtbOne = (WidgetTopBar) findViewById(R.id.topbar);
        wtbOne.getLeftBtnImage().setOnClickListener(this);
        wtbOne.getRightBtnImage().setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_left_top_bar: {
                Toast.makeText(this, "第二个标题 左边按钮", Toast.LENGTH_SHORT).show();
                finish();
                break;
            }
            case R.id.ib_right_top_bar: {
                Toast.makeText(this, "第一个标题 右边按钮", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btn_left_top_bar: {
                Toast.makeText(this, "第一个标题 左边按钮", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btn_right_top_bar: {
                Toast.makeText(this, "第二个标题 右边按钮", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    OnTabItemSelectListener listener = new OnTabItemSelectListener() {
        @Override
        public void onSelected(int index, Object tag)
        {
            Log.i("asd","onSelected:"+index+"   TAG: "+tag.toString());
            BottomClickAction(index);
        }

        @Override
        public void onRepeatClick(int index, Object tag) {
            Log.i("asd","onRepeatClick:"+index+"   TAG: "+tag.toString());
            BottomClickAction(index);
        }
    };
    private void BottomClickAction(int index){
        if(index == 0){
            //上一步
            if(currentFragment > 0){
                --currentFragment;
            }else {
                //TODO:goto main activity
            }
        }else {
            //下一步
            if(currentFragment == (mFragments.size()-1)){

                if(TextUtils.isEmpty(mPath[0])){
                    Toast.makeText(this,"请选择或拍摄图片",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mPath[1])){
                    Toast.makeText(this,"请选择或拍摄视频",Toast.LENGTH_SHORT).show();
                    return;
                }
                //TODO:show progress bar, and then goto main fragment
                progressUtils = new ProgressUtils(this);
//                mThreadUtils = new ThreadUtils(100);
//                mThreadUtils.start();
                uploadToBmob(groupSelectFragment.getSelectedGroup());
                return;
            }
            if(currentFragment < 2){
                ++currentFragment;
            }else {
                //TODO:connect complete,goto main activity
//                vidSelectFragment.setPicThumbnail(mPath[0]);
            }
        }
        Log.i("====","currentFragment="+currentFragment);
        switch (currentFragment) {
            case 2:
                //last page, change the 下一步 as 完成
                Log.i("====","switch="+2);
                mTabItemBuilder1.getTabItemBuild().setText("上一步");
                mTabItemBuilder2.getTabItemBuild().setText("完  成");
                break;
            case 0:
                Log.i("====","switch="+0);
                mTabItemBuilder1.getTabItemBuild().setText("返  回");
                mTabItemBuilder2.getTabItemBuild().setText("下一步");
                break;
            default:
                Log.i("====","switch="+1);
                mTabItemBuilder1.getTabItemBuild().setText("上一步");
                mTabItemBuilder2.getTabItemBuild().setText("下一步");
                break;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.setCustomAnimations(R.anim.push_up_in,R.anim.push_up_out);
        transaction.replace(R.id.frameLayout,mFragments.get(currentFragment));
        transaction.commit();
//        if(currentFragment == 2){
//            vidSelectFragment.setPicThumbnail(mPath[0]);
//        }
    }
    private void BottomGoToFistFrame(){
        currentFragment = 0;
        mTabItemBuilder1.getTabItemBuild().setText("返  回");
        mTabItemBuilder2.getTabItemBuild().setText("下一步");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.setCustomAnimations(R.anim.push_up_in,R.anim.push_up_out);
        transaction.replace(R.id.frameLayout,mFragments.get(currentFragment));
        transaction.commit();
    }
    private void BottomTabTest()
    {
        Controller controller;
//    int[] testColors = {0xFF7BA3A8,0xFFF4F3DE,0xFFBEAD92,0xFFF35A4A,0xFF5B4947};
        int[] testColors = {0xFF00796B,0xFF00796B,0xFF00796B,0xFF00796B,0xFF00796B};
//        int[] testColors = {0xFF00796B,0xFF5B4947,0xFF607D8B,0xFFF57C00,0xFFF57C00};
        PagerBottomTabLayout pagerBottomTabLayout = (PagerBottomTabLayout) findViewById(R.id.tab);

        //用TabItemBuilder构建一个导航按钮
        mTabItemBuilder1 = new TabItemBuilder(this).create()
                .setDefaultIcon(android.R.drawable.ic_menu_send)
                .setText("返  回")
                .setSelectedColor(testColors[0])
                .setTag("A")
                .build();

        mTabItemBuilder2 = new TabItemBuilder(this).create()
                .setDefaultIcon(android.R.drawable.ic_menu_search)
                .setText("下一步")
                .setSelectedColor(testColors[1])
                .setTag("B")
                .build();
        //构建导航栏,得到Controller进行后续控制
        controller = pagerBottomTabLayout.builder()
                .addTabItem(mTabItemBuilder1)
                .addTabItem(mTabItemBuilder2)
//                .addTabItem(android.R.drawable.ic_menu_search, "下一步",testColors[1])
//                .setMode(TabLayoutMode.HIDE_TEXT)
                .setMode(TabLayoutMode.CHANGE_BACKGROUND_COLOR)
//                .setMode(TabLayoutMode.HIDE_TEXT| TabLayoutMode.CHANGE_BACKGROUND_COLOR)
                .build();


        controller.addTabItemClickListener(listener);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("onActivityResult",requestCode+":"+resultCode);
        Intent intent = new Intent("com.android.camera.action.CROP");
        Uri uri = null;
        String path;
        //如果返回的是拍照上传
        if (data == null) {
//            uri = imageUri;
        } //返回的是图库上传
        else {
            uri = data.getData();
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode&0xf) {
                case 1:
//                    mBmobHelper.updatePersonImage(mPath[0]);
                    Log.e("====camera=pic===", "onActivityResult:success");

                    break;
                case 2:
                    Log.e("====select==pic==", "onActivityResult:success");
                    path = new FileUtils().getInstance().convertUriToPath(this,uri);
                    EventBus.getDefault().post(new EventBusMessage(11,path));
                    break;
                case 3:
                    Log.e("====camera==video==", "onActivityResult:success");
                    break;
                case 4:
                    Log.e("====select==video==", "onActivityResult:success");
                    path = new FileUtils().getInstance().convertUriToPath(this,uri);
                    EventBus.getDefault().post(new EventBusMessage(12,path));

                    break;
                default:
                    break;
            }
        }
    }
    private void uploadToBmob(String group){
        //group button onlistener
        Log.e("mpath","0:"+mPath[0]);
        Log.e("mpath","1:"+mPath[1]);
        mBmobHelper.updatePersonData(mPath,group);
    }
    /*
    * 11-save pic
    * 12-save video
    * 13-save objid
    * */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusMessage event) {
        switch (event.type){
            case 11:
                //TODO:save local photo to 1
//                mPath[0] = event.message;
                mPath[0] = fileUtils.compressPicture(event.message);
                if(currentFragment != 0)//1 image select frame
                {
                    break;
                }
                Log.e("======","select photo success:"+event.message);
                handler.sendEmptyMessage(1);

                fileUtils.setmImageFile(event.message);
                break;
            case 12:
                Log.e("======","select video success:"+event.message);
                mPath[1] = event.message;
                if(currentFragment != 1)//1 image select frame
                {
                    break;
                }
                Log.e("======","select photo success:"+event.message);
                handler.sendEmptyMessage(1);
                fileUtils.setmVideoFile(event.message);
                vidSelectFragment.setVidThumbnail(event.message);
                break;
            case 16:
                progressUtils.dismissProgressBar();
                if(event.message.equals("success")){
                    handler.sendEmptyMessage(0);
                }
                break;
            case 17://update progress
                progressUtils.setProgressMessage(event.message);
                break;
        }
    }
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.e("====","handleMessage:"+msg.what);
            if(msg.what == 1){
                BottomClickAction(1);//next step
            }else {
                BottomGoToFistFrame();
            }
            return false;
        }
    });
}
