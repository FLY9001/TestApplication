package com.eyesmart.testapplication.android.opengl;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * OpenGL只能画点、线、三角形，复杂的图形都是由三角形构成的。
 * <p>
 * 1、坐标值准备
 *      1.1、创建包含顶点坐标的浮点数组
 *      1.2、将浮点数组添加到一个浮点缓冲区
 * 2、着色器准备
 *      2.1、创建两个着色器代码
 *      2.2、加载编译着色器代码
 *      2.3、着色器放入到程式并链接GLES
 * 3、着色器准备
 *      3.1、设置程式
 *      3.2、设置着色器索引
 *      3.3、绘制及收尾
 */
//三角形
public class Triangle {

    /**1.1、创建包含顶点坐标的浮点数组*/
    //每个顶点的坐标数
    private static final int COORDS_PER_VERTEX = 3;
    //创建三角形顶点数组，[0,0,0]为GLSurfaceView框架的中心，[ - 1，-1,0]是左下角，[1,1,0]是右上角，
    //OpenGL 只会渲染坐标值范围在 [-1, 1] 的内容
    private static float triangleCoords[] = {   //“卷曲顺序”，以逆时针顺序排列顶点。可以优化性能：使用卷曲顺序可以指出一个三角形属于任何给定物体的前面或者后面，OpenGL可以忽略那些无论如何都无法被看到的后面的三角形。
            0.0f, 1.0f, 0.0f,   // top
            -0.5f, -1.0f, 0.0f, // bottom left
            0.5f, -1.0f, 0.0f   // bottom right
    };
    //浮点缓冲区
    private FloatBuffer vertexBuffer;


    /**2.1、创建两个着色器代码*/
    //Shader着色器，专为图形处理单元（即GPU）编译的一种小型程序
    //OpenGL程序本质上都可以被分为两部分：CPU端运行的部分，采用C++、Java之类的语言编写；以及GPU端运行的部分，使用GLSL语言编写。
    //顶点着色器代码，绘制形状。用于渲染形状的顶点的OpenGLES 图形代码。
    //attribute变量(属性变量)只能用于顶点着色器中
    //uniforms变量(一致变量)用来将数据值从应用程其序传递到顶点着色器或者片元着色器。 。
    //varying变量(易变变量)是从顶点着色器传递到片元着色器的数据变量。
    //gl_Position（必须）为内建变量，表示变换后点的空间位置。
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +           //应用程序传入顶点着色器的顶点位置
                    "void main() {" +
                    "  gl_Position = vPosition;" +  //设置此次绘制此顶点位置
                    "}";
    //片元着色器代码，上色。用于渲染形状的外观（颜色或纹理）的OpenGLES 代码。
    private final String fragmentShaderCode =
            "precision mediump float;" +            //设置工作精度
                    "uniform vec4 vColor;" +        //应用程序传入着色器的颜色变量
                    "void main() {" +
                    "  gl_FragColor = vColor;" +    //颜色值传给gl_FragColor内建变量，完成片元的着色
                    "}";
    //一个OpenGLES对象，包含了你想要用来绘制一个或多个形状的shader。
    private final int mProgram;


    public Triangle() {
        /**1.2、将浮点数组添加到一个浮点缓冲区*/
        //Java代码运行在虚拟机中，OpenGL直接运行在本地硬件
        //需要把Java的数据复制到本地内存，本地内存就可以被本地环境（OpenGL）存取，而且不受Java垃圾回收器的管控
        //1、先初始化大小合适的字节缓冲区。因为每个浮点数占用4个字节，需*4
        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);//allocateDirect创建内存块用作缓冲区，本地内存，不在JVM堆栈中；
        bb.order(ByteOrder.nativeOrder());  //设置使用设备硬件的原生字节序
        vertexBuffer = bb.asFloatBuffer();  //2、再从ByteBuffer中创建一个浮点缓冲区
        vertexBuffer.put(triangleCoords);   //3、最后把坐标都添加到FloatBuffer中
        vertexBuffer.position(0);           //并设置buffer从第一个坐标开始读


        /**2.2、加载编译着色器代码*/
        int vertexShader = TestGLSurfaceView.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = TestGLSurfaceView.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        /**2.3、将着色器对象放入到程式并链接GLES*/
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
    }


    private int mPositionHandle;    //用于存取attribute修饰变量的索引
    private int mColorHandle;       //用于存取 uniform 修饰变量的索引
    private final int vertexStride = COORDS_PER_VERTEX * 4;                     //每个顶点坐标数据长度
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;  //顶点个数
    private float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};      //red, green, blue, alpha

    //图形类中创建draw()方法，拿到链接至GLES的程式(Program)，设置形状的顶点位置和表面的颜色值;
    public void draw() {
        /**3.1、设置要使用的程式*/
        GLES20.glUseProgram(mProgram);

        /**3.2、设置着色器索引*/
        //定点着色器相关
        //Java代码中需要获取shader代码中定义的变量索引，用于在后面的绘制代码中进行赋值
        //变量索引在GLSL程式生命周期内（链接之后和销毁之前）都是固定的，只需获取一次
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");//从程式中获取顶点着色器代码中的变量索引
        //绑定vertex坐标值
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);   //glVertexAttribPointer()告诉OpenGL，它可以在缓冲区vertexBuffer中获取vPosition的数据
        //启用索引
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        //片元着色器相关
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");     //从程式中获取片元着色器代码中的变量索引
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);              //设置颜色

        /**3.3、绘制及收尾*/
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);            //GLES20.glDrawArrays或GLES20.glDrawElements
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}