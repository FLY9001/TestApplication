package com.eyesmart.testapplication.android;

/**
 * 产生卡顿原因：
 * 1、布局Layout过于复杂，无法在16ms内完成渲染。
 * 2、同一时间动画执行的次数过多，导致CPU或GPU负载过重。
 * 3、View过度绘制，导致某些像素在同一帧时间内被绘制多次。
 * 4、UI线程中做了稍微耗时的操作。
 * <p>
 * 工具：
 * GPU呈现模式分析；位置：开发者选项；以彩色柱状图表示一帧处理时间
 * 调试GPU过度绘制；位置：开发者选项；
 * Hierarchy Viewer
 * Android Lint：代码扫描工具；Analyze--->Inspect Code：执行扫描
 * <p>
 * 布局优化：
 * 1、合理运用布局：RelativeLayout性能较低，但如果能减少布局嵌套，反而更合适
 * 2、运用标签：Include（布局复用）、Merge（去除多余层级）、ViewStub（提高加载速度）
 * 3、避免过度绘制：白色、蓝色为主，绿色以上区域不能超过整个的三分之一
 * ---1.移除不需要的background。
 * ---2.在自定义View的OnDraw方法中，用canvas.clipRect来指定绘制的区域，防止重叠的组件发生过度绘制。
 */

public class TestUI {

}