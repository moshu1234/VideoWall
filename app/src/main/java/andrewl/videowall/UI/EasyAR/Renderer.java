/**
* Copyright (c) 2015-2016 VisionStar Information Technology (Shanghai) Co., Ltd. All Rights Reserved.
* EasyAR is the registered trademark or trademark of VisionStar Information Technology (Shanghai) Co., Ltd in China
* and other countries for the augmented reality technology developed by VisionStar Information Technology (Shanghai) Co., Ltd.
*/

package andrewl.videowall.UI.EasyAR;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

import andrewl.videowall.UI.MainActivity;
import andrewl.videowall.UI.StartActivity;

public class Renderer implements GLSurfaceView.Renderer {
    private MyEasyAR myEasyAR = new MyEasyAR().getInstance();
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        myEasyAR.EasyARNativeInitGL();
    }

    public void onSurfaceChanged(GL10 gl, int w, int h) {
        myEasyAR.EasyARNativeResizeGL(w, h);
    }

    public void onDrawFrame(GL10 gl) {
        myEasyAR.EasyARNativeRender();
    }

}
