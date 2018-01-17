package com.samboy.contactproject.app;

import android.database.Cursor;
import android.util.Log;

import com.samboy.contactproject.webservices.SC;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by HariGroup on 15/11/2017.
 */

public class Parse {
    private static final String STATUS="status";
    public static final String DATA="contacts";
    public static boolean isSuccess(String result) {
        try {
            return new JSONObject(result).getString(STATUS).equals("success");
        } catch (Exception e) {
        }
        return false;
    }
    public static boolean isUserRegistered(String result) {
        try {
            return !(new JSONObject(result).getString(DATA).equals(SC.MSG_USER_NOT_REGISTERED));
        } catch (Exception e) {
        }
        return true;
    }

    public static boolean needLogout(String response) {
        try {
            JSONObject object=new JSONObject(response);
            return object.getString(STATUS).equals("error") && object.getString(DATA).equals("logout");
        } catch (Exception e) {
        }
        return false;
    }

    public static JSONObject getData(String response) throws Exception {
        return new JSONObject(response).getJSONObject(DATA);
    }
    public static String getError(String result,String defRetuen) {
        try {
            JSONObject ob=new JSONObject(result);
            return ob.getString(STATUS).equals("error")?ob.getString(DATA):defRetuen;
        } catch (Exception e) {
        }
        return defRetuen;
    }
    public static boolean isOtpNotVerified(String result) {
        try {
            JSONObject ob=new JSONObject(result);
            return ob.getString(DATA).equals(SC.MSG_OTP_NOT_VARIFIED);
        } catch (Exception e) {
        }
        return false;
    }

    private static List<Field> getFields(Class<?> cls) {
        List<Field> fields=new ArrayList<>();
        try {
            fields.addAll(Arrays.asList(cls.getDeclaredFields()));
            if(cls.getSuperclass()!=null) {
                fields.addAll(getFields(cls.getSuperclass()));
            }
        } catch (SecurityException e) {
        }
        return fields;
    }
    private static Field [] getFieldsByArray(Class<?> cls) {
        List<Field> fields=getFields(cls);
        return fields.toArray(new Field[fields.size()]);
    }


    public static <T> List<T> getList(Class<T> cls, JSONObject jsonObject) {
        List<T> list = new ArrayList<>();
        Field[] fields = getFieldsByArray(cls);
        Iterator<String> keys = jsonObject.keys();
        JSONArray details = new JSONArray();
        //Log.e("classCls", "=" + cls.getSimpleName());
        while (keys.hasNext()) {
            String key = keys.next();
            try {
                details = jsonObject.getJSONArray(key);
                break;
            } catch (Exception e) {
            }
        }
//        if(cls.equals(SubCategoryModel.class)) {
//            Log.e("getList","="+fields.length+"  "+details.length());
//        }
        for (int i = 0, j = details.length(); i < j; i++) {
            try {
                if (cls.equals(Integer.class)) {
                    Constructor<T> con = cls.getConstructor(int.class);
                    list.add(con.newInstance(details.optInt(i)));
                } else if (cls.equals(String.class)) {
                    Constructor<T> con = cls.getConstructor(String.class);
                    list.add(con.newInstance(details.optString(i)));
                } else if (cls.equals(Long.class)) {
                    Constructor<T> con = cls.getConstructor(long.class);
                    list.add(con.newInstance(details.optLong(i)));
                } else if (cls.equals(Float.class)) {
                    Constructor<T> con = cls.getConstructor(double.class);
                    list.add(con.newInstance(details.optDouble(i,0)));
                } else if (cls.equals(Double.class)) {
                    Constructor<T> con = cls.getConstructor(double.class);
                    list.add(con.newInstance(details.optDouble(i,0)));
                } else {
                    list.add(getClass(cls, details.getJSONObject(i), fields));
                }
            } catch (Exception e) {
                break;
            }
        }
        return list;
    }

    public static <T> T getClass(Class<T> cls, JSONObject jsonObject) throws Exception {
        return getClass(cls, jsonObject, getFieldsByArray(cls));
    }

    private static <T> T getClass(Class<T> cls, JSONObject jsonObject, Field[] fields) throws Exception {
        Class<?> cl;
        Constructor<T> con = cls.getConstructor();
        T object = con.newInstance();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                cl = field.getType();
                if (cl.equals(int.class)) {
                    field.set(object, jsonObject.optInt(field.getName()));
                } else if (cl.equals(String.class)) {
                    field.set(object, jsonObject.optString(field.getName(), ""));
                }  else if (cl.equals(boolean.class)) {
                    field.set(object, jsonObject.optBoolean(field.getName(), false));
                } else if (cl.equals(long.class)) {
                    field.set(object, jsonObject.optLong(field.getName()));
                } else if (cl.equals(float.class)) {
                    field.set(object, (float) jsonObject.optDouble(field.getName()));
                } else if (cl.equals(double.class)) {
                    field.set(object, jsonObject.optDouble(field.getName()));
                } else if (cl.equals(List.class) || cl.equals(ArrayList.class)) {

                } else if (cl.equals(int[].class)) {
                    List<Integer> list = getList(Integer.class, new JSONObject().put(DATA,jsonObject.getJSONArray(field.getName())));
                    int [] arr=new int[list.size()];
                    for(int i=0;i<list.size();i++) {
                        arr[i]=list.get(i);
                    }
                    field.set(object, arr);
                } else if (cl.equals(String[].class)) {
                    List<String> list = getList(String.class, new JSONObject().put(DATA,jsonObject.getJSONArray(field.getName())));
                    String [] arr=new String[list.size()];
                    for(int i=0;i<list.size();i++) {
                        arr[i]=list.get(i);
                    }
                    field.set(object, arr);
                } else if (cl.equals(long[].class)) {
                    List<Long> list = getList(Long.class, new JSONObject().put(DATA,jsonObject.getJSONArray(field.getName())));
                    long [] arr=new long[list.size()];
                    for(int i=0;i<list.size();i++) {
                        arr[i]=list.get(i);
                    }
                    field.set(object, arr);
                } else if (cl.equals(float[].class)) {
                    List<Float> list = getList(Float.class, new JSONObject().put(DATA,jsonObject.getJSONArray(field.getName())));
                    float [] arr=new float[list.size()];
                    for(int i=0;i<list.size();i++) {
                        arr[i]=list.get(i);
                    }
                    field.set(object, arr);
                } else if (cl.equals(double[].class)) {

                    List<Double> list = getList(Double.class, new JSONObject().put(DATA,jsonObject.getJSONArray(field.getName())));
                    double [] arr=new double[list.size()];
                    for(int i=0;i<list.size();i++) {
                        arr[i]=list.get(i);
                    }
                    field.set(object, arr);
                }
//                else if (cl.equals(SubCategoryModel[].class)) {
//                    Log.e("Parse","="+new JSONObject().put(DATA,jsonObject.getJSONArray(field.getName())).toString());
//                    List<SubCategoryModel> list = getList(SubCategoryModel.class, new JSONObject().put(DATA,jsonObject.getJSONArray(field.getName())));
//                    SubCategoryModel [] arr=new SubCategoryModel[list.size()];
//                    Log.e("Cat","="+arr.length);
//                    for(int i=0;i<list.size();i++) {
//                        arr[i]=list.get(i);
//                    }
//                    field.set(object, arr);
//                }
                else {
                    field.set(object, getClass(cl, jsonObject.getJSONObject(field.getName())));
                }
            } catch (Exception e) {
            }
        }
        return object;
    }

    public static <T> T getClass(Class<T> cls, Cursor cursor) {
        if(!cursor.moveToNext()) {
            return null;
        }
        JSONObject object=getJobjectFromCursor(cursor);
        if(object==null) {
            return null;
        }
        try {
            return getClass(cls,object);
        } catch (Exception e) {
        }
        return null;
    }
    public static JSONObject getJobjectFromCursor(Cursor cursor) {

        JSONObject object=new JSONObject();
        try {
            int clmCount=cursor.getColumnCount();
            for(int i=0;i<clmCount;i++) {
                object.put(cursor.getColumnName(i),cursor.getString(i));
            }
            return object;
        } catch (Exception e) {
        }
        return null;
    }
    public static <T> List<T> getList(Class<T> cls, Cursor cursor) {
        List<T> list = new ArrayList<>();
        Field[] fields = getFieldsByArray(cls);;
        JSONArray details = new JSONArray();

        while(cursor.moveToNext()) {
            try {
                details.put(getJobjectFromCursor(cursor));
            } catch (Exception e) {
            }
        }
        for (int i = 0, j = details.length(); i < j; i++) {
            try {
                if (cls.equals(Integer.class)) {
                    Constructor<T> con = cls.getConstructor(int.class);
                    list.add(con.newInstance(details.optInt(i)));
                } else if (cls.equals(String.class)) {
                    Constructor<T> con = cls.getConstructor(String.class);
                    list.add(con.newInstance(details.optString(i)));
                } else if (cls.equals(Long.class)) {
                    Constructor<T> con = cls.getConstructor(long.class);
                    list.add(con.newInstance(details.optLong(i)));
                } else if (cls.equals(Float.class)) {
                    Constructor<T> con = cls.getConstructor(double.class);
                    list.add(con.newInstance(details.optDouble(i,0)));
                } else if (cls.equals(Double.class)) {
                    Constructor<T> con = cls.getConstructor(double.class);
                    list.add(con.newInstance(details.optDouble(i,0)));
                } else {
                    list.add(getClass(cls, details.getJSONObject(i), fields));
                }
            } catch (Exception e) {
                break;
            }
        }
        return list;
    }
}
