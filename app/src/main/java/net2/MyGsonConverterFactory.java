package net2;

import com.google.gson.Gson;
import com.socks.library.KLog;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by zhangdongsheng on 16/9/4.
 */
public class MyGsonConverterFactory extends Converter.Factory {
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new UserRequestBodyConverter<>();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        //根据type判断是否是自己能处理的类型，不能的话，return null ,交给后面的Converter.Factory
        return new UserResponseConverter<>(type);
    }
//===============================结果类型判断User or List<User>==============================================
//    @Override
//    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit)
//    {
//        //根据type判断是否是自己能处理的类型，不能的话，return null ,交给后面的Converter.Factory
//        if (type == User.class)//支持返回值是User
//        {
//            return new UserResponseConverter(type);
//        }
//
//        if (type instanceof ParameterizedType)//支持返回值是List<User>
//        {
//            Type rawType = ((ParameterizedType) type).getRawType();
//            Type actualType = ((ParameterizedType) type).getActualTypeArguments()[0];
//            if (rawType == List.class && actualType == User.class)
//            {
//                return new UserResponseConverter(type);
//            }
//        }
//        return null;
//    }
//
//
//    class UserResponseConverter<T> implements Converter<ResponseBody, T>
//    {
//        private Type type;
//        Gson gson = new Gson();
//
//        public UserResponseConverter(Type type)
//        {
//            this.type = type;
//        }
//
//        @Override
//        public T convert(ResponseBody responseBody) throws IOException
//        {
//            String result = responseBody.string();
//
//            if (result.startsWith("["))
//            {
//                return (T) parseUsers(result);
//            } else
//            {
//                return (T) parseUser(result);
//            }
//        }
//
//        private User parseUser(String result)
//        {
//            JSONObject jsonObject = null;
//            try
//            {
//                jsonObject = new JSONObject(result);
//                User u = new User();
//                u.setUsername(jsonObject.getString("username"));
//                return u;
//            } catch (JSONException e)
//            {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        private List<User> parseUsers(String result)
//        {
//            List<User> users = new ArrayList<>();
//            try
//            {
//                JSONArray jsonArray = new JSONArray(result);
//                User u = null;
//                for (int i = 0; i < jsonArray.length(); i++)
//                {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    u = new User();
//                    u.setUsername(jsonObject.getString("username"));
//                    users.add(u);
//                }
//            } catch (JSONException e)
//            {
//                e.printStackTrace();
//            }
//            return users;
//        }

    //==============响应===================
    class UserResponseConverter<T> implements Converter<ResponseBody, T> {
        Gson gson = new Gson();
        private Type type;

        public UserResponseConverter(Type type) {
            this.type = type;
        }

        @Override
        public T convert(ResponseBody responseBody) throws IOException {
            String result = responseBody.string();
//            KLog.e(result);
//            T users = gson.fromJson(result, type);


            Class c = this.getClass();
            Type t = c.getGenericSuperclass();
            if (t instanceof ParameterizedType) {
//          System.out.println("in if");
                Type[] p = ((ParameterizedType) t).getActualTypeArguments();
//          System.out.println(Arrays.toString(p));
                KLog.e("class" + (Class<T>) p[0]);
            }
            KLog.e("type" + t);
            return (T) result;
        }
    }

    //=================请求====================
    class UserRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private Gson mGson = new Gson();

        @Override
        public RequestBody convert(T value) throws IOException {
            String string = mGson.toJson(value);
            return RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), string);
        }
    }
}
