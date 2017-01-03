package net1;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @Author 冯高远
 * @Date 2016/7/29 16:44
 * @Description
 */
public class Json extends JSONObject {


    public Json(String result) throws JSONException {
        super(result);
    }

    @Override
    public int getInt(String name) throws JSONException {
        if (!has(name)) {
            return -1;
        }
        return super.getInt(name);
    }

    @Override
    public String getString(String name) throws JSONException {
        if (!has(name)) {
            return null;
        }
        return super.getString(name);
    }
}
