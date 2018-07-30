package com.yangy.codetool.utils;

import com.alibaba.fastjson.JSON;
import com.yangy.codetool.config.GenerateConfig;
import com.yangy.codetool.model.ColumnClass;
import com.yangy.codetool.model.TableClass;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：代码生成器
 * Created by Ay on 2017/5/1.
 */
public class CodeGenerateUtils {

    public static void main(String[] args) throws Exception {
        CodeGenerateUtils codeGenerateUtils = new CodeGenerateUtils();
        codeGenerateUtils.generateAll();
    }

    /**
     * 获取连接
     *
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        Class.forName(GenerateConfig.getDRIVER());
        Connection connection = DriverManager.getConnection(GenerateConfig.getURL(), GenerateConfig.getUSER(), GenerateConfig.getPASSWORD());
        return connection;
    }

    /**
     * 生成当前数据库中所有表的代码
     *
     * @throws Exception
     */
    public void generateAll() {
        try {
            List<TableClass> tables = getTables();

            for (TableClass table : tables) {
                generate(table);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {

        }
    }


    /**
     * 生成单个表的代码
     *
     * @param tableClass
     * @throws Exception
     */
    public void generate(TableClass tableClass) throws Exception {
        try {
            Connection connection = getConnection();
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getColumns(null, "%", tableClass.getTableName(), "%");

            System.out.println(JSON.toJSONString(resultSet));


//            //生成Api文件
//            generateApiFile(resultSet, tableClass);
//            //生成Hystrix文件
//            generateHystrixFile(resultSet, tableClass);
//            //生成Mybatis文件
//            generateMybatisFile(resultSet, tableClass);
//            //生成Mapper文件
//            generateMapperFile(resultSet, tableClass);
//            //生成服务实现层文件
//            generateServiceImplFile(resultSet, tableClass);
//            //生成服务层接口文件
//            generateServiceInterfaceFile(resultSet, tableClass);
//            //生成Controller层文件
//            generateControllerFile(resultSet, tableClass);
//
//            ResultSet resultSet2 = databaseMetaData.getColumns(null, "%", tableClass.getTableName(), "%");
//            //生成Model文件
//            generateEntityFile(resultSet2, tableClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {

        }
    }

    /**
     * 生成控制器
     *
     * @param resultSet
     * @throws Exception
     */
    private void generateApiFile(ResultSet resultSet, TableClass tableClass) throws Exception {
        final String suffix = "Api.java";
        String dicPath = GenerateConfig.getDISKPATH() + "api/";
        final String path = dicPath + tableClass.getChangeTableName() + suffix;
        final String templateName = "api.ftl";
        makeFile(dicPath, suffix);
        File mapperFile = new File(path);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, mapperFile, dataMap, tableClass);
    }

    /**
     * 生成控制器
     *
     * @param resultSet
     * @throws Exception
     */
    private void generateHystrixFile(ResultSet resultSet, TableClass tableClass) throws Exception {
        final String suffix = "Hystrix.java";
        String dicPath = GenerateConfig.getDISKPATH() + "api/hystrix/";
        final String path = dicPath + tableClass.getChangeTableName() + suffix;
        final String templateName = "hystrix.ftl";
        makeFile(dicPath, suffix);
        File mapperFile = new File(path);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, mapperFile, dataMap, tableClass);
    }

    /**
     * 生成控制器层的代码
     *
     * @param resultSet
     * @param tableClass
     * @throws Exception
     */
    private void generateControllerFile(ResultSet resultSet, TableClass tableClass) throws Exception {
        final String suffix = "Controller.java";
        String dicPath = GenerateConfig.getDISKPATH() + "controller/";
        final String path = dicPath + tableClass.getChangeTableName() + suffix;
        final String templateName = "controller.ftl";
        makeFile(dicPath, suffix);
        File mapperFile = new File(path);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, mapperFile, dataMap, tableClass);
    }

    /**
     * 生成实体类
     *
     * @param resultSet
     * @throws Exception
     */
    private void generateEntityFile(ResultSet resultSet, TableClass tableClass) throws Exception {

//        changeTableName = replaceUnderLineAndUpperCase(tableClass.tableName);

        final String suffix = ".java";
        String dicPath = GenerateConfig.getDISKPATH() + "entity/";
        final String path = dicPath + tableClass.getChangeTableName() + suffix;
        final String templateName = "entity.ftl";
        makeFile(dicPath, suffix);
        File mapperFile = new File(path);
        Map<String, Object> dataMap = getEntityMap(resultSet, tableClass);
        generateFileByTemplate(templateName, mapperFile, dataMap, tableClass);

    }

    /**
     * 生成mapper
     *
     * @param resultSet
     * @throws Exception
     */
    private void generateMapperFile(ResultSet resultSet, TableClass tableClass) throws Exception {
        final String suffix = "Mapper.java";
        String dicPath = GenerateConfig.getDISKPATH() + "dao/";
        final String path = dicPath + tableClass.getChangeTableName() + suffix;
        final String templateName = "mapper.ftl";
        makeFile(dicPath, suffix);
        File mapperFile = new File(path);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, mapperFile, dataMap, tableClass);

    }

    /**
     * 生成关系映射层代码
     *
     * @param resultSet
     * @throws Exception
     */
    private void generateMybatisFile(ResultSet resultSet, TableClass tableClass) throws Exception {
        String dicPath = GenerateConfig.getRESOURCEPATH() + "mybatis/";
        final String suffix = "Mapper.xml";
        final String path = dicPath + tableClass.getChangeTableName() + suffix;
        final String templateName = "mybatis.ftl";
        makeFile(dicPath, suffix);
        File mapperFile = new File(path);
        Map<String, Object> dataMap = getEntityMap(resultSet, tableClass);
        generateFileByTemplate(templateName, mapperFile, dataMap, tableClass);
    }

    /**
     * 生成业务实现层代码
     *
     * @param resultSet
     * @throws Exception
     */
    private void generateServiceImplFile(ResultSet resultSet, TableClass tableClass) throws Exception {
        final String suffix = "ServiceImpl.java";
        String dicPath = GenerateConfig.getDISKPATH() + "service/impl/";
        final String path = dicPath + tableClass.getChangeTableName() + suffix;
        final String templateName = "serviceImpl.ftl";
        makeFile(dicPath, suffix);
        File mapperFile = new File(path);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, mapperFile, dataMap, tableClass);
    }

    /**
     * 生成业务抽象层代码
     *
     * @param resultSet
     * @throws Exception
     */
    private void generateServiceInterfaceFile(ResultSet resultSet, TableClass tableClass) throws Exception {
        final String suffix = "Service.java";
        String dicPath = GenerateConfig.getDISKPATH() + "service/";
        final String path = dicPath + tableClass.getChangeTableName() + suffix;
        final String templateName = "service.ftl";
        makeFile(dicPath, suffix);
        File mapperFile = new File(path);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, mapperFile, dataMap, tableClass);
    }

    /**
     * 根据模板生成文件
     *
     * @param templateName
     * @param file
     * @param dataMap
     * @throws Exception
     */
    private void generateFileByTemplate(final String templateName, File file, Map<String, Object> dataMap, TableClass tableClass) throws Exception {
        Template template = FreeMarkerTemplateUtils.getTemplate(templateName);
        FileOutputStream fos = new FileOutputStream(file);
        dataMap.put("table_name_small", tableClass.getTableName());
        dataMap.put("table_name", tableClass.getChangeTableName());
        dataMap.put("author", GenerateConfig.getAUTHOR());
        dataMap.put("date", GenerateConfig.getDATE());
        dataMap.put("package_name", GenerateConfig.getPACKAGENAME());
        dataMap.put("table_annotation", tableClass.getTableAnnotation());
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        template.process(dataMap, out);
    }

    /**
     * 创建文件夹
     *
     * @param dicPath
     * @param filePath
     */
    private void makeFile(String dicPath, String filePath) {
        File file = new File(dicPath);
        boolean exists = file.exists();
        if (!exists) {
            try {
                boolean mkdirs = file.mkdirs();
                boolean newFile = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 去掉下划线
     *
     * @param str
     * @return
     */
    public String replaceUnderLineAndUpperCase(String str, TableClass tableClass) {
        String replace = str.replace(tableClass.getPrefix(), "");
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

    /**
     * 获取表中的字段
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private Map<String, Object> getEntityMap(ResultSet resultSet, TableClass tableClass) throws SQLException {
        List<ColumnClass> columnClassList = new ArrayList<>();
        ColumnClass columnClass = null;
        while (resultSet.next()) {
            //id字段略过
//            if (resultSet.getString("COLUMN_NAME").equals("id")) continue;
            columnClass = new ColumnClass();
            //获取字段名称
            columnClass.setColumnName(resultSet.getString("COLUMN_NAME"));
            //获取字段类型
            columnClass.setColumnType(resultSet.getString("TYPE_NAME"));
            //转换字段名称，如 sys_name 变成 SysName
            columnClass.setChangeColumnName(replaceUnderLineAndUpperCase(resultSet.getString("COLUMN_NAME"), tableClass));
            //字段在数据库的注释
            columnClass.setColumnComment(resultSet.getString("REMARKS"));
            columnClassList.add(columnClass);
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("model_column", columnClassList);
        return dataMap;
    }

    private List<TableClass> getTables() throws Exception {
        List<TableClass> tableClassList = new ArrayList<>();
        Connection connection = getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(connection.getCatalog(), "root", null, new String[]{"TABLE"});
        TableClass tableClass = null;
        while (resultSet.next()) {
            //id字段略过
//            if (resultSet.getString("COLUMN_NAME").equals("id")) continue;
            tableClass = new TableClass();
            //获取字段名称
            tableClass.setTableName(resultSet.getString("TABLE_NAME"));
            //字段在数据库的注释
            tableClass.setTableAnnotation(resultSet.getString("REMARKS"));
            tableClassList.add(tableClass);
        }
        return tableClassList;
    }
}