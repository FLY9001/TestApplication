package com.eyesmart.testapplication.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.eyesmart.testapplication.R;

/**
 * 图像处理
 * https://www.jianshu.com/p/8206dd8b6d8b
 */

public class TestBitmap {
    public static void test(Bitmap bm, Canvas canvas) {
        testBitmapColorMatrix(bm);//ColorMatrix改变图像效果
        testBitmapPixels(bm);     //Pixels像素值改变图像效果

        testBitmapMatrix(canvas); //Matrix变换图像
        testBitmapMesh(canvas);   //Mesh网格扭曲图像

        testXformode(canvas);     //图像叠加
    }

    //根据颜色矩阵改变图像效果
    //实现如：灰度效果、颜色反转、怀旧效果、高饱和度等
    public static Bitmap testBitmapColorMatrix(Bitmap bm) {
        /**颜色矩阵初始化*/
        ColorMatrix colorMatrix0 = new ColorMatrix(new float[]{
                1, 0, 0, 0, 0,  //红R
                0, 1, 0, 0, 0,  //绿G
                0, 0, 1, 0, 0,  //蓝B
                0, 0, 0, 1, 0,  //透明度A
        });
        float[] array = colorMatrix0.getArray();  //4*5的矩阵，表现为20位的float数组(可直接修改其值，用于复杂变换)

        /**简单API颜色矩阵设置*/
        //设置RGB色调
        ColorMatrix rotateMatrix = new ColorMatrix();
        rotateMatrix.setRotate(0, 255);//0红色，1绿色，2蓝色
        rotateMatrix.setRotate(1, 255);//0红色，1绿色，2蓝色
        rotateMatrix.setRotate(2, 255);//0红色，1绿色，2蓝色

        //设置饱和度
        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(0);//0为灰度图像
        //设置亮度
        ColorMatrix scaleMatrix = new ColorMatrix();
        scaleMatrix.setScale(0, 0, 0, 1); //三色为0，图像全黑

        //合并效果
        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(rotateMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(scaleMatrix);

        /**根据矩阵得到结果*/
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));

        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        canvas.drawBitmap(bm, 0, 0, paint);
        return bmp;
    }

    //根据改变像素值改变图像效果
    public static Bitmap testBitmapPixels(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        int[] oldPx = new int[width * height];
        //得到图片像素点
        bm.getPixels(oldPx, 0, width, 0, 0, width, height);
        int[] newPx = new int[width * height];
        for (int i = 0; i < width * height; i++) {
            int color = oldPx[i];
            int r = Color.red(color);
            int g = Color.green(color);
            int b = Color.blue(color);
            int a = Color.alpha(color);
            //根据算法得出新的a,r,g,b
            newPx[i] = Color.argb(a, r, g, b);
        }
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        //设置新的像素点
        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }


    static Matrix matrix = new Matrix();
    static Bitmap bitmap = Bitmap.createBitmap((Bitmap) null);

    //Matrix图像变换
    private static void testBitmapMatrix(Canvas canvas) {
        float[] matrix_value = new float[]{
                1, 0, 0,
                0, 1, 0,
                0, 0, 1};
        matrix.setValues(matrix_value); //3*3的矩阵，表现为9位的float数组(可直接修改其值，用于复杂变换)

        /**简单API矩阵变换*/
        matrix.setTranslate(30, 30);                //平移，set方法每次会重置，pre、post可区分前后关系
        matrix.setRotate(90, 0, 0);        //旋转
        matrix.setScale(0.15f, 0.15f, 0, 0);//缩放
        matrix.setSkew(30, 30, 0, 0);       //错切

        //根据原始位图与Matrix创建新图片
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        //绘制新位图
        canvas.drawBitmap(bmp, matrix, null);
    }

    private static void testBitmapMesh(Canvas canvas) {
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

    static Paint mPaint = new Paint();
    static Context context;

    //图像叠加
    private static void testXformode(Canvas canvas) {
        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.xformod);

        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap1, 0, 0, mPaint);
        mPaint.setXfermode(null);
    }
}
