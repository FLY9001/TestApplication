#include "com_eyesmart_testapplication_android_TestJni.h"
/*
 * Class:     io_github_yanbober_ndkapplication_NdkJniUtils
 * Method:    getCLanguageString
 * Signature: ()Ljava/lang/String;
 // 1. JNIEnv：代表了VM里面的环境，本地的代码可以通过该参数与Java代码进行操作
 // 2. jobject：定义JNI方法的类的一个本地引用（this）
 */
JNIEXPORT jstring JNICALL Java_com_eyesmart_testapplication_android_TestJni_sayHello
  (JNIEnv *env, jobject obj){

     return (*env)->NewStringUTF(env,"This just a test for Android Studio NDK JNI developer!");
  }
