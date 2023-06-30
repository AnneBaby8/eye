package cn.com.citydo.utils;




import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * HTTP工具类
 * </p>
 * @author
 * @since 2021-06-07
 */
@Slf4j
public class HttpUtil {

    public static String urlEncoderText(String text) {
        String result = "";
        try {
            result = java.net.URLEncoder.encode(text, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 构造Get请求参数
     * @param url
     * @param params
     */
    public static String buildHttpGetParam(String url, Map<String, String> params) {
        Set<String> keys = params.keySet();
        StringBuffer arg = new StringBuffer("?");
        for (String key : keys) {
            arg.append((key) + "=" + urlEncoderText(params.get(key)) + "&");
        }
        return url + arg.deleteCharAt(arg.length() -1).toString();//此处为了兼容case内容为空
    }


    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("abc", "123");
        map.put("bcd", "4444");
        System.out.println(HttpUtil.buildHttpGetParam("www.baidu.com", map));
    }

}
