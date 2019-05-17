package com.eyesmart.testapplication.java;

import com.eyesmart.testapplication.mvp.view.MVPActivity;

/**
 * 架构设计的目的:模块内的高聚合和模块间的低耦合
 */

public class TestArchitecture {
    /**
     * 模型层(Model)       处理数据    Bean类等
     * 视图层(View)        界面显示    xml
     * 控制层(Controller)  桥梁作用    Activity
     */
    void testMVC() {
        //缺点：随着界面及其逻辑的复杂度不断提升，Activity类的职责不断增加，以致变得庞大臃肿。
    }

    /**
     * Model:       负责存储、检索、操纵数据
     * View:        负责绘制UI元素、与用户进行交互(在Android中体现为Activity)
     * Presenter:   作为View与Model交互的中间纽带，处理与用户交互的负责逻辑。
     * View interface:需要View实现的接口，View通过View interface与Presenter进行交互，降低耦合，方便进行单元测试
     * <p>
     * 为了保持类的功能单一，并且面向接口编程。
     */
    void testMVP() {
        Class<MVPActivity> mvpActivityClass = MVPActivity.class;
    }
}
