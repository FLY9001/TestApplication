//
// Created by FLY on 2017/8/23.
//
#include "com_eyesmart_testapplication_android_TestJni.h"

JNIEXPORT jint JNICALL Java_com_eyesmart_testapplication_android_TestJni_add
  (JNIEnv *env, jobject jobject0, jint jint0, jint jint1){
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
  }

