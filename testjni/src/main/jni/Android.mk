LOCAL_PATH := $(call my-dir)
$(warning $(Hello))

include $(CLEAR_VARS)
ifeq ($(TARGET_ARCH_ABI),armeabi-v7a)
    LOCAL_MODULE    := nativelib
    LOCAL_SRC_FILES := ./sharedlib/armeabi-v7a/libnative-lib.so
    include $(PREBUILT_SHARED_LIBRARY)
endif

include $(CLEAR_VARS)
ifeq ($(TARGET_ARCH_ABI),armeabi-v7a)
    LOCAL_MODULE    := plasma
    LOCAL_SRC_FILES := ./sharedlib/armeabi-v7a/libplasma.so
    include $(PREBUILT_SHARED_LIBRARY)
endif

include $(CLEAR_VARS)
ifeq ($(TARGET_ARCH_ABI),armeabi-v7a)
    LOCAL_MODULE    := eyematrix
    LOCAL_SRC_FILES := ./sharedlib/armeabi-v7a/libEyeMatrix.so
    include $(PREBUILT_SHARED_LIBRARY)
endif

include $(CLEAR_VARS)
ifeq ($(TARGET_ARCH_ABI),armeabi-v7a)
    LOCAL_MODULE    := esimageprocess
    LOCAL_SRC_FILES := ./sharedlib/armeabi-v7a/libESImageProcess.so
    include $(PREBUILT_SHARED_LIBRARY)
endif

include $(CLEAR_VARS)
ifeq ($(TARGET_ARCH_ABI),armeabi-v7a)
    LOCAL_MODULE    := eyematrixcore
    LOCAL_SRC_FILES := ./sharedlib/armeabi-v7a/libEyeMatrixCore.so
    include $(PREBUILT_SHARED_LIBRARY)
endif

include $(CLEAR_VARS)
ifeq ($(TARGET_ARCH_ABI),armeabi-v7a)
    LOCAL_MODULE    := json
    LOCAL_SRC_FILES := ./sharedlib/armeabi-v7a/libjsoncpp.so
    include $(PREBUILT_SHARED_LIBRARY)
endif

include $(CLEAR_VARS)
ifeq ($(TARGET_ARCH_ABI),armeabi-v7a)
    LOCAL_MODULE    := seetaface
    LOCAL_SRC_FILES := ./sharedlib/armeabi-v7a/libSeetaFace.so
    include $(PREBUILT_SHARED_LIBRARY)
endif

include $(CLEAR_VARS)
ifeq ($(TARGET_ARCH_ABI),armeabi-v7a)
    LOCAL_MODULE    := libsetirislog
    LOCAL_SRC_FILES := ./sharedlib/armeabi-v7a/libSetIrisLog.so
    include $(PREBUILT_SHARED_LIBRARY)
endif

include $(CLEAR_VARS)
LOCAL_MODULE    := androidEyeMatrix

LOCAL_SRC_FILES := main.cpp \
                   utils/UtilsBase.cpp \
                   base64/base_64.cpp


LOCAL_SHARED_LIBRARIES += eyematrix
LOCAL_SHARED_LIBRARIES += esimageprocess
LOCAL_SHARED_LIBRARIES += eyematrixcore
LOCAL_SHARED_LIBRARIES += nativelib
LOCAL_SHARED_LIBRARIES += plasma
LOCAL_SHARED_LIBRARIES += json


#LOCAL_CFLAGS += -DDEBUG
LOCAL_LDLIBS    +=  -L$(LOCAL_PATH)/sharedlib/armeabi-v7a -llog -ljnigraphics -lOpenSLES -landroid -lm

include $(BUILD_SHARED_LIBRARY)