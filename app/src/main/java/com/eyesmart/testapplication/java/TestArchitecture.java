package com.eyesmart.testapplication.java;

import com.eyesmart.testapplication.mvp.view.MVPActivity;

/**
 * 架构设计的目的:模块内部的高聚合和模块之间的低耦合
 */

public class TestArchitecture {
    /**
     * 模型层(Model)、视图层(View)、控制层(Controller)
     */
    void testMVC() {
        //M：处理数据，业务逻辑，业务模型
        //V：界面的显示，xml
        //C：桥梁的作用，Activity
        //
        //缺点：随着界面及其逻辑的复杂度不断提升，Activity类的职责不断增加，以致变得庞大臃肿。
    }

    /**
     * Model:负责存储、检索、操纵数据
     * View:负责绘制UI元素、与用户进行交互(在Android中体现为Activity)
     * Presenter:作为View与Model交互的中间纽带，处理与用户交互的负责逻辑。
     * View interface:需要View实现的接口，View通过View interface与Presenter进行交互，降低耦合，方便进行单元测试
     * <p>
     * 分离了视图逻辑和业务逻辑，降低了耦合,提高代码的可阅读性
     * Activity 只处理生命周期的任务，代码变得更加简洁
     * 把业务逻辑抽到 Presenter 中去，避免后台线程引用着 Activity 导致 Activity 的资源无法被系统回收从而引起内存泄露和 OOM
     * Presenter 被抽象成接口，可以有多种具体的实现，所以方便进行单元测试
     */
    void testMVP() {
        Class<MVPActivity> mvpActivityClass = MVPActivity.class;
    }
}
