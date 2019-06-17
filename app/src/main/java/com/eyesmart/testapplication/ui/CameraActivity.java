package com.eyesmart.testapplication.ui;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;

import com.eyesmart.testapplication.R;

/**
 * 相机预览，性能排名：SurfaceView > GLSurfaceView > TextureView（差距不大）
 */
public class CameraActivity extends AppCompatActivity implements Camera.PreviewCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
//        CameraSurfaceView cameraSurfaceView = new CameraSurfaceView(this, this, new CameraSurfaceView.CameraMonitorListener() {
//            @Override
//            public void cameraOpenSuccess(CameraSurfaceView cameraSurfaceView, int preWidth, int preHeight) {
//                cameraSurfaceView.setRotationY(180);
//            }
//
//            @Override
//            public void cameraOpenFail(String errorInfo) {
//
//            }
//        });
//        constraintLayout.addView(cameraSurfaceView);

        CameraTextureView cameraTextureView = new CameraTextureView(this, this, new CameraTextureView.CameraMonitorListener() {
            @Override
            public void cameraOpenSuccess(CameraTextureView cameraTextureView, int preWidth, int preHeight) {
                cameraTextureView.setRotationY(180);
            }

            @Override
            public void cameraOpenFail(String errorInfo) {

            }
        });
        constraintLayout.addView(cameraTextureView);
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        //camera.addCallbackBuffer(data);
    }
}
