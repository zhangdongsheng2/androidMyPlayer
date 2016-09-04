package net1;

/**
 * Created by zhangdongsheng on 16/9/4.
 */
public interface CallBack<T> {
    void onSuccess(T result);

    void onFailure();
}
