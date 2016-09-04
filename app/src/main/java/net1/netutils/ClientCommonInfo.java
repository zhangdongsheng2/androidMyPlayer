package net1.netutils;

import android.os.Build;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ClientCommonInfo {

    private static ClientCommonInfo clientInfo = new ClientCommonInfo();
    private int currentUserId;    //当前登陆用户的ID，如果客户端用户未登陆，则该属�?为空�?
    private String userToken;    //用户授权的Token, 该Token与用户相关，如果客户端用户未登陆，则该token为空
    private String appToken;    //App授权的Token,
    private String terminalSourceDesc;    //终端描述	安卓:[SDK],[Brand],[Mode]  例如: SDK 5.0,华为,荣�?	IOS :[IOS Version],[Model] 例如: IOS 7, IPhone5
    private String areaCode;    //区域�?
    private double lat;    //维度
    private double lng;    //经度
    private String statisticsType;    //统计类型,如果参与统计请设定该字段
    private String deviceId = "867695022287709";
    private int terminalSource = 2;
    private String terminalSourceVersion = "1.2.22";
    private String sdk = Build.VERSION.RELEASE;
    private String brand = Build.BRAND;
    private String model = Build.MODEL;
    private String board = Build.BOARD;
    private String product = Build.PRODUCT;

    public static ClientCommonInfo getInstance() {
        return clientInfo;
    }

    public static Map<String, String> getClientInfo() {
        Map<String, String> map = new HashMap<>();
        Class<? extends ClientCommonInfo> clazz = ClientCommonInfo.getInstance().getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            if (!Modifier.isStatic(modifiers)) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                try {
                    Object object = field.get(ClientCommonInfo.getInstance());
                    map.put("exiu_" + field.getName(), null == object ? null : object.toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    public String getTerminalSourceDesc() {
        return terminalSourceDesc;
    }

    public void setTerminalSourceDesc(String terminalSourceDesc) {
        this.terminalSourceDesc = terminalSourceDesc;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getStatisticsType() {
        return statisticsType;
    }

    public void setStatisticsType(String statisticsType) {
        this.statisticsType = statisticsType;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public int getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(int currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getTerminalSource() {
        return terminalSource;
    }

    public void setTerminalSource(int terminalSource) {
        this.terminalSource = terminalSource;
    }

    public String getTerminalSourceVersion() {
        return terminalSourceVersion;
    }

    public void setTerminalSourceVersion(String terminalSourceVersion) {
        this.terminalSourceVersion = terminalSourceVersion;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
