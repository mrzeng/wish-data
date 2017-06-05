package com.raycloud.overseas.erp.data.common.util;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhanxf on 16/7/18.
 */
public class ListUtil {
    /**
     * 判断列表是否为空
     * @param list
     * @return
     */
    public static boolean isBlank(List<?> list){
        if(list==null||list.size()==0){
            return true;
        }
        return false;
    }

    /**
     * 字符串转列表
     * @param s
     * @param pattern
     * @return
     */
    public static List<String> strToList(String s,String pattern){

        if(StringUtils.isEmpty(s)){
            return null;
        }

        String[] sa = s.split(pattern);
        List<String> list = new ArrayList<String>();
        for(String s1:sa){
            list.add(s1);
        }
        return list;
    }

    /**
     * 字符串转列表
     * @param s
     * @param pattern
     * @return
     */
    public static List<Integer> strToIList(String s,String pattern){

        if(StringUtils.isEmpty(s)){
            return null;
        }

        String[] sa = s.split(pattern);
        List<Integer> list = new ArrayList<Integer>();
        for(String s1:sa){
            list.add(Integer.parseInt(s1));
        }
        return list;
    }

    /**
     * 字符串转列表
     * @param s
     * @param pattern
     * @return
     */
    public static Set<String> strToSet(String s,String pattern){

        if(StringUtils.isEmpty(s)){
            return null;
        }

        String[] sa = s.split(pattern);
        Set<String> set = new HashSet<String>();
        for(String s1:sa){
            set.add(s1);
        }
        return set;
    }

    /**
     * 列表转字符串
     * @param pattern
     * @return
     */
    public static String listToStr( List<String> list,String pattern){

        if(isBlank(list)){
            return "";
        }else{
            StringBuffer sb = new StringBuffer();
            for(String s : list){
                sb.append(s+pattern);
            }
            return sb.toString();
        }
    }

    public static List setToList(Set set){
        if(set == null || set.size()==0){
            return null;
        }
        List list = new ArrayList(set.size());
        list.addAll(set);
        return list;
    }

    /**
     * list 去重方法
     * @param list
     */
    public static void removeDuplicate(List list) {
        if (list == null || list.size() == 0)
            return;
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
    }

    public static int countListLen(List...lists){
        if(lists==null){
            return 0;
        }
        int sum = 0;
        for(List list : lists){
            if(list!=null){
                sum+=list.size();
            }
        }
        return sum;
    }
}
