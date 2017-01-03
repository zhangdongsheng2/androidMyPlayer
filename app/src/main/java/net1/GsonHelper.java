package net1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonHelper {
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private GsonHelper() {
    }

    public static <T> T fromJson(String json, Class<T> class1) {
        return gson.fromJson(json, class1);
    }

    /**
     * Type是ArrayList<Integer>
     *
     * @param json
     * @return
     */
    public static <T> T fromJson(String json) {
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static <T> List<T> fromJsonList(String resultInfo, Class<T> class1) {
        List<T> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(resultInfo);
            for (int i = 0; i < jsonArray.length(); i++) {
                T fromJson = fromJson(jsonArray.get(i).toString(), class1);
                list.add(fromJson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    public static <T> String toJson(T t) {
        return gson.toJson(t);
    }

    public static <T> T clone(T mModel) {
        T fromJson = (T) gson.fromJson(gson.toJson(mModel), mModel.getClass());
        return fromJson;
    }

}
