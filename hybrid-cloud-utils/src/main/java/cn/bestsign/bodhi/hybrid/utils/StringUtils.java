package org.github.bodhi.hybrid.utils;


import java.util.HashMap;
import java.util.Map;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-06 13:05
 **/
public class StringUtils {

    public static boolean isNullOrEmpty(String str){
        if (str==null||str.length()==0){
            return true;
        }
        return false;
    }

    public static String combinePath(String host,String path){
        if (host==null||host.length()==0){
            throw new IllegalArgumentException("host can't is empty");
        }
        if (path==null||path.length()==0){
            throw new IllegalArgumentException("path can't is empty");
        }
        if(!host.startsWith("http://")&&!host.startsWith("https://")){
            throw new IllegalArgumentException("protocol not supported");
        }

        if(host.endsWith("/")){
            host = host.substring(0,host.length()-1);
        }
        if(!path.startsWith("/")){
            path = "/"+path;
        }

        return host+path;
    }

    public static String combineParams(Map<String,String> params){
        StringBuilder stringBuilder = new StringBuilder("?");
        int size = params.size();
        int count = 0;
        for(Map.Entry entry : params.entrySet()){
            count++;
            stringBuilder.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue());
            if (size>count){
                stringBuilder.append("&");
            }
        }
        return stringBuilder.toString();
    }


    public static Map<String,String> templateMatch(String template, String source){
        Map<String,Integer> indexMap = new HashMap<String, Integer>();
        Map<String,String> valueMap = new HashMap<String, String>();

        String[] parts = template.split("/");
        for(int i = 0;i<parts.length;i++){
            String part = parts[i];
            if (part.startsWith("{")&&part.endsWith("}")){
                String param = part.replace("{","").replace("}","");
                indexMap.put(param,i);
            }
        }

        String[] values = source.split("/");
        for(Map.Entry<String,Integer> entry :indexMap.entrySet()){
            valueMap.put(entry.getKey(),values[entry.getValue()]);
        }

        return valueMap;
    }


    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0))){
            return s;
        }else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }
}
