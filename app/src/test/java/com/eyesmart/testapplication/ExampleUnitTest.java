package com.eyesmart.testapplication;

import android.content.Context;

import com.eyesmart.testapplication.android.TestXmlJson;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * https://www.jianshu.com/p/aa51a3e007e2
 *
 * 单元测试：指对软件中的最小可测试单元进行检查和验证。
 * Java语言单元测试框架，JUnit4
 * Android 目前不支持Junit5
 *
 * test/java/目录：          编写Java测试用例。本地单元测试，运行在本地JVM上，速度快
 * androidTest/java/目录：   编写Android测试用例。仪器化单元测试，需要运行在android设备或模拟器下面，可以使用android系统的API
 *
 * 测试代码分类：
 * 1.强依赖关系   如在Activity，Service等组件中的方法，其特点是大部分为private方法，并且与其生命周期相关，无法直接进行单元测试，可以进行Ecspreso等UI测试。
 * 2.部分依赖     代码实现依赖注入，该类需要依赖Context等android对象的依赖，可以通过Mock或其它第三方框架实现JUnit单元测试或使用androidJunitRunner进行单元测试。
 * 3.纯java代码   不存在对android库的依赖，可以进行JUnit单元测试
 *
 * JUnit4               一个基于注解的单元测试框架，可以使用Hamcrest library，通过使用hamcrest的匹配工具，可以让你更灵活的进行测试
 * AndroidJUnitRunner   兼容JUnit4测试运行器。涉及到android系统库的调用时，在androidTest目录创建测试类，添加@RunWith(AndroidJUnit4.class)注解
 * Mockito              模拟依赖对象，以达到隔离依赖的效果，实现解耦
 * Robolectric          模拟Android系统核心库，可直接运行在JVM上，十分方便
 * Espresso             UI测试框架;适合在单个应用的功能UI测试
 * UI Automator：       UI测试框架;适用于跨应用的功能UI测试及安装应用
 *
 * 运行方式：1、手动Run，2、gradle指令运行
 *
 * 快捷方法创建类的测试类：ALT + ENTER
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
    /**
     @Test                                  定义为单元测试方法，方法必须是public void
     @Test (expected = Exception.class)     若没有抛出Exception类型(子类也可以)->失败
     @Test(timeout=100)                     性能测试，如果方法耗时超过100毫秒->失败

     @Before                                这个方法在每个测试之前执行，用于准备测试环境(如: 初始化类，读输入流等)，在一个测试类中，每个@Test方法的执行都会触发一次调用。
     @After                                 这个方法在每个测试之后执行，用于清理测试环境数据，在一个测试类中，每个@Test方法的执行都会触发一次调用。

     @Ignore或者@Ignore("太耗时")           忽略当前测试方法，一般用于测试方法还没有准备好，或者太耗时之类的
     public void method()

     @BeforeClass                           这个方法在所有测试开始之前执行一次，用于做一些耗时的初始化工作(如: 连接数据库)
     @AfterClass                            这个方法在所有测试结束之后执行一次，用于清理数据(如: 断开数据连接)
     public static void method()            方法必须是public static void

     @RunWith                               测试类名之前，用来确定这个类的测试运行器
     @FixMethodOrder(MethodSorters.NAME_ASCENDING)
     public class TestClass{}
     使得该测试类中的所有测试方法都按照方法名的字母顺序执行，可以指定3个值，分别是DEFAULT、JVM、NAME_ASCENDING
     */


    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test2() {
        boolean result = "18210741899".matches("\\d{11}");
        System.out.println("#####:" + result);
        /**
         * 验证邮箱
         */
        assertEquals("result:", result, true);

        //使用hamcrest进行assert，直观，易读
        assertThat(result, is(true));
    }

    @Mock
    Context mMockContext;

    @Test
    public void readStringFromContext_LocalizedString() {
        TestXmlJson.Person mPerson = mock(TestXmlJson.Person.class); //<--使用mock方法
        assertNotNull(mPerson);


        //模拟方法调用的返回值，隔离对Android系统的依赖
        when(mMockContext.getString(R.string.app_name)).thenReturn("AndroidUnitTest");
        Assert.assertThat(mMockContext.getString(R.string.app_name), is("AndroidUnitTest"));

        when(mMockContext.getPackageName()).thenReturn("com.jdqm.androidunittest");
        System.out.println(mMockContext.getPackageName());
    }

    /*
    @Mock
    MyClass test;

    @Test
    public void mockitoTestExample() throws Exception {

        //可是使用注解@Mock替代
        //MyClass test = mock(MyClass.class);

        // 当调用test.getUniqueId()的时候返回43
        when(test.getUniqueId()).thenReturn(18);
        // 当调用test.compareTo()传入任意的Int值都返回43
        when(test.compareTo(anyInt())).thenReturn(18);

        // 当调用test.close()的时候，抛NullPointerException异常
        doThrow(new NullPointerException()).when(test).close();
        // 当调用test.execute()的时候，什么都不做
        doNothing().when(test).execute();

        assertThat(test.getUniqueId(), is(18));
        // 验证是否调用了1次test.getUniqueId()
        verify(test, times(1)).getUniqueId();
        // 验证是否没有调用过test.getUniqueId()
        verify(test, never()).getUniqueId();
        // 验证是否至少调用过2次test.getUniqueId()
        verify(test, atLeast(2)).getUniqueId();
        // 验证是否最多调用过3次test.getUniqueId()
        verify(test, atMost(3)).getUniqueId();
        // 验证是否这样调用过:test.query("test string")
        verify(test).query("test string");
        // 通过Mockito.spy() 封装List对象并返回将其mock的spy对象
        List list = new LinkedList();
        List spy = spy(list);
        //指定spy.get(0)返回"Jdqm"
        doReturn("Jdqm").when(spy).get(0);
        assertEquals("Jdqm", spy.get(0));
    }
    */
}