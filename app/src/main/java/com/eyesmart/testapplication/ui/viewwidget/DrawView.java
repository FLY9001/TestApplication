package com.eyesmart.testapplication.ui.viewwidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.eyesmart.testapplication.R;

/**
 * Created by FLY on 2017/12/7.
 */

public class DrawView extends View {

    void test(Canvas canvas) {
        initPain();         //画笔设置
        testDraw(canvas);   //画图形
        testcanvas(canvas); //画布变换

        testBitmapMatrix(canvas); //Matrix图像变换
        testBitmapMesh(canvas);   //Mesh图像扭曲
    }

    private Paint mPaint;

    public DrawView(Context context) {
        this(context, null);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPain();
    }

    private void initPain() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);         //颜色
        mPaint.setStyle(Paint.Style.FILL);  //填充风格
        mPaint.setStrokeWidth(4);           //宽度

        mPaint.setAntiAlias(true);          //抗锯齿
        mPaint.setAlpha(100);               //透明度
        //mPaint.setPathEffect(new DiscretePathEffect(3,5));//路径效果
        //mPaint.setShadowLayer(25, 20, 20, Color.GRAY);//设置阴影

        mPaint.setTextSize(58);             //文字大小
        mPaint.setTextAlign(Paint.Align.LEFT);//文字对齐方式

        // Shader可用几种效果来填充view，包括 平铺、线性渐变、圆形渐变、角度渐变
        //参数一为渐变起初点坐标x位置，参数二为y轴位置，参数三和四分辨对应渐变终点,其中参数new int[]{startColor, midleColor,endColor}是参与渐变效果的颜色集合，
        // 其中参数new float[]{0 , 0.5f, 1.0f}是定义每个颜色处于的渐变相对位置， 这个参数可以为null，如果为null表示所有的颜色按顺序均匀的分布
        // Shader.TileMode三种模式
        // REPEAT:沿着渐变方向循环重复
        // CLAMP:如果在预先定义的范围外画的话，就重复边界的颜色
        // MIRROR:与REPEAT一样都是循环重复，但这个会对称重复
        // 为Paint设置渐变器
//        Shader mShader = new LinearGradient(0, 0, 40, 60, new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW}, null, Shader.TileMode.REPEAT);
//        mPaint.setShader(mShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        testDraw(canvas);
        testBitmapMatrix(canvas);
        testcanvas(canvas);
    }

    private void testDraw(Canvas canvas) {
        canvas.drawColor(Color.LTGRAY);                                     // 绘制画布

        canvas.drawCircle(100, 100, 100, mPaint);          //圆形
        canvas.drawRect(0, 220, 200, 320, mPaint); //矩形
        RectF re1 = new RectF(0, 340, 200, 440);
        canvas.drawRoundRect(re1, 20, 20, mPaint);                   //圆角矩形
        RectF re11 = new RectF(0, 460, 200, 560);
        canvas.drawOval(re11, mPaint);                                      //椭圆
        Path path1 = new Path();
        path1.moveTo(100, 580);                                       //路径
        path1.lineTo(0, 780);
        path1.lineTo(200, 780);
        path1.close();//封闭
        canvas.drawPath(path1, mPaint);                                     //封闭成一个三角形

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        canvas.drawPoint(400, 100, mPaint);                             //点
        canvas.drawLine(300, 270, 500, 270, mPaint); //线
        RectF re2 = new RectF(300, 340, 500, 440);  //绘制圆弧、弧形
        canvas.drawArc(re2, 10, 270, false, mPaint);
        RectF re3 = new RectF(300, 460, 500, 560);
        canvas.drawArc(re3, 10, 270, true, mPaint);
        mPaint.setTextSize(58);
        canvas.drawText("文字", 300, 730, mPaint);

        mPaint.setColor(Color.GREEN);
        Path path = new Path();
        path.moveTo(600, 100);
        path.lineTo(1020, 80);
        path.lineTo(820, 200);
        path.lineTo(700, 400);
        path.close();
        mPaint.setTextSize(46);
        canvas.drawPath(path, mPaint);
        canvas.drawTextOnPath("沿路径绘制文字沿路径绘制文字沿路径绘制文字沿路径绘制文字", path, -20, -20, mPaint);
    }


    /**
     * 绘制方法练习
     *
     * @param canvas
     */
    private void testcanvas(Canvas canvas) {
        //translate平移、scale缩放、rotate旋转、skew错切
        //save保存画布坐标体系，restore恢复到保存时画布坐标体系
        //saveLayer新建图层

        //平移
        canvas.translate(50, 900);
        canvas.drawRect(new Rect(0, 0, 100, 100), mPaint);
        canvas.translate(50, 50);
        canvas.drawRect(new Rect(0, 0, 100, 100), mPaint);
        canvas.translate(100, -50);
        canvas.drawRect(new Rect(0, 0, 300, 300), mPaint);

        // 保存画布状态
        canvas.save();
        //缩放
        canvas.scale(0.5f, 0.5f);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(new Rect(0, 0, 300, 300), mPaint);
        // 画布状态回滚
        canvas.restore();

        // 先将画布平移到矩形的中心
        canvas.translate(400, -50);
        canvas.drawRect(new Rect(0, 0, 300, 300), mPaint);

        //旋转测试
        canvas.save();
        canvas.translate(350, 50);
        canvas.drawRect(new Rect(0, 0, 200, 200), mPaint);
        mPaint.setColor(Color.RED);
        //以点(x,y)为原点旋转坐标系,
        canvas.rotate(45, 200, 200);
        canvas.drawRect(new Rect(0, 0, 200, 200), mPaint);
        canvas.restore();

        //画布错切 三角函数tan的值
        canvas.translate(350, 300);
        canvas.drawRect(new Rect(0, 0, 400, 400), mPaint);
        // y 方向上倾斜45 度
        canvas.skew(0, 1);
        mPaint.setColor(0x8800ff00);
        canvas.drawRect(new Rect(0, 0, 400, 400), mPaint);

        //新建图层
        canvas.saveLayer(0, 0, 200, 200, mPaint);
        canvas.saveLayerAlpha(0, 0, 200, 200, 0x88);
        //复位图层，并画出内容
        canvas.restore();
        //canvas.restoreToCount();
    }


    Matrix matrix = new Matrix();
    Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.xy)).getBitmap();

    private void testBitmapMatrix(Canvas canvas) {
        float[] matrix_value = new float[]{
                1, 0, 0,
                0, 1, 0,
                0, 0, 1};
        matrix.setValues(matrix_value); //3*3的矩阵，表现为9位的float数组(可直接修改其值，用于复杂变换)

        /**简单API矩阵变换*/
        matrix.setTranslate(30, 30);                //平移
        matrix.setRotate(90, 0, 0);        //旋转
        matrix.setScale(0.15f, 0.15f, 0, 0);//缩放
        matrix.setSkew(30, 30, 0, 0);       //错切

        //根据原始位图与Matrix创建新图片
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        //绘制新位图
        canvas.drawBitmap(bmp, matrix, null);

        /**将图片分为网格，修改网格坐标进行扭曲*/
        //canvas.drawBitmapMesh();                          //扭曲
    }
    private void testBitmapMesh(Canvas canvas) {
        /**将图片分为网格，修改网格坐标进行扭曲*/
        int widthNum = 100;                                 //图宽网格的个数
        int heightNum = 100;                                //图高网格的个数
        int pointNum = (widthNum + 1) * (heightNum + 1);    //网格坐标点的个数
        float[] oldVerts = new float[pointNum * 2];         //存储原网格坐标点数值的数组
        float[] verts = new float[pointNum * 2];            //存储修改后的网格坐标点数值的数组

        int index = 0;
        for (int y = 0; y < heightNum; y++) {
            int fy = bitmap.getHeight() * y / heightNum;
            for (int x = 0; x < widthNum; x++) {
                int fx = bitmap.getWidth() * x / widthNum;
                oldVerts[index * 2 + 0] = verts[index * 2 + 0] = fx;
                oldVerts[index * 2 + 0] = verts[index * 2 + 1] = fx;
                index++;
            }
        }
        //修改verts中指定坐标数据，扭曲图像

        canvas.drawBitmapMesh(bitmap, widthNum, heightNum, verts, 0, null, 0, null);
    }
}
