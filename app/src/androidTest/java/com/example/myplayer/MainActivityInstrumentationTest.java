package com.example.myplayer;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.myplayer.activity.VideoPlayerActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by zhangdongsheng on 2017/3/3.
 * <p>
 * android 用UI 测试等            Instrumentation测试
 */


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityInstrumentationTest {

    private static final String STRING_TO_BE_TYPED = "Peter";

    //只能针对Activity 进行测试
    @Rule
    public ActivityTestRule<VideoPlayerActivity> mActivityRule = new ActivityTestRule<>(
            VideoPlayerActivity.class);

    @Test
    public void sayHello() {
        //1.获取控件输入字符串  关闭键盘
//        onView(withId(R.id.content)).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard()); //line 1
//
        //2.接下来，点击Say hello!的View，我们没有在布局的XML中为这个Button设置id，因此，通过搜索它上面的文字来找到它
//        onView(withText("Say hello!")).perform(click()); //line 2
//3.最后，将TextView上的文本同预期结果对比，如果一致则测试通过
//        String expectedText = "Hello, " + STRING_TO_BE_TYPED + "!";
//        onView(withId(R.id.content)).check(matches(withText(expectedText))); //line 3

        System.out.println("aaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println(onView(withId(R.id.content)).getClass().getName());
        onView(withId(R.id.content)).check(matches(withText("")));
    }

}
