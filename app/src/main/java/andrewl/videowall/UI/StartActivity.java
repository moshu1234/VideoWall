package andrewl.videowall.UI;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import andrewl.videowall.DataBase.UserInfo.ARInfo;
import andrewl.videowall.DataBase.greendao.VideoWallHelper;
import andrewl.videowall.R;
import andrewl.videowall.UI.EasyAR.GLView;
import andrewl.videowall.UI.EasyAR.MyEasyAR;
import andrewl.videowall.UI.EasyAR.Renderer;
import andrewl.videowall.UI.MyWidget.WidgetTopBar;
import andrewl.videowall.Utils.ARJson;
import cn.easyar.engine.EasyAR;
import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.TabItemBuilder;
import me.majiajie.pagerbottomtabstrip.TabLayoutMode;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

public class StartActivity extends AppCompatActivity  implements View.OnClickListener {
    private MyEasyAR mEasyAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ActionBar actionBar = getSupportActionBar();//高版本可以换成 ActionBar actionBar = getActionBar();
        actionBar.hide();
        BottomTabTest();
        initTopbar();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initEasyAR();
    }
    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        mEasyAR.EasyARNativeRotationChange(getWindowManager().getDefaultDisplay().getRotation() == android.view.Surface.ROTATION_0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEasyAR.EasyARNativeDestory();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mEasyAR.MyEasyARResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mEasyAR.MyEasyARPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            Log.e("asd","onSelected:"+index+"   TAG: "+tag.toString());
            if(index == 1) {
                ArrayList<ARInfo> arInfos = new ARJson().parseARJson("default");
                Log.e("====","arinfos sieze="+arInfos.size());
                mEasyAR.EasyARNativeReinit(arInfos,arInfos.size());
            }
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            //transaction.setCustomAnimations(R.anim.push_up_in,R.anim.push_up_out);
//            transaction.replace(R.id.frameLayout,mFragments.get(index));
//            transaction.commit();
        }

        @Override
        public void onRepeatClick(int index, Object tag) {
            Log.i("asd","onRepeatClick:"+index+"   TAG: "+tag.toString());
        }
    };
    private void BottomTabTest()
    {
        Controller controller;
//    int[] testColors = {0xFF7BA3A8,0xFFF4F3DE,0xFFBEAD92,0xFFF35A4A,0xFF5B4947};
        int[] testColors = {0xFF00796B,0xFF00796B,0xFF00796B,0xFF00796B,0xFF00796B};
//        int[] testColors = {0xFF00796B,0xFF5B4947,0xFF607D8B,0xFFF57C00,0xFFF57C00};
        PagerBottomTabLayout pagerBottomTabLayout = (PagerBottomTabLayout) findViewById(R.id.tab);

        //用TabItemBuilder构建一个导航按钮
        TabItemBuilder tabItemBuilder = new TabItemBuilder(this).create()
                .setDefaultIcon(android.R.drawable.ic_menu_send)
                .setText("信息")
                .setSelectedColor(testColors[0])
                .setTag("A")
                .build();

        //构建导航栏,得到Controller进行后续控制
        controller = pagerBottomTabLayout.builder()
                .addTabItem(tabItemBuilder)
                .addTabItem(android.R.drawable.ic_menu_compass, "位置",testColors[1])
                .addTabItem(android.R.drawable.ic_menu_search, "搜索",testColors[2])
                .addTabItem(android.R.drawable.ic_menu_help, "帮助",testColors[3])
//                .setMode(TabLayoutMode.HIDE_TEXT)
                .setMode(TabLayoutMode.CHANGE_BACKGROUND_COLOR)
//                .setMode(TabLayoutMode.HIDE_TEXT| TabLayoutMode.CHANGE_BACKGROUND_COLOR)
                .build();

//        controller.setMessageNumber("A",2);
//        controller.setDisplayOval(0,true);

        controller.addTabItemClickListener(listener);
    }
    private void initEasyAR(){
        mEasyAR = new MyEasyAR().getInstance();
        mEasyAR.MyEasyARInit(this);
        String key = "NreooynJ7BdpePxmoQfi9ykO4khtOHyHMHgov5k77XnUNDRGdNhvizxlcbcmhJsNDlDPgsiL9cgwHjqOL8LsRAPuwjFjATplOxTG67ef61a05502f76daf239723dae099c9AAJA6GnqRdz8vUfMjuCT23Q5yoUsbNdbJFfmx7ZA8hIt6OYiITAPbyJ0YGERhoiTXfsZ";

        EasyAR.initialize(this, key);
        mEasyAR.EasyARNativeInit("targets.json");

        GLView glView = new GLView(this);
        glView.setRenderer(new Renderer());
        glView.setZOrderMediaOverlay(true);

        ((ViewGroup) findViewById(R.id.preview)).addView(glView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mEasyAR.EasyARNativeRotationChange(getWindowManager().getDefaultDisplay().getRotation() == android.view.Surface.ROTATION_0);
    }
}
