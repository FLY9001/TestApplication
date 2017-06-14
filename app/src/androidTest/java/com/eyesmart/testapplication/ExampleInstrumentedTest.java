package com.eyesmart.testapplication;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * 单元测试：C语言中单元指一个函数，Java里单元指一个类，图形化的软件中可以指一个窗口或一个菜单等
 * <p>
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    /**
     * 测试函数需要添加 @Test 注解
     * 测试函数需要为 public ，一般为void
     */
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.eyesmart.testapplication", appContext.getPackageName());
    }

    /**
     * 单元测试，测试函数
     */
    @Test
    public void test2() {
        boolean result = "18210741899".matches("\\d{11}");
        Log.i("tag", "#####:" + result);
        /**
         * 验证邮箱
         */
        assertEquals("result:", result, true);
    }
}
