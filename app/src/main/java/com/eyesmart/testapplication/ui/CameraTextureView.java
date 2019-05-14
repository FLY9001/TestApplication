package com.eyesmart.testapplication.ui;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.util.Log;
import android.view.TextureView;

import java.util.List;

/**
 * https://www.jianshu.com/p/bac0e72351e4
 */
public class CameraTextureView extends TextureView {
    private static final String TAG = "CameraTextureView";

    private CameraMonitorListener mCameraMonitorListener;
    private PreviewCallback mPreviewCallback;
    private Camera mCamera;
    private SurfaceTexture mSurfaceTexture;
    private int preWidth, preHeight;

    SurfaceTextureListener mSurfaceTextureListener = new SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            Log.i(TAG, "onSurfaceTextureAvailable: " + width + " * " + height);
            mSurfaceTexture = surface;
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            Log.i(TAG, "onSurfaceTextureSizeChanged: " + width + " * " + height);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            Log.i(TAG, "onSurfaceTextureDestroyed: ");
            releaseCamera();
            surface.release();
            mSurfaceTexture = null;
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            Log.i(TAG, "onSurfaceTextureUpdated: ");
        }
    };

    public CameraTextureView(Context context, PreviewCallback previewCallback, CameraMonitorListener cameraMonitorListener) {
        super(context);
        mPreviewCallback = previewCallback;
        mCameraMonitorListener = cameraMonitorListener;
        init();
    }

    /**
     * 初始化工作
     */
    private void init() {
        Log.i(TAG, "init: ");
        this.setSurfaceTextureListener(mSurfaceTextureListener);
    }

    private void openCamera() {
        Log.i(TAG, "openCamera: ");
        int cameraId = 0;
        mCamera = Camera.open(cameraId);
        //setCameraDisplayOrientation(cameraId, mCamera);
        try {
            updateCameraParameters();
            mCamera.setPreviewTexture(mSurfaceTexture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mCamera.addCallbackBuffer(new byte[((preWidth * preHeight) * ImageFormat.getBitsPerPixel(ImageFormat.NV21)) / 8]);
        mCamera.setPreviewCallbackWithBuffer(mPreviewCallback);
        mCamera.startPreview();

        mCameraMonitorListener.cameraOpenSuccess(this, preWidth, preHeight);
    }

    public void updateCameraParameters() throws Exception {
        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> preSizes = parameters.getSupportedPreviewSizes();
        for (Camera.Size size : preSizes) {
            Log.d(TAG, "size-------" + size.width + " * " + size.height);
        }
        Camera.Size maxSize = getPreviewMaxSize(preSizes);
        preWidth = maxSize.width;
        preHeight = maxSize.height;
        parameters.setPreviewSize(preWidth, preHeight);
        Log.d(TAG, "预览尺寸取：" + parameters.getPreviewSize().width + " * " + parameters.getPreviewSize().height);
        int maxExposureCompensation = parameters.getMaxExposureCompensation();
        int minExposureCompensation = parameters.getMinExposureCompensation();
        Log.e(TAG, minExposureCompensation + " < " + "ExposureCompensation" + " < " + maxExposureCompensation);

        mCamera.setParameters(parameters);
    }

    private void setCameraDisplayOrientation(int paramInt, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(paramInt, info);
        int degrees = 0;
        //int orientionOfCamera = info.orientation; // 获得摄像头的安装旋转角度
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else { // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        // System.out.println("---------- result====="+result);
        camera.setDisplayOrientation(result); // 注意前后置的处理，前置是映象画面，该段是SDK文档的标准DEMO
    }

    private static Camera.Size getPreviewMaxSize(List<Camera.Size> sizes) {
        Camera.Size maxSize = null;
        if (sizes != null) {
            maxSize = sizes.get(0);
            for (Camera.Size size : sizes) {
                Log.d(TAG, "------- size.width===" + size.width + ",size.height===" + size.height);
                if (size.width * size.height > maxSize.width * maxSize.height) {
                    maxSize = size;
                }
            }
        }
        return maxSize;
    }

    public void releaseCamera() {
        if (mCamera != null) {
            Log.d(TAG, "-------releaseCamera");
            mCamera.setPreviewCallbackWithBuffer(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * 相机状态接口,CameraPreviewView的持有者需要注册此接口。目的是通知持有者相机开启成功你可以做有关相机的其它设置了
     *
     * @author Mr.lu
     */
    public interface CameraMonitorListener {
        void cameraOpenSuccess(CameraTextureView cameraTextureView, int preWidth, int preHeight);

        void cameraOpenFail(String errorInfo);

    }

    public Camera getUsingCamera() {
        return mCamera;
    }
}
