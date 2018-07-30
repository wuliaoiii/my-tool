package com.yangy.codetool.model;

import org.apache.commons.lang3.StringUtils;

/**
 * 代码生成器对象
 *
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/6/13
 * @since 1.0.0
 */
public class TableClass {
    private String prefix;
    private String tableName;
    private String tableAnnotation;
    private String changeTableName;

    public String getPrefix() {
        String[] split = tableName.split("_");
        return split[0] + "_";
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableAnnotation() {
        return tableAnnotation;
    }

    public void setTableAnnotation(String tableAnnotation) {
        this.tableAnnotation = tableAnnotation;
    }

    public String getChangeTableName() {
        return replaceUnderLineAndUpperCase(tableName);
    }

    /**
     * 去掉下划线
     *
     * @param str
     * @return
     */
    public String replaceUnderLineAndUpperCase(String str) {
        String replace = str.replace(getPrefix(), "");
        StringBuffer sb = new StringBuffer();
        sb.append(replace);
        int count = sb.indexOf("_");
        while (count != 0) {
            int num = sb.indexOf("_", count);
            count = num + 1;
            if (num != -1) {
                char ss = sb.charAt(count);
                char ia = (char) (ss - 32);
                sb.replace(count, count + 1, ia + "");
            }
        }
        String result = sb.toString().replaceAll("_", "");
        return StringUtils.capitalize(result);
    }
}