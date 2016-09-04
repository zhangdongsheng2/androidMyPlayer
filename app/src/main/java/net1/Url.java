package net1;

/**
 * URL，网络层使用，其它人不需要关心
 *
 * @author 冯高远
 */
public class Url {

    private static String HOSTNAME = "http://api.114995.com";

    public static String getHostName() {
        return HOSTNAME;
    }

    public static void setHostName(String hOSTNAME) {
        HOSTNAME = hOSTNAME;
    }


    public static class Store {
        private static final String NAME = HOSTNAME + "/api/Store/";
        public static final String QueryRescueStores = NAME + "QueryRescueStores";

    }
}
