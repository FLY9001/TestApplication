package com.eyesmart.testapplication.android.opengl;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class CameraDrawer {

    /**
     * 1.1、创建包含顶点坐标的浮点数组
     */
    private static final int COORDS_PER_VERTEX = 2;
    //顶点坐标-坐标值是固定的搭配，不同的顺序会出现不同的预览效果
    static float squareCoords[] = {
            -1.0f, 1.0f,
            -1.0f, -1.0f,
            1.0f, -1.0f,
            1.0f, 1.0f,
    };
    private short drawOrder[] = {0, 1, 2, 0, 2, 3}; //绘制顶点的顺序
    //纹理坐标-相机预览时对片元着色器使用的是纹理texture而不是颜色color
    static float textureVertices[] = {
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 0.0f,
    };
    private FloatBuffer vertexBuffer, textureVerticesBuffer;
    private ShortBuffer drawListBuffer;

    /**
     * 2.1、创建两个着色器代码
     */
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "attribute vec2 inputTextureCoordinate;" +
                    "varying vec2 textureCoordinate;" +
                    "void main() {" +
                    "   gl_Position = vPosition; gl_PointSize = 10.0;" +
                    "   textureCoordinate = inputTextureCoordinate;" +
                    "}";

    private final String fragmentShaderCode =
            "#extension GL_OES_EGL_image_external : require\n" +
                    "precision mediump float;" +
                    "varying vec2 textureCoordinate;" +
                    "uniform samplerExternalOES s_texture;" +
                    "void main() {" +
                    "   gl_FragColor = texture2D( s_texture, textureCoordinate );" +
                    "}";

    private final int mProgram;

    private int mPositionHandle, mTextureCoordHandle;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    public CameraDrawer(int textureID) {
        /**1.2、将浮点数组添加到一个浮点缓冲区*/
        vertexBuffer = FloatBufferUtil(squareCoords);
        drawListBuffer = ShortBufferUtil(drawOrder);
        textureVerticesBuffer = FloatBufferUtil(textureVertices);


        /**2.2、加载编译着色器代码*/
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        /**2.3、将着色器对象放入到程式并链接GLES*/
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);


        /**3.1、设置要使用的程式*/
        GLES20.glUseProgram(mProgram);
        //设置SurfaceTexture
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureID);
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        mTextureCoordHandle = GLES20.glGetAttribLocation(mProgram, "inputTextureCoordinate");
    }

    public void draw(float[] mtx) {
        /**3.2、设置着色器索引*/
        //使用一次glEnableVertexAttribArray方法就要使用一次glVertexAttribPointer方法
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
        GLES20.glEnableVertexAttribArray(mTextureCoordHandle);
        GLES20.glVertexAttribPointer(mTextureCoordHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, vertexStride, textureVerticesBuffer);

        //相机预览变换为正
        textureVerticesBuffer.clear();
        //textureVerticesBuffer.put(textureVertices);
        textureVerticesBuffer.put(transformTextureCoordinates(textureVertices, mtx));
        textureVerticesBuffer.position(0);

        /**3.3、绘制及收尾*/
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
        // 使用了glEnableVertexAttribArray方法就必须使用glDisableVertexAttribArray方法
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTextureCoordHandle);
    }

    /**
     * 变换纹理坐标，旋转90度
     */
    private float[] transformTextureCoordinates(float[] coords, float[] matrix) {
        float[] result = new float[coords.length];
        float[] vt = new float[4];

        for (int i = 0; i < coords.length; i += 2) {//坐标
            float[] v = {coords[i], coords[i + 1], 0, 1};
            Matrix.multiplyMV(vt, 0, matrix, 0, v, 0);
            //x轴
            result[i + 1] = vt[0];          //逆时针
            //result[i + 1] = 1.0f - vt[0];   //顺时针

            //y轴
            result[i] = 1.0f - vt[1];
           //result[i ] = coords[i + 1];
        }
        return result;
    }

    /**
     * 加载并编译着色器代码
     *
     * @param type       渲染器类型type={GLES20.GL_VERTEX_SHADER, GLES20.GL_FRAGMENT_SHADER}
     * @param shaderCode 渲染器代码 GLSL
     * @return
     */
    private int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);   //传入渲染器类型参数type，得到对应的着色器对象；
        GLES20.glShaderSource(shader, shaderCode);  //传入着色器对象和字符串shaderCode定义的源代码，将二者关联起来；
        GLES20.glCompileShader(shader);             //传入着色器对象，对其进行编译。
        return shader;
    }

    // 定义一个工具方法，将int[]数组转换为OpenGL ES所需的IntBuffer
    private IntBuffer IntBufferUtil(int[] arr) {
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个int占4个字节
        ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
        // 数组排列用nativeOrder
        bb.order(ByteOrder.nativeOrder());
        IntBuffer mBuffer = bb.asIntBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }

    // 定义一个工具方法，将float[]数组转换为OpenGL ES所需的FloatBuffer
    public FloatBuffer FloatBufferUtil(float[] arr) {
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个int占4个字节
        ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
        // 数组排列用nativeOrder
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer mBuffer = bb.asFloatBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }

    // 定义一个工具方法，将short[]数组转换为OpenGL ES所需的ShortBuffer
    private ShortBuffer ShortBufferUtil(short[] arr) {
        ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 2);
        bb.order(ByteOrder.nativeOrder());
        ShortBuffer buffer = bb.asShortBuffer();
        buffer.put(arr);
        buffer.position(0);
        return buffer;
    }

    // 定义一个工具方法，将Short[]数组转换为OpenGL ES所需的ShortBuffer
    private ByteBuffer ByteBufferUtil(Byte[] arr) {
        ByteBuffer bb = ByteBuffer.allocateDirect(arr.length);
        bb.position(0);
        return bb;
    }
}