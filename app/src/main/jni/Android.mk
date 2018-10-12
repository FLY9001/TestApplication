# 作用：指定源码编译的配置信息，如工作目录，编译模块的名称，参与编译的文件等

LOCAL_PATH := $(call my-dir)    #查找源文件路径

include $(CLEAR_VARS)           #

LOCAL_MODULE    := TestJni      #生成的so文件名：libTestJni.so
LOCAL_SRC_FILES := main.c       #源代码文件

include $(BUILD_SHARED_LIBRARY) #指定生成的静态库或者共享库在运行时依赖的共享库模块列表

# 注释掉了，不写会生成全部支持的平台。目前支持：
# APP_ABI := armeabi arm64-v8a armeabi-v7a mips mips64 x86 x86_64