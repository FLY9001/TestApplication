package com.eyesmart.testapplication.android.opengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 *
 */
public class CameraGLSurfaceView extends GLSurfaceView {
    private static final String TAG = "TestGLSurfaceView";


    private Renderer mGLRenderer = new Renderer() {

        //当View的OpenGL环境被创建的时候调用
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            Log.d(TAG, "onSurfaceCreated: ");
            //设置清空屏幕用的颜色，即背景色（r,g,b,a）
            //GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        }

        //当视图的几何形状发生变化（例如，当设备的屏幕方向改变时）时调用
        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            Log.d(TAG, "onSurfaceChanged: ");
            //设置视图的尺寸，可以用来渲染surface的大小。
            //GLES20.glViewport(0, 0, width, height);
        }

        //每一次View的重绘都会调用
        @Override
        public void onDrawFrame(GL10 gl) {
            Log.d(TAG, "onDrawFrame: ");
            //清空屏幕，清空屏幕后调用glClearColor(）中设置的颜色填充屏幕
            //GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

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
        //设置OpenGL ES的版本为2
        setEGLContextClientVersion(2);
        //设置渲染器，负责真正绘制
        setRenderer(mGLRenderer);
        //RENDERMODE_CONTINUOUSLY模式会一直Render(一直调用onDrawFrame)，默认是此模式
        //RENDERMODE_WHEN_DIRTY当有数据时才rendered或主动调用GLSurfaceView的requestRender，适合camera预览
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    /**
     * 加载并编译着色器代码
     *
     * @param type       渲染器类型type={GLES20.GL_VERTEX_SHADER, GLES20.GL_FRAGMENT_SHADER}
     * @param shaderCode 渲染器代码 GLSL
     * @return
     */
    public static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);   //传入渲染器类型参数type，得到对应的着色器对象；
        GLES20.glShaderSource(shader, shaderCode);  //传入着色器对象和字符串shaderCode定义的源代码，将二者关联起来；
        GLES20.glCompileShader(shader);             //传入着色器对象，对其进行编译。
        return shader;
    }

}
