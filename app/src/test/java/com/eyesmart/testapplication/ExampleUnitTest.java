package com.eyesmart.testapplication;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * 不依赖 Android SDK
 *
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    /**
     * 单元测试，测试函数
     */
    @Test
    public void test2() {
        boolean result = "18210741899".matches("\\d{11}");
        System.out.println("#####:" + result);
        /**
         * 验证邮箱
         */
        assertEquals("result:", result, true);
    }
}