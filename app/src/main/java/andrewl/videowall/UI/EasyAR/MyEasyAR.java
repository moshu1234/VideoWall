package andrewl.videowall.UI.EasyAR;

import android.app.Activity;

import cn.easyar.engine.EasyAR;

/**
 * Created by liut1 on 8/9/16.
 */
public class MyEasyAR {
    static String key = "NreooynJ7BdpePxmoQfi9ykO4khtOHyHMHgov5k77XnUNDRGdNhvizxlcbcmhJsNDlDPgsiL9cgwHjqOL8LsRAPuwjFjATplOxTG67ef61a05502f76daf239723dae099c9AAJA6GnqRdz8vUfMjuCT23Q5yoUsbNdbJFfmx7ZA8hIt6OYiITAPbyJ0YGERhoiTXfsZ";

    static {
        System.loadLibrary("EasyAR");
        System.loadLibrary("HelloARVideoNative");
    }
    private MyEasyAR instance;
    private native void nativeInitGL();
    private native void nativeResizeGL(int w, int h);
    private native void nativeRender();
    private native boolean nativeInit();
    private native boolean nativeReinit();
    private native void nativeDestory();
    private native void nativeRotationChange(boolean portrait);
    public MyEasyAR getInstance(){
        if(instance == null){
            synchronized (MyEasyAR.class){
                if(instance == null){
                    instance = new MyEasyAR();
                }
            }
        }
        return instance;
    }
    public void MyEasyARInit(Activity activity){
//        EasyAR.initialize(activity, key);
    }
    public void MyEasyARPause(){
        EasyAR.onPause();
    }
    public void MyEasyARResume(){
        EasyAR.onResume();
    }
    public void EasyARNativeInitGL(){
        nativeInitGL();
    }
    public void EasyARNativeResizeGL(int w, int h){
        nativeResizeGL(w, h);
    }
    public void EasyARNativeRender(){
        nativeRender();
    }
    public boolean EasyARNativeInit(){
        return nativeInit();
    }
    public boolean EasyARNativeReinit(){
        return nativeReinit();
    }
    public void EasyARNativeDestory(){
        nativeDestory();
    }
    public void EasyARNativeRotationChange(boolean portrait){
        nativeRotationChange(portrait);
    }

}
