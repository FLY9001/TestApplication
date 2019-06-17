package com.eyesmart.testapplication.android.opengl;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 *
 */
public class CameraGLSurfaceView extends GLSurfaceView {
    private static final String TAG = "CameraGLSurfaceView";

    private Camera mCamera;
    private int preWidth,preHeight;
    private SurfaceTexture mSurfaceTexture;
    private CameraDrawer mCameraDrawer;

    private Renderer mGLRenderer = new Renderer() {

        //当View的OpenGL环境被创建的时候调用
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            Log.d(TAG, "onSurfaceCreated: ");
            //设置清空屏幕用的颜色，即背景色（r,g,b,a）
            GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

            int textureID = createTextureID();
            mSurfaceTexture = new SurfaceTexture(textureID);
            mSurfaceTexture.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() {
                @Override
                public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                    CameraGLSurfaceView.this.requestRender();
                }
            });
            mCameraDrawer = new CameraDrawer(textureID);
            openCamera(0);
        }

        //当视图的几何形状发生变化（例如，当设备的屏幕方向改变时）时调用
        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            Log.d(TAG, "onSurfaceChanged: ");
            //设置视图的尺寸，可以用来渲染surface的大小。
            GLES20.glViewport(0, 0, width, height);
        }

        //每一次View的重绘都会调用
        @Override
        public void onDrawFrame(GL10 gl) {
            Log.d(TAG, "onDrawFrame: ");
            //清空屏幕，清空屏幕后调用glClearColor(）中设置的颜色填充屏幕
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

            float[] mtx = new float[16];
            mSurfaceTexture.getTransformMatrix(mtx);    //SurfaceTexture的关键方法
            mCameraDrawer.draw(mtx);                    //调用图形类的draw()方法
            mSurfaceTexture.updateTexImage();           //SurfaceTexture的关键方法
        }
    };

    public CameraGLSurfaceView(Context context) {
        super(context);
        init();
    }

    public CameraGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        SurfaceHolder holder = this.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.d(TAG, "surfaceCreated: ");
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.d(TAG, "surfaceChanged: ");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d(TAG, "surfaceDestroyed: ");
                releaseCamera();
            }
        });
        //设置OpenGL ES的版本为2
        setEGLContextClientVersion(2);
        //设置渲染器，负责真正绘制
        setRenderer(mGLRenderer);
        //RENDERMODE_CONTINUOUSLY模式会一直Render(一直调用onDrawFrame)，默认是此模式
        //RENDERMODE_WHEN_DIRTY当有数据时才rendered或主动调用GLSurfaceView的requestRender，适合camera预览
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    public void openCamera(int cameraId) {
        mCamera = Camera.open(cameraId);
        updateCameraParameters();
        try {
            mCamera.setPreviewTexture(mSurfaceTexture);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        mCamera.addCallbackBuffer(new byte[((preWidth * preHeight) * ImageFormat.getBitsPerPixel(ImageFormat.NV21)) / 8]);
//        setPreviewCallbackWithBuffer
        mCamera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                Log.d(TAG, "onPreviewFrame: ");
                //camera.addCallbackBuffer(data);
            }
        });
        mCamera.startPreview();
    }

    public void releaseCamera() {
        if (mCamera != null) {
            Log.d(TAG, "-------releaseCamera");
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    public void updateCameraParameters() {
        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> preSizes = parameters.getSupportedPreviewSizes();
        for (Camera.Size size : preSizes) {
            Log.d(TAG, "size-------" + size.width + " * " + size.height);
        }
        Camera.Size maxSize = getPreviewMaxSize(preSizes);
        preWidth = maxSize.width;
        preHeight = maxSize.height;
        parameters.setPreviewSize(maxSize.width, maxSize.height);
        Log.d(TAG, "预览尺寸取：" + parameters.getPreviewSize().width + " * " + parameters.getPreviewSize().height);
        int maxExposureCompensation = parameters.getMaxExposureCompensation();
        int minExposureCompensation = parameters.getMinExposureCompensation();
        Log.e(TAG, minExposureCompensation + " < " + "ExposureCompensation" + " < " + maxExposureCompensation);

        mCamera.setParameters(parameters);
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

    private int createTextureID() {
        int[] texture = new int[1];
        GLES20.glGenTextures(1, texture, 0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture[0]);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        return texture[0];
    }

}
