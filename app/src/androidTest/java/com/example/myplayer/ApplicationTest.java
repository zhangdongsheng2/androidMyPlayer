package com.example.myplayer;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.junit.Test;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 * <p>
 * android 专用测试类
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


    @Test
    public void test_a() throws Exception {
        final int expected = 3;
        final int reality = 3;
        assertEquals(expected, reality);
    }
}