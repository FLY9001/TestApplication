package com.eyesmart.testapplication;

import android.content.Intent;
import android.test.ActivityUnitTestCase;

/**
 * Created by Tian on 2017-6-13 0013.
 */

public class MyActivityTest extends ActivityUnitTestCase<MainActivity> {
    private Intent intent;

    // 要这样才不会报错
    public MyActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        intent = new Intent(getInstrumentation().getTargetContext(), MainActivity.class);

    }

    public void testStart() {
        startActivity(intent, null, null);
        assertEquals("com.eyesmart.testapplication", getInstrumentation().getTargetContext().getPackageName());
    }
}
