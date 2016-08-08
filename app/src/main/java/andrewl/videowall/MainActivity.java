package andrewl.videowall;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;

import andrewl.videowall.DataBase.greendao.VideoWallHelper;
import andrewl.videowall.UI.GLView;
import andrewl.videowall.UI.Renderer;
import cn.easyar.engine.EasyAR;

public class MainActivity extends AppCompatActivity {
    private VideoWallHelper mVideoWallHelper;
    static String key = "2P0RAiTyyHKRzSFXFVJa6A8B7yr7J60S0f9NSXsuY4xMPyP8pYwuq7nw4cklfJBOWc8djW086TPTHS1bxQbIGyw4GQoAX1BqURNK0730bcff48994f078af6f669f29a4904a9HNqd9BEIXAxjqQM2o46zQhDIyowT3JRtJth2CyjiE38RjbRnpG4QXlJC7oT1yHoEsW";

    static {
        System.loadLibrary("EasyAR");
        System.loadLibrary("HelloARVideoNative");
    }

    public static native void nativeInitGL();
    public static native void nativeResizeGL(int w, int h);
    public static native void nativeRender();
    private native boolean nativeInit();
    private native boolean nativeReinit();
    private native void nativeDestory();
    private native void nativeRotationChange(boolean portrait);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mVideoWallHelper = new VideoWallHelper().getInstance();
        mVideoWallHelper.initGreenDao(this,"T_VIDEOWALL");
        EasyAR.initialize(this, key);
        nativeInit();

        GLView glView = new GLView(this);
        glView.setRenderer(new Renderer());
        glView.setZOrderMediaOverlay(true);

        ((ViewGroup) findViewById(R.id.preview)).addView(glView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        nativeRotationChange(getWindowManager().getDefaultDisplay().getRotation() == android.view.Surface.ROTATION_0);
    }
    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        nativeRotationChange(getWindowManager().getDefaultDisplay().getRotation() == android.view.Surface.ROTATION_0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        nativeDestory();
    }
    @Override
    protected void onResume() {
        super.onResume();
        EasyAR.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EasyAR.onPause();
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
}
