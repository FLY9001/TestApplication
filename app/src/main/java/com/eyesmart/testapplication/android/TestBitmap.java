package com.eyesmart.testapplication.android;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * 颜色矩阵
 */

public class TestBitmap {

    //根据颜色矩阵改变图像效果
    //实现如：灰度效果、颜色反转、怀旧效果、高饱和度等
    public Bitmap imageEffect(Bitmap bm){
        /**颜色矩阵初始化*/
        ColorMatrix colorMatrix0 = new ColorMatrix(new float[]{
                1, 0, 0, 0, 0,  //红R
                0, 1, 0, 0, 0,  //绿G
                0, 0, 1, 0, 0,  //蓝B
                0, 0, 0, 1, 0,  //透明度A
        });
        float[] array = colorMatrix0.getArray();  //4*5的矩阵，表现为20位的float数组

        /**颜色矩阵设置*/
        ColorMatrix colorMatrix = new ColorMatrix();
        //设置RGB色调
        colorMatrix.setRotate(0,255);//0红色，1绿色，2蓝色
        //设置饱和度
        colorMatrix.setSaturation(0);//0为灰度图像
        //设置亮度
        colorMatrix.setScale(0,0,0,1); //三色为0，图像全黑

        //合并另一个设置
        colorMatrix.postConcat(colorMatrix0);

        /**根据矩阵得到结果*/
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        canvas.drawBitmap(bm,0,0,paint);
        return bmp;
    }

    //根据改变像素值改变图像效果
    public Bitmap handleImagePixels(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        int[] oldPx=new int[width*height];
        //得到图片像素点
        bm.getPixels(oldPx,0,width,0,0,width,height);
        int[] newPx =new int[width*height];
        for (int i = 0; i <width*height ; i++) {
            int color = oldPx[i];
            int r = Color.red(color);
            int g = Color.green(color);
            int b = Color.blue(color);
            int a = Color.alpha(color);
            //根据算法得出新的a,r,g,b
            newPx[i]=Color.argb(a,r,g,b);
        }
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        //设置新的像素点
        bmp.setPixels(newPx,0,width,0,0,width,height);
        return bmp;
    }
}
