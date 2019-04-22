package com.qihui.sourcedemo.mybatis.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenqihui
 * @date 2019/4/22
 */
public class SQLUtils {
    public static String[] sqlInsertParameter(String sql) {
        int startIndex = sql.indexOf("values");
        int endIndex = sql.length();
        String substring = sql.substring(startIndex + 6, endIndex).replace("(", "").replace(")", "").replace("#{", "")
                .replace("}", "").replace(" ", "");
        String[] split = substring.split(",");
        return split;
    }

    public static List<String> sqlSelectParameter(String sql) {
        int startIndex = sql.indexOf("where");
        int endIndex = sql.length();
        String substring = sql.substring(startIndex + 5, endIndex);
        String[] split = substring.split("and");
        List<String> listArr = new ArrayList<>();
        for (String string : split) {
            String[] sp2 = string.split("=");
            listArr.add(sp2[0].trim());
        }
        return listArr;
    }

    public static String parameQuestion(String sql, String[] parameterName) {
        for (int i = 0; i < parameterName.length; i++) {
            String string = parameterName[i];
            sql = sql.replace("#{" + string + "}", "?");
        }
        return sql;
    }

    public static String parameQuestion(String sql, List<String> parameterName) {
        for (int i = 0; i < parameterName.size(); i++) {
            String string = parameterName.get(i);
            sql = sql.replace("#{" + string + "}", "?");
        }
        return sql;
    }
}
