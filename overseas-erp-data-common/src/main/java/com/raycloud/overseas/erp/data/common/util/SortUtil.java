package com.raycloud.overseas.erp.data.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhanxf on 17/2/28.
 */
public class SortUtil {

    private static Pattern linePattern = Pattern.compile("_(\\w)");

    public static Map<String,Class> propertyClassMap = new HashMap<String,Class>();

    static {
        propertyClassMap.put("amount_7",Integer.class);
        propertyClassMap.put("item_count",Integer.class);
        propertyClassMap.put("price_7",Double.class);
        propertyClassMap.put("new_save_7",Integer.class);
        propertyClassMap.put("item_count",Integer.class);
        propertyClassMap.put("cat_amount_rank",Integer.class);
    }

    public static void sortListByProperty(final List list, final String sortField, final String order){
        Collections.sort(list, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                try {
                    Class propertyClass = propertyClassMap.get(sortField);
                    if(propertyClass == null){
                        return 0;
                    }
                    Method method = o1.getClass().getMethod("get"+lineToHump(sortField),new Class[]{});

                    Object v1 = method.invoke(o1, new Object[]{});
                    Object v2 = method.invoke(o1, new Object[]{});
                    if(propertyClass == Integer.class){
                        Integer i1 = (Integer) v1;
                        Integer i2 = (Integer) v2;
                        if(order.equalsIgnoreCase("DESC")){
                            return i1==v2?0:i1>i2?-1:1;
                        }else{
                            return i1==v2?0:i1<i2?-1:1;
                        }
                    }else if(propertyClass == Double.class){
                        Double d1 = (Double) v1;
                        Double d2 = (Double) v2;
                        int result = d1.compareTo(d2);
                        if(order.equalsIgnoreCase("DESC")){
                            return result == 0?result:result==-1?1:-1;
                        }
                        return result;
                    }else if(propertyClass == String.class){
                        String s1 = (String) v1;
                        String s2 = (String) v2;
                        if(s1!=null&&s2!=null){
                            int result = s1.compareTo(s2);
                            if(order.equalsIgnoreCase("DESC")){
                                return result == 0?result:result==-1?1:-1;
                            }
                        }else{
                            return 0;
                        }
                    }else if(propertyClass == Date.class){
                        Date date1 = (Date) v1;
                        Date date2 = (Date) v2;
                        if(date1!=null&&date2!=null){
                            int result = date1.compareTo(date2);
                            if(order.equalsIgnoreCase("DESC")){
                                return result == 0?result:result==-1?1:-1;
                            }
                        }else{
                            return 0;
                        }
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                return 0;
            }
        });
    }


    /**下划线转驼峰*/
    public static String lineToHump(String str){
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        String tmp = sb.toString();
        tmp = tmp.substring(0,1).toUpperCase()+tmp.substring(1);
        return tmp;
    }
    /**驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)})*/
    public static String humpToLine(String str){
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }
    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    /**驼峰转下划线,效率比上面高*/
    public static String humpToLine2(String str){
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    public static void main(String[] args) {
        String lineToHump = lineToHump("f_parent_no_leader");
        System.out.println(lineToHump);//fParentNoLeader
        System.out.println(humpToLine(lineToHump));//f_parent_no_leader
        System.out.println(humpToLine2(lineToHump));//f_parent_no_leader
    }


}
