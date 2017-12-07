package com.eyesmart.testapplication.ui.viewwidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by FLY on 2017/12/7.
 */

public class MyView extends View {

    private Paint mPaint;


    void testCanvas(Canvas canvas) {
        // canvas.drawBitmap
        // canvas.drawLines();
//        canvas.drawPoint();
//        canvas.drawPoints();
        //canvas.drawText();
        //canvas.drawTextOnPath();
        //canvas.clipRect()
    }

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawNomal(canvas);
        drawTest(canvas);
        //drawDial(canvas);

        // 把整张画布绘制成白色
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        // 去锯齿
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        int viewWidth = this.getWidth();
        // 绘制圆形
        canvas.drawCircle(viewWidth / 10 + 10, viewWidth / 10 + 10
                , viewWidth / 10, paint);
        // 绘制正方形
        canvas.drawRect(10, viewWidth / 5 + 20, viewWidth / 5 + 10
                , viewWidth * 2 / 5 + 20, paint);
        // 绘制矩形
        canvas.drawRect(10, viewWidth * 2 / 5 + 30, viewWidth / 5 + 10
                , viewWidth / 2 + 30, paint);
        RectF re1 = new RectF(10, viewWidth / 2 + 40
                , 10 + viewWidth / 5, viewWidth * 3 / 5 + 40);
        // 绘制圆角矩形
        canvas.drawRoundRect(re1, 15, 15, paint);
        RectF re11 = new RectF(10, viewWidth * 3 / 5 + 50
                , 10 + viewWidth / 5, viewWidth * 7 / 10 + 50);
        // 绘制椭圆
        canvas.drawOval(re11, paint);
        // 定义一个Path对象，封闭成一个三角形
        Path path1 = new Path();
        path1.moveTo(10, viewWidth * 9 / 10 + 60);
        path1.lineTo(viewWidth / 5 + 10, viewWidth * 9 / 10 + 60);
        path1.lineTo(viewWidth / 10 + 10, viewWidth * 7 / 10 + 60);
        path1.close();
        // 根据Path进行绘制，绘制三角形
        canvas.drawPath(path1, paint);
        // 定义一个Path对象，封闭成一个五角形
        Path path2 = new Path();
        path2.moveTo(10 + viewWidth / 15, viewWidth * 9 / 10 + 70);
        path2.lineTo(10 + viewWidth * 2 / 15, viewWidth * 9 / 10 + 70);
        path2.lineTo(10 + viewWidth / 5, viewWidth + 70);
        path2.lineTo(10 + viewWidth / 10, viewWidth * 11 / 10 + 70);
        path2.lineTo(10, viewWidth + 70);
        path2.close();
        // 根据Path进行绘制，绘制五角形
        canvas.drawPath(path2, paint);
        // ----------设置填充风格后绘制----------
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        // 绘制圆形
        canvas.drawCircle(viewWidth * 3 / 10 + 20, viewWidth / 10 + 10
                , viewWidth / 10, paint);
        // 绘制正方形
        canvas.drawRect(viewWidth / 5 + 20, viewWidth / 5 + 20
                , viewWidth * 2 / 5 + 20, viewWidth * 2 / 5 + 20, paint);
        // 绘制矩形
        canvas.drawRect(viewWidth / 5 + 20, viewWidth * 2 / 5 + 30
                , viewWidth * 2 / 5 + 20, viewWidth / 2 + 30, paint);
        RectF re2 = new RectF(viewWidth / 5 + 20, viewWidth / 2 + 40
                , 20 + viewWidth * 2 / 5, viewWidth * 3 / 5 + 40);
        // 绘制圆角矩形
        canvas.drawRoundRect(re2, 15, 15, paint);
        RectF re21 = new RectF(20 + viewWidth / 5, viewWidth * 3 / 5 + 50
                , 20 + viewWidth * 2 / 5, viewWidth * 7 / 10 + 50);
        // 绘制椭圆
        canvas.drawOval(re21, paint);
        // 定义一个Path对象，封闭成一个三角形
        Path path3 = new Path();
        path3.moveTo(20 + viewWidth / 5, viewWidth * 9 / 10 + 60);
        path3.lineTo(viewWidth * 2 / 5 + 20, viewWidth * 9 / 10 + 60);
        path3.lineTo(viewWidth * 3 / 10 + 20, viewWidth * 7 / 10 + 60);
        path3.close();
        // 根据Path进行绘制，绘制三角形
        canvas.drawPath(path3, paint);
        // 定义一个Path对象，封闭成一个五角形
        Path path4 = new Path();
        path4.moveTo(20 + viewWidth * 4 / 15, viewWidth * 9 / 10 + 70);
        path4.lineTo(20 + viewWidth / 3, viewWidth * 9 / 10 + 70);
        path4.lineTo(20 + viewWidth * 2 / 5, viewWidth + 70);
        path4.lineTo(20 + viewWidth * 3 / 10, viewWidth * 11 / 10 + 70);
        path4.lineTo(20 + viewWidth / 5, viewWidth + 70);
        path4.close();
        // 根据Path进行绘制，绘制五角形
        canvas.drawPath(path4, paint);
        // ----------设置渐变器后绘制----------
        // 为Paint设置渐变器
        Shader mShader = new LinearGradient(0, 0, 40, 60
                , new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW}
                , null, Shader.TileMode.REPEAT);
        paint.setShader(mShader);
        //设置阴影
        paint.setShadowLayer(25, 20, 20, Color.GRAY);
        // 绘制圆形
        canvas.drawCircle(viewWidth / 2 + 30, viewWidth / 10 + 10
                , viewWidth / 10, paint);
        // 绘制正方形
        canvas.drawRect(viewWidth * 2 / 5 + 30, viewWidth / 5 + 20
                , viewWidth * 3 / 5 + 30, viewWidth * 2 / 5 + 20, paint);
        // 绘制矩形
        canvas.drawRect(viewWidth * 2 / 5 + 30, viewWidth * 2 / 5 + 30
                , viewWidth * 3 / 5 + 30, viewWidth / 2 + 30, paint);
        RectF re3 = new RectF(viewWidth * 2 / 5 + 30, viewWidth / 2 + 40
                , 30 + viewWidth * 3 / 5, viewWidth * 3 / 5 + 40);
        // 绘制圆角矩形
        canvas.drawRoundRect(re3, 15, 15, paint);
        RectF re31 = new RectF(30 + viewWidth * 2 / 5, viewWidth * 3 / 5 + 50
                , 30 + viewWidth * 3 / 5, viewWidth * 7 / 10 + 50);
        // 绘制椭圆
        canvas.drawOval(re31, paint);
        // 定义一个Path对象，封闭成一个三角形
        Path path5 = new Path();
        path5.moveTo(30 + viewWidth * 2 / 5, viewWidth * 9 / 10 + 60);
        path5.lineTo(viewWidth * 3 / 5 + 30, viewWidth * 9 / 10 + 60);
        path5.lineTo(viewWidth / 2 + 30, viewWidth * 7 / 10 + 60);
        path5.close();
        // 根据Path进行绘制，绘制三角形
        canvas.drawPath(path5, paint);
        // 定义一个Path对象，封闭成一个五角形
        Path path6 = new Path();
        path6.moveTo(30 + viewWidth * 7 / 15, viewWidth * 9 / 10 + 70);
        path6.lineTo(30 + viewWidth * 8 / 15, viewWidth * 9 / 10 + 70);
        path6.lineTo(30 + viewWidth * 3 / 5, viewWidth + 70);
        path6.lineTo(30 + viewWidth / 2, viewWidth * 11 / 10 + 70);
        path6.lineTo(30 + viewWidth * 2 / 5, viewWidth + 70);
        path6.close();
        // 根据Path进行绘制，绘制五角形
        canvas.drawPath(path6, paint);
        // ----------设置字符大小后绘制----------
        paint.setTextSize(48);
        paint.setShader(null);
        // 绘制7个字符串
        canvas.drawText("圆形", 60 + viewWidth * 3 / 5, viewWidth / 10 + 10, paint);
        canvas.drawText("正方形", 60 + viewWidth * 3 / 5, viewWidth * 3 / 10 + 20, paint);
        canvas.drawText("矩形", 60 + viewWidth * 3 / 5, viewWidth * 1 / 2 + 20, paint);
        canvas.drawText("圆角矩形", 60 + viewWidth * 3 / 5, viewWidth * 3 / 5 + 30, paint);
        canvas.drawText("椭圆", 60 + viewWidth * 3 / 5, viewWidth * 7 / 10 + 30, paint);
        canvas.drawText("三角形", 60 + viewWidth * 3 / 5, viewWidth * 9 / 10 + 30, paint);
        canvas.drawText("五角形", 60 + viewWidth * 3 / 5, viewWidth * 11 / 10 + 30, paint);
    }

    /**
     * 常规绘制  以(0,0)作为坐标原点参考点
     *
     * @param canvas
     */
    private void drawNomal(Canvas canvas) {
        mPaint = new Paint();
        // 绘制画布背景
        canvas.drawColor(Color.GRAY);
        //设置画笔颜色
        mPaint.setColor(Color.BLUE);
        //设置画笔为空心     如果将这里改为Style.STROKE  这个图中的实线圆柱体就变成了空心的圆柱体
        mPaint.setStyle(Paint.Style.STROKE);

        //绘制直线
        canvas.drawLine(50, 50, 450, 50, mPaint);

        //绘制矩形
        canvas.drawRect(100, 100, 200, 300, mPaint);
        //绘制矩形
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(300, 100, 400, 400, mPaint);
        mPaint.setColor(Color.YELLOW);
        RectF r = new RectF(150, 500, 270, 600);
        // 画矩形
        canvas.drawRect(r, mPaint);

        // 画圆
        canvas.drawCircle(50, 500, 50, mPaint);
        RectF oval = new RectF(350, 500, 450, 700);
        // 画椭圆
        canvas.drawOval(oval, mPaint);
        RectF rect = new RectF(100, 700, 170, 800);
        // 画圆角矩形
        canvas.drawRoundRect(rect, 30, 20, mPaint);
        //绘制圆弧 绘制弧形
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
        RectF re1 = new RectF(1000, 50, 1400, 200);
        canvas.drawArc(re1, 10, 270, false, mPaint);
        RectF re2 = new RectF(1000, 300, 1400, 500);
        canvas.drawArc(re2, 10, 270, true, mPaint);

        //设置Path路径
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(3);
        Path path = new Path();
        path.moveTo(500, 100);
        path.lineTo(920, 80);
        path.lineTo(720, 200);
        path.lineTo(600, 400);
        path.close();
        mPaint.setTextSize(46);
        canvas.drawPath(path, mPaint);
        canvas.drawTextOnPath("7qiuwoeruowoqjifasdkfjksjfiojio23ur8950", path, -20, -20, mPaint);
        //三角形
        path.moveTo(10, 330);
        path.lineTo(70, 330);
        path.lineTo(40, 270);
        path.close();
        canvas.drawPath(path, mPaint);
        //把开始的点和最后的点连接在一起，构成一个封闭梯形
        path.moveTo(10, 410);//绘画基点
        path.lineTo(70, 410);
        path.lineTo(55, 350);
        path.lineTo(25, 350);
        //如果是Style.FILL的话，不设置close,也没有区别，可是如果是STROKE模式， 如果不设置close,图形不封闭。当然，你也可以不设置close，再添加一条线，效果一样。
        path.close();
        canvas.drawPath(path, mPaint);
        //参数一为渐变起初点坐标x位置，参数二为y轴位置，参数三和四分辨对应渐变终点,其中参数new int[]{startColor, midleColor,endColor}是参与渐变效果的颜色集合，
        // 其中参数new float[]{0 , 0.5f, 1.0f}是定义每个颜色处于的渐变相对位置， 这个参数可以为null，如果为null表示所有的颜色按顺序均匀的分布
        // Shader.TileMode三种模式
        // REPEAT:沿着渐变方向循环重复
        // CLAMP:如果在预先定义的范围外画的话，就重复边界的颜色
        // MIRROR:与REPEAT一样都是循环重复，但这个会对称重复
        Shader mShader = new LinearGradient(0, 0, 100, 100,
                new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW},
                null, Shader.TileMode.REPEAT);
        mPaint.setShader(mShader);// 用Shader中定义定义的颜色来话
        mPaint.setStyle(Paint.Style.FILL);
        Path path1 = new Path();
        path1.moveTo(170, 410);
        path1.lineTo(230, 410);
        path1.lineTo(215, 350);
        path1.lineTo(185, 350);
        path1.close();
        canvas.drawPath(path1, mPaint);
        canvas.save();
    }

    /**
     * 绘制方法练习
     *
     * @param canvas
     */
    private void drawTest(Canvas canvas) {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        //平移
        canvas.translate(50, 900);
        canvas.drawRect(new Rect(0, 0, 100, 100), mPaint);
        canvas.translate(50, 50);
        canvas.drawRect(new Rect(0, 0, 100, 100), mPaint);
        //缩放
        canvas.translate(100, -50);
        canvas.drawRect(new Rect(0, 0, 300, 300), mPaint);
        // 保存画布状态
        canvas.save();
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
    }
}
