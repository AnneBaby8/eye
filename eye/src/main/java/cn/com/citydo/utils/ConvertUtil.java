package cn.com.citydo.utils;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.util.*;

/**
 * <p>
 *  实体和map<String,Object>转化
 * </p>
 *
 * @author wx227336
 * @Date 2021/6/4 15:59
 * @Version 1.0
 */
public class ConvertUtil {

    /**
     * 将Object对象里面的属性和值转化成Map对象
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap<String,Object>();
        Class<?> clazz = obj.getClass();
        List<Field> fields = new ArrayList<>();
        try {
            //把父类包含的字段遍历出来
            while (clazz!=null){
                fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
                clazz = clazz.getSuperclass();

            }
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(obj);
                map.put(fieldName, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     *  转为新列表(对象属性名要相同)
     * @param originList 原列表
     * @param tClass 新列表类对象
     * @param <T>
     * @return
     */
    public static <T> List<T> listToOtherList(List originList,Class<T> tClass){
        List<T> list = new ArrayList<>();
        for (Object info : originList) {
            T t = JSON.parseObject(JSON.toJSONString(info),tClass);
            list.add(t);
        }
        return list;
    }
}
