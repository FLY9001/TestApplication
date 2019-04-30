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

    /**
     @Test                                  定义为单元测试方法
     @Test (expected = Exception.class)     若没有抛出Exception类型(子类也可以)->失败
     @Test(timeout=100)                     性能测试，如果方法耗时超过100毫秒->失败
     @Before                                这个方法在每个测试之前执行，用于准备测试环境(如: 初始化类，读输入流等)，在一个测试类中，每个@Test方法的执行都会触发一次调用。
     @After                                 这个方法在每个测试之后执行，用于清理测试环境数据，在一个测试类中，每个@Test方法的执行都会触发一次调用。
     @Ignore或者@Ignore("太耗时")           忽略当前测试方法，一般用于测试方法还没有准备好，或者太耗时之类的
     public void method()

     @BeforeClass                           这个方法在所有测试开始之前执行一次，用于做一些耗时的初始化工作(如: 连接数据库)，方法必须是static
     @AfterClass                            这个方法在所有测试结束之后执行一次，用于清理数据(如: 断开数据连接)，方法必须是static
     public static void method()

     @FixMethodOrder(MethodSorters.NAME_ASCENDING)
     public class TestClass{}
     使得该测试类中的所有测试方法都按照方法名的字母顺序执行，可以指定3个值，分别是DEFAULT、JVM、NAME_ASCENDING

     */
}