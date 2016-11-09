package com.yangzhiyan.qqreader.utils;

import java.util.List;

/**
 * Created by YZY on 2016/10/29.
 */

public class ConverUtility {
    private ConverUtility(){}

    public static String convertBookIdToBookImageUrl(String bookId){
        String last3 = null;

        last3 = String.valueOf(Integer.parseInt(bookId)%1000);
        String url = "http://wfqqreader.3g.qq.com/cover/" + last3 + "/" +
                bookId + "/t5_" + bookId + ".jpg";
        return url;
    }
    public static  String join(String[] items,String sep){
        if (items == null || items.length == 0){
            return "";
        }
        StringBuffer result = new StringBuffer();
        for (int i=0;i<items.length;i++){
            result.append(items[i]);
            if (i != items.length-1){
                result.append(sep);
            }
        }
        return result.toString();
    }
    public static String join(List<String> items,String sep){
        if (items == null || items.size() == 0){
            return "";
        }
        StringBuffer result = new StringBuffer();
        for (int i=0;i<items.size();i++){
            result.append(items.get(i));
            if (i != items.size()-1){
                result.append(sep);
            }
        }
        return result.toString();
    }

}
