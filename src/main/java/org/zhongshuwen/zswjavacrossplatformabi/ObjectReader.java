package org.zhongshuwen.zswjavacrossplatformabi;

import com.google.gson.internal.LinkedTreeMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;

public class ObjectReader {

    public static String[] getFieldList(Object obj) throws Exception {
        if(obj == null){
            return new String[]{};
        }
        if(obj instanceof LinkedTreeMap){
            LinkedTreeMap<String, Object> ltm = (LinkedTreeMap<String, Object>) obj;
            return (String[]) ltm.keySet().toArray();
        }else if(obj instanceof Dictionary){
            Enumeration<String> e = ((Dictionary) obj).keys();
            ArrayList<String> keys = new ArrayList<>();
            while(e.hasMoreElements()) {
                keys.add(e.nextElement());
            }
            return (String[])keys.toArray();
        }else{
            /*
            Field[] fields = obj.getClass().getDeclaredFields();
            for(Field f : fields){
                if(f.canAccess(obj)){
                    f.getAnnotatedType().
                }
            }

             */
            throw new Exception("Unable to get field list for type!");

        }

    }
    public static Object getField(Object o, String fieldName){
        if(o == null){
            return  null;

        }else if (o instanceof  LinkedTreeMap){
            return ((LinkedTreeMap<String, Object>)o).get(fieldName);
        }else if(o instanceof Dictionary){
            return ((Dictionary<String, Object>)o).get(fieldName);
        }else{
            throw new Error("Unsupported object type!");
        }

    }
}
