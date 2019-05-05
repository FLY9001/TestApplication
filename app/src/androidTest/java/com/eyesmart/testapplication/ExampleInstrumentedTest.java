package com.eyesmart.testapplication;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * AndroidJUnitRunner，无法在本地JVM运行，速度慢
 *
 */

//若只使用JUnit4，可省略
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() throws Exception {
        //通过InstrumentationRegistry来获取Context
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.eyesmart.testapplication", appContext.getPackageName());
    }
}
