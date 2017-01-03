package net1;


/**
 * 网络请求的工厂类
 *
 * @author 冯高远
 * @date 2016年3月21日 下午2:39:28
 * @description
 */
public class NetWorkFactory {
    private static OkhttpImpl okhttpImpl = new OkhttpImpl();

    private NetWorkFactory() {
    }

    public static INetWork getInstance() {
        return okhttpImpl;
    }


}
