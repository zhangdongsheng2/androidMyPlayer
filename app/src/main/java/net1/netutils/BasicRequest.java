package net1.netutils;

public class BasicRequest {
    private ClientCommonInfo clientCommonInfo;
    private Object param;

    public ClientCommonInfo getClientCommonInfo() {
        return clientCommonInfo;
    }

    public void setClientCommonInfo(ClientCommonInfo clientCommonInfo) {
        this.clientCommonInfo = clientCommonInfo;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }
}
