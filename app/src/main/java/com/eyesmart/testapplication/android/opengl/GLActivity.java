package com.eyesmart.testapplication.android.opengl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.eyesmart.testapplication.R;

public class GLActivity extends AppCompatActivity {
    CameraGLSurfaceView cameraGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gl);
        cameraGLSurfaceView = findViewById(R.id.cameraGLSurfaceView);
    }
}
