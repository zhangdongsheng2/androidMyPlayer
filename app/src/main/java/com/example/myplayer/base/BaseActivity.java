package com.example.myplayer.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * Activity基类 2016/3/23.
 */
public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (initBundle(getIntent().getExtras())) {
            setContentView(getContentView());

            ButterKnife.bind(this);

            initWindow();
            initWidget();
            initData();
        } else {
            finish();
        }
    }

    protected abstract int getContentView();

    protected boolean initBundle(Bundle bundle) {
        return true;
    }

    protected void initWindow() {
    }

    protected void initWidget() {
    }

    protected void initData() {
    }

    /**
     * 文本View
     */
    public TextView textView(int textview) {
        return (TextView) findViewById(textview);
    }

    /**
     * 文本button
     */
    public Button button(int id) {
        return (Button) findViewById(id);
    }

    /**
     * 文本button
     */
    public ImageView imageView(int id) {
        return (ImageView) findViewById(id);
    }

    /**
     * 文本editText
     */
    public EditText editText(int id) {
        return (EditText) findViewById(id);
    }
}
