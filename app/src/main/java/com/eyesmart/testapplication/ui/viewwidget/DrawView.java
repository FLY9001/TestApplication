package com.eyesmart.testapplication.ui.viewwidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.eyesmart.testapplication.android.TestBitmap;

/**
 * Created by FLY on 2017/12/7.
 */

public class DrawView extends View {

    void test(Bitmap bitmap, Canvas canvas) {
        initPain();         //画笔设置
        testShader();       //Shader渲染
        testDraw(canvas);   //画图形
        testcanvas(canvas); //画布变换

        TestBitmap.test(bitmap, canvas);//Bitmap相关
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
    }

    private void testShader() {
        int[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
        // Shader可用几种效果来填充view，包括 平铺、线性渐变、圆形渐变、角度渐变
        //参数一为渐变起初点坐标x位置，参数二为y轴位置，参数三和四分辨对应渐变终点,其中参数new int[]{startColor, midleColor,endColor}是参与渐变效果的颜色集合，
        // 其中参数new float[]{0 , 0.5f, 1.0f}是定义每个颜色处于的渐变相对位置， 这个参数可以为null，如果为null表示所有的颜色按顺序均匀的分布
        // Shader.TileMode三种模式
        // REPEAT:沿着渐变方向循环重复
        // CLAMP:如果在预先定义的范围外画的话，就重复边界的颜色
        // MIRROR:与REPEAT一样都是循环重复，但这个会对称重复

        //平铺渲染
        Shader bitmapShader = new BitmapShader(Bitmap.createBitmap((Bitmap) null), Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);//x方向重复，y方向镜像
        //线性渐变
        Shader linearGradient = new LinearGradient(0, 0, 40, 60, colors, null, Shader.TileMode.REPEAT);
        //圆形渐变
        Shader radialGradient = new RadialGradient(0f, 0f, 80, colors, null, Shader.TileMode.REPEAT);
        //角度渐变
        Shader sweepGradient = new SweepGradient(160, 160, colors, null);
        //组合
        Shader composeShader = new ComposeShader(linearGradient, radialGradient, PorterDuff.Mode.DARKEN);
        mPaint.setShader(composeShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        testDraw(canvas);
        testcanvas(canvas);
    }

    private void testDraw(Canvas canvas) {
        canvas.drawColor(Color.LTGRAY);                                     // 绘制画布

        canvas.drawCircle(100, 100, 100, mPaint);            //圆形
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

        testPathEffect(canvas, path);
    }

    //PathEffect：线条效果（实线、虚线、圆角线等）
    private void testPathEffect(Canvas canvas, Path path) {
        mPaint.setPathEffect(new CornerPathEffect(10));                      //拐角圆滑的线
        mPaint.setPathEffect(new DiscretePathEffect(1, 1));//线上带有杂点
        mPaint.setPathEffect(new DashPathEffect(new float[10], 0));          //虚线
//        mPaint.setPathEffect(new PathDashPathEffect());     //虚线点的形状
//        mPaint.setPathEffect(new ComposePathEffect());     //组合效果

        mPaint.setPathEffect(new CornerPathEffect(10));              //PathEffect：线条效果（实线、虚线、圆角线等）
        path.addRect(0, 0, 8, 8, Path.Direction.CCW);
        canvas.drawPath(path, mPaint);
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
}
