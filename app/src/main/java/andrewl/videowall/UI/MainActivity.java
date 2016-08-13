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
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import andrewl.videowall.DataBase.bmob.BmobHelper;
import andrewl.videowall.DataBase.bmob.BmobPerson;
import andrewl.videowall.DataBase.bmob.BmobPersonData;
import andrewl.videowall.DataBase.greendao.EventBusMessage;
import andrewl.videowall.DataBase.greendao.VideoWallHelper;
import andrewl.videowall.R;

import andrewl.videowall.UI.MyWidget.WidgetTopBar;

import andrewl.videowall.Utils.FileUtils;
import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.TabItemBuilder;
import me.majiajie.pagerbottomtabstrip.TabLayoutMode;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    private VideoWallHelper mVideoWallHelper;
    private Intent startIntent, connectIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new EventBusMessage(1,"init ui and data"));
        ActionBar actionBar = getSupportActionBar();//高版本可以换成 ActionBar actionBar = getActionBar();
        actionBar.hide();
        mVideoWallHelper = new VideoWallHelper().getInstance();
        mVideoWallHelper.initGreenDao(this,"T_VIDEOWALL");
//        BottomTabTest();
//        initTopbar();
//        initButtons();
//        initBmob();
        startIntent = new Intent(this,StartActivity.class);
        connectIntent = new Intent(this,VideoConnectActivity.class);
    }
    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
            case R.id.start:{
                startActivity(startIntent);

//                Toast.makeText(this, "第二个标题 右边按钮", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.connect:{
                startActivity(connectIntent);

                Toast.makeText(this, "connectIntent", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.management:{
//                startActivity(connectIntent);

                Toast.makeText(this, "management", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
    OnTabItemSelectListener listener = new OnTabItemSelectListener() {
        @Override
        public void onSelected(int index, Object tag)
        {
            Log.i("asd","onSelected:"+index+"   TAG: "+tag.toString());

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
//                .setSelectedIcon(R.drawable.pic)
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
//                .setMessageNumberColor(0xffffff)
//                .setDefaultColor(0xff0000)
//                .setMessageBackgroundColor(0xff0000)
                .setMode(TabLayoutMode.CHANGE_BACKGROUND_COLOR)
//                .setMode(TabLayoutMode.HIDE_TEXT| TabLayoutMode.CHANGE_BACKGROUND_COLOR)
                .build();

//        controller.setMessageNumber("A",2);
//        controller.setDisplayOval(0,true);

        controller.addTabItemClickListener(listener);
    }
    private void initButtons(){
        Button bt;
        bt = (Button)findViewById(R.id.start);
        bt.setOnClickListener(this);
        bt = (Button)findViewById(R.id.connect);
        bt.setOnClickListener(this);
        bt = (Button)findViewById(R.id.management);
        bt.setOnClickListener(this);
    }

    /*
    *1-init
    */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusMessage event) {
        switch (event.type){
            case 1:
                initTopbar();
                BottomTabTest();
                initButtons();
                new BmobHelper().initBmob(this);
                addBmobData();
                new FileUtils().getInstance().createVideoWallFolder();
                break;
        }
    }
    public void addBmobData(){
        BmobPerson bmobUser = new BmobPerson();
        bmobUser.setNickNmae("taotao");
        bmobUser.setAge(18);
        bmobUser.setBirthday("2016-01-01");
//        bmobUser.setLocalPicAddr("/1/2/1.png");
//        bmobUserData.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if(e == null){
//                    Log.e("bmobUserData.save done",s);
//                }else {
//                    Log.e("bmobUserData.save fail",e.toString());
//                }
//            }
//        });
//        BmobQuery<BmobPersonData> bmobQuery = new BmobQuery<BmobPersonData>();
//        bmobQuery.getObject("11111", new QueryListener<BmobPersonData>() {
//            @Override
//            public void done(BmobPersonData userData, BmobException e) {
//
//            }
//        });
    }
}
