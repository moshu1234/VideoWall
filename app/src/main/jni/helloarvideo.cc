/**
* Copyright (c) 2015-2016 VisionStar Information Technology (Shanghai) Co., Ltd. All Rights Reserved.
* EasyAR is the registered trademark or trademark of VisionStar Information Technology (Shanghai) Co., Ltd in China
* and other countries for the augmented reality technology developed by VisionStar Information Technology (Shanghai) Co., Ltd.
*/

#include "ar.hpp"
#include <Android/log.h>
#include "renderer.hpp"
#include <jni.h>
#include <GLES2/gl2.h>

#define JNIFUNCTION_NATIVE(sig) Java_andrewl_videowall_UI_EasyAR_MyEasyAR_##sig

#define TAG "myDemo-jni" // 这个是自定义的LOG的标识
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__)// 定义LOGE类型

extern "C" {
    JNIEXPORT jboolean JNICALL JNIFUNCTION_NATIVE(nativeInit(JNIEnv* env, jobject object, jstring targetJson));
    JNIEXPORT jboolean JNICALL JNIFUNCTION_NATIVE(nativeReinit(JNIEnv* env, jobject object, jobjectArray arlist,jint arLength));
    JNIEXPORT void JNICALL JNIFUNCTION_NATIVE(nativeDestory(JNIEnv* env, jobject object));
    JNIEXPORT void JNICALL JNIFUNCTION_NATIVE(nativeInitGL(JNIEnv* env, jobject object));
    JNIEXPORT void JNICALL JNIFUNCTION_NATIVE(nativeResizeGL(JNIEnv* env, jobject object, jint w, jint h));
    JNIEXPORT void JNICALL JNIFUNCTION_NATIVE(nativeRender(JNIEnv* env, jobject obj));
    JNIEXPORT void JNICALL JNIFUNCTION_NATIVE(nativeRotationChange(JNIEnv* env, jobject obj, jboolean portrait));
};
#define MAXVIDEO 4
namespace EasyAR {
namespace samples {

class HelloARVideo : public AR
{
public:
    HelloARVideo();
    ~HelloARVideo();
    virtual void initGL();
    virtual void resizeGL(int width, int height);
    virtual void render();
    virtual bool clear();
private:
    Vec2I view_size;
    VideoRenderer* renderer[MAXVIDEO];
    int tracked_target;
    int active_target;
    int texid[MAXVIDEO];
    ARVideo* video;
    VideoRenderer* video_renderer;
};

HelloARVideo::HelloARVideo()
{
    view_size[0] = -1;
    tracked_target = 0;
    active_target = 0;
    for(int i = 0; i < MAXVIDEO; ++i) {
        texid[i] = 0;
        renderer[i] = new VideoRenderer;
    }
    video = NULL;
    video_renderer = NULL;
}

HelloARVideo::~HelloARVideo()
{
    for(int i = 0; i < MAXVIDEO; ++i) {
        delete renderer[i];
    }
}

void HelloARVideo::initGL()
{
    augmenter_ = Augmenter();
    augmenter_.attachCamera(camera_);
    for(int i = 0; i < MAXVIDEO; ++i) {
        renderer[i]->init();
        texid[i] = renderer[i]->texId();
    }
}

void HelloARVideo::resizeGL(int width, int height)
{
    view_size = Vec2I(width, height);
}

void HelloARVideo::render()
{

    glClearColor(0.f, 0.f, 0.f, 1.f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    Frame frame = augmenter_.newFrame();
    if(view_size[0] > 0){
        AR::resizeGL(view_size[0], view_size[1]);
        if(camera_ && camera_.isOpened())
            view_size[0] = -1;
    }
    augmenter_.setViewPort(viewport_);
    augmenter_.drawVideoBackground();
    glViewport(viewport_[0], viewport_[1], viewport_[2], viewport_[3]);

    AugmentedTarget::Status status = frame.targets()[0].status();
    if(status == AugmentedTarget::kTargetStatusTracked){
        int id = frame.targets()[0].target().id();
        if(active_target && active_target != id) {
            video->onLost();
            delete video;
            video = NULL;
            tracked_target = 0;
            active_target = 0;
        }
        if (!tracked_target) {
            if (video == NULL) {
                LOGE("frame.targets()[0].target().name()=%s,texid=%d",frame.targets()[0].target().name(),texid[0]);
                if(frame.targets()[0].target().name() == std::string("argame") && texid[0]) {
                    video = new ARVideo;
                    video->openVideoFile("video.mp4", texid[0]);
                    video_renderer = renderer[0];
                }
                else if(frame.targets()[0].target().name() == std::string("namecard") && texid[1]) {
                    video = new ARVideo;
                    video->openTransparentVideoFile("transparentvideo.mp4", texid[1]);
                    video_renderer = renderer[1];
                }
                else if(frame.targets()[0].target().name() == std::string("idback") && texid[3]) {
                    video = new ARVideo;
                    video->openStreamingVideo("http://bmob-cdn-5573.b0.upaiyun.com/2016/08/13/ef2eafc616f0485aa8b70bb2162f58e6.mp4", texid[3]);
                    video_renderer = renderer[3];
                }
                else if(frame.targets()[0].target().name() == std::string("4") && texid[2]) {
                    video = new ARVideo;
                    video->openStreamingVideo("http://10.41.18.119/1.mp4", texid[2]);
                    video_renderer = renderer[2];
                }
            }
            if (video) {
                video->onFound();
                tracked_target = id;
                active_target = id;
            }
        }
        Matrix44F projectionMatrix = getProjectionGL(camera_.cameraCalibration(), 0.2f, 500.f);
        Matrix44F cameraview = getPoseGL(frame.targets()[0].pose());
        ImageTarget target = frame.targets()[0].target().cast_dynamic<ImageTarget>();
        if(tracked_target) {
            video->update();
            video_renderer->render(projectionMatrix, cameraview, target.size());
        }
    } else {
        if (tracked_target) {
            video->onLost();
            tracked_target = 0;
        }
    }
}

bool HelloARVideo::clear()
{
    AR::clear();
    if(video){
        delete video;
        video = NULL;
        tracked_target = 0;
        active_target = 0;
    }
    return true;
}

}
}
EasyAR::samples::HelloARVideo ar;

typedef struct {
    char imageName[128];
    char imageURL[256];
    char videoURL[512];
}ARImage;

ARImage *images;
char * StringToChar(JNIEnv*env, jstring sin, char *sout,int len){
    if(sout == NULL){
        return NULL;
    }
    const char *str;
    str = env->GetStringUTFChars(sin,NULL);
    if(str == NULL)
    {//注释2
        return NULL;
    }
    strncpy(sout,str,strlen(str)>len?len:strlen(str));

//    LOGE("StringToChar start, str=%s,sout=%s",str,sout);
    env->ReleaseStringUTFChars(sin, str);//注释3
    return sout;
}
JNIEXPORT jboolean JNICALL JNIFUNCTION_NATIVE(nativeInit(JNIEnv* env, jobject, jstring targetJson))
{

    bool status = ar.initCamera();

    char str[128] = {0};
    StringToChar(env,targetJson,str,sizeof(str));
    LOGE("nativeInit start, str=%s",str);

    ar.loadAllFromJsonFile(str);
    ar.loadFromImage("namecard.jpg","namecard");
//    ar.loadFromImage("http://bmob-cdn-5573.b0.upaiyun.com/2016/08/13/6f6ef0fd63354799b382d9763eb3456e.jpg");
    status &= ar.start();

    return status;
}

JNIEXPORT void JNICALL JNIFUNCTION_NATIVE(nativeDestory(JNIEnv*, jobject))
{
    ar.clear();
}
int objReturnInt(JNIEnv* env, jobject obj,char *methos, char*paras){
    //class ArrayList
    jclass cls_arraylist = env->GetObjectClass(obj);
    //method in class ArrayList
    jmethodID arraylist_size = env->GetMethodID(cls_arraylist,methos,paras);
    jint size = env->CallIntMethod(obj,arraylist_size);

//    LOGE("objSize start, size=%d",size);
    return size;
}
jstring objReturnString(JNIEnv* env, jobject obj,char *methos, char*paras){
    //class ArrayList
    jclass cls_arraylist = env->GetObjectClass(obj);
    //method in class ArrayList
    jmethodID arraylist_get = env->GetMethodID(cls_arraylist,methos,paras);
    jstring str = (jstring)env->CallObjectMethod(obj,arraylist_get);

    return str;
}
JNIEXPORT jboolean JNICALL JNIFUNCTION_NATIVE(nativeReinit(JNIEnv* env, jobject, jobjectArray arlist,jint arLength))
{
    LOGE("nativeReinit start, arLenght=%d",arLength);

    jclass clazz ;
    jclass listClass;
    jint length = objReturnInt(env,arlist,"size","()I");
    LOGE("nativeReinit start, arLenght=%d,get length=%d",arLength,length);

    listClass = env->GetObjectClass(arlist);
    LOGE("nativeReinit start, i=%d",0);
    jmethodID method_get = env->GetMethodID(listClass,"get","(I)Ljava/lang/Object;");
    LOGE("nativeReinit start, i=%d",1);

//    char url[512] = {0};
//    char name[512] = {0};
    if(length > 0){
        images = (ARImage*)malloc(sizeof(ARImage)*length);
    }
    for(int i=0;i<arLength;i++){
        jobject info = env->CallObjectMethod(arlist,method_get,i);
        jstring javaNameStr = objReturnString(env,info,"getLocalImgAddr","()Ljava/lang/String;");
        StringToChar(env,javaNameStr,images[i].imageName,sizeof(images[i].imageName));

        jstring javaUrlStr = objReturnString(env,info,"getRemoteImagUrl","()Ljava/lang/String;");
        StringToChar(env,javaUrlStr,images[i].imageURL,sizeof(images[i].imageURL));

        jstring javaVideoUrlStr = objReturnString(env,info,"getRemoteVideoUrl","()Ljava/lang/String;");
        StringToChar(env,javaVideoUrlStr,images[i].videoURL,sizeof(images[i].videoURL));

        LOGE("loadFromImage, imageName=%s,url=%s,video=%s",images[i].imageName,images[i].imageURL,images[i].videoURL);
//        ar.loadFromImage(images[i].imageURL,images[i].imageName);
        ar.loadFromImage(images[i].imageURL,"idback");
//        value = env->GetObjectArrayElement((jobjectArray)obj,1);
//        const char * imageAddr = env->GetStringUTFChars((jstring)value,0);
//        LOGE("nativeReinit start, imageAddr=%s",imageAddr);
//
//        value = env->GetObjectArrayElement((jobjectArray)obj,2);
//        const char * videoName = env->GetStringUTFChars((jstring)value,0);
//        LOGE("nativeReinit start, videoName=%s",videoName);
//
//        value = env->GetObjectArrayElement((jobjectArray)obj,3);
//        const char * videoUrl = env->GetStringUTFChars((jstring)value,0);
//        LOGE("nativeReinit start, videoUrl=%s",videoUrl);
    }
    bool status = true;
//    ar.clear();
//    status &= ar.stop();
    ar.loadAllFromJsonFile("targets1.json");
    ar.loadFromImage("namecard.jpg","namecard");
    status &= ar.start();

    return status;
}
JNIEXPORT void JNICALL JNIFUNCTION_NATIVE(nativeInitGL(JNIEnv*, jobject))
{
    ar.initGL();
}

JNIEXPORT void JNICALL JNIFUNCTION_NATIVE(nativeResizeGL(JNIEnv*, jobject, jint w, jint h))
{
    ar.resizeGL(w, h);
}

JNIEXPORT void JNICALL JNIFUNCTION_NATIVE(nativeRender(JNIEnv*, jobject))
{
    ar.render();
}

JNIEXPORT void JNICALL JNIFUNCTION_NATIVE(nativeRotationChange(JNIEnv*, jobject, jboolean portrait))
{
    ar.setPortrait(portrait);
}
