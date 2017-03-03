package com.example.myplayer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by zhangdongsheng on 2017/3/3.
 * <p>
 * 单元测试  的专用类   测试一下java 的东西 不用测android 专用的
 */
public class CalculatorTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("aaaaaaaaaaaaaaaaaaa");
    }

    @Test
    public void sum() throws Exception {
        System.out.println("bbbbbbbbbbbbbbbbbbb");
        final int expected = 3;
        final int reality = 3;
        assertEquals(expected, reality);
    }

    @Test
    public void substract() throws Exception {

    }

    @Test
    public void divide() throws Exception {

    }

    @Test
    public void multiply() throws Exception {

    }

}