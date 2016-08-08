#ifndef __MULTI_TRACE_H__
#define __MULTI_TRACE_H__
#ifndef ANDROID_NDK_BUILD
#define ANDROID_NDK_BUILD 1
#endif
#ifdef ANDROID_NDK_BUILD
#define LOG_TAG "NATIVE NDK INFO"

#include <Android/log.h> // only use in NDK

#define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__))
#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__))
#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__))
#define LOGW(...) ((void)__android_log_print(ANDROID_LOG_WARN, LOG_TAG, __VA_ARGS__))
#elif defined ANDROID_SOURCE_BUILD
#define LOG_TAG "NATIVE SRC INFO"

extern "C" {
#include <cutils/log.h> // only use in ANDROID SOURCE
}

#define LOGE(...) LOG(LOG_ERROR, LOG_TAG, __VA_ARGS__)
#define LOGD(...) LOG(LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) LOG(LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGW(...) LOG(LOG_WARN, LOG_TAG, __VA_ARGS__)
#elif defined IOS_PLATFORM_BUILD
#define LOGE printf
#define LOGD printf
#define LOGI printf
#define LOGW printf
#elif defined WP_PLATFORM_BUILD
#define LOG_TAG "NATIVE WINDOWS PHONE"
#define LOG_ERROR "ERROR"
#define LOG_DEBUG "DEBUG"
#define LOG_INFOR "INFOR"
#define LOG_WARN "WARN"

void LOG(const char* status, const char* tag, const char* format, ...);

#define LOGE(...) LOG(LOG_ERROR, LOG_TAG, __VA_ARGS__)
#define LOGD(...) LOG(LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) LOG(LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGW(...) LOG(LOG_WARN, LOG_TAG, __VA_ARGS__)
#else
#define LOGE printf
#define LOGD printf
#define LOGI printf
#define LOGW printf
#endif

#endif