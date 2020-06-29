package com.spring.web.utils; /**
 * @author 陈来件(鹰涯)
 * @date 2020/04/24
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class CodeGeneratorUtil {
    private static final String TABLE_NAME = "audit_task_history";
    private static final String SCHEMA = "cht_timer_task";
    private static final String MODEL_PATH = "/";
    private static final String MAPPER_PATH = "/";

    private static final JdbcTemplate jdbcTemplate;
    static {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        dataSource.setUrl("jdbc:mysql://localhost:3306/origin_goods_paas");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public static void main(String[] args) throws IOException {
        //获取数据名称和类型 name - type
        List<Map<String, Object>> result = jdbcTemplate.queryForList("select column_name name, data_type type,is_nullable is_null, column_comment from information_schema.columns where table_schema='" + SCHEMA + "' and table_name = '" + TABLE_NAME + "'");
        String userDir = System.getProperty("user.dir");
        System.out.print(userDir);
        File file = new File(userDir+MODEL_PATH+StringUtil.camelCaseNameForClassName(TABLE_NAME)+".java");
        File mapper = new File(userDir+MAPPER_PATH + StringUtil.camelCaseNameForClassName(TABLE_NAME)+ "Mapper.xml");

        if(file.exists()){
            file.delete();
        }
        if(mapper.exists()){
            mapper.delete();
        }
        mapper.createNewFile();
        file.createNewFile();
        OutputStream outputStream = new FileOutputStream(file);
        outputStream.write(buildJavaBean(result).getBytes());
        outputStream.close();
        OutputStream mapperOut = new FileOutputStream(mapper);
        mapperOut.write(buildMapper(result).getBytes());
        mapperOut.flush();
        mapperOut.close();
    }


    private static String buildJavaBean(List<Map<String, Object>> result) {
        StringBuilder header = new StringBuilder();
        StringBuilder body = new StringBuilder();
        String ClassName = StringUtil.camelCaseNameForClassName(TABLE_NAME);
        String packagePath = "";

        //头部信息添加
        header.append("package ").append(packagePath).append(";\n\n");
        header.append("import lombok.Data;\n");
        header.append("import java.io.Serializable;\n");

        //body部分
        body.append("\n\n@Data\n");
        body.append("public class ").append(ClassName).append(" implements Serializable {\n");
        body.append("    private static final long serialVersionUID = 4287265884495507638L;\n\n");
        boolean hasDate = false;
        for (Map<String,Object> row:result){
            String name = row.get("name").toString();
            String type = row.get("type").toString();
            String isNull = row.get("is_null").toString();
            String comment = row.get("column_comment").toString();
            String JType = getMappingType(type);
            if(JType.equals("Date") && !hasDate){
                hasDate = true;
                header.append("import java.util.Date;\n");
            }

            //加注释；
            body.append("    /**\n     * ").append(comment).append("  ").append("NO".equalsIgnoreCase(isNull)?"not null":"");
            body.append("\n").append("     */\n");

            //方法属性
            body.append("    private ").append(JType).append(" ").append(StringUtil.camelCaseName(name));
            body.append(";\n\n");
        }
        body.append("}");


        header.append(body.toString());
        return header.toString();
    }

    private static String getMappingType(String type) {
        if("bigint".equalsIgnoreCase(type)){
            return "Long";
        } else if("varchar".equalsIgnoreCase(type)){
            return "String";
        } else if(type.contains("int")){
            return "Integer";
        } else if("float".equalsIgnoreCase(type)){
            return "Float";
        } else if ("double".equalsIgnoreCase(type)){
            return "Double";
        } else if( type.contains("DATE") || type.contains("date")){
            return "Date";
        }
        return "Object";
    }

    public static String buildMapper(List<Map<String,Object>> result){
        StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
            "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");

        //创建基础mapper
        String upperName = StringUtil.camelCaseNameForClassName(TABLE_NAME);
        sb.append("<mapper namespace=\"").append(upperName).append("\">\n")
            .append("    <resultMap id=\"").append(upperName).append("Map\" type=\"").append(upperName).append("\">\n");
        for(Map<String,Object> row:result){
            String name = row.get("name").toString();
            String type = row.get("type").toString();
            if("id".equals(name)){
                sb.append("        <id property=\"id\" column=\"id\"/>\n");
            } else {
                sb.append("        <result property=\"").append(StringUtil.camelCaseName(name)).append("\" column=\"").append(name).append("\"/>\n");
            }
        }
        sb.append("    </resultMap>\n\n");

        //创建id
        sb.append("    <sql id=\"table_name\">\n" +
            "        "+TABLE_NAME+"\n" +
            "    </sql>\n");

        sb.append("    <sql id=\"columns_all\">\n" +
            "        id,\n" +
            "        <include refid=\"columns_exclude_id\"/>\n" +
            "    </sql>\n\n");

        sb.append("    <sql id=\"columns_exclude_id\">\n");

        boolean isFirst = true;
        for(Map<String,Object> row:result){
            String name = row.get("name").toString();
            if("id".equals(name)) continue;
            if(!isFirst){
                sb.append(",");
            }else {
                sb.append("        ");
                isFirst = false;
            }
            sb.append("`").append(name).append("`");
        }
        sb.append("\n    </sql>\n\n");

        //插入values_exclude_id
        sb.append("    <sql id=\"values_exclude_id\">\n");
        isFirst = true;
        for(Map<String,Object> row:result){
            String name = row.get("name").toString();
            if("id".equals(name)) continue;
            if(!isFirst){
                sb.append(",");
            }else {
                sb.append("        ");
                isFirst = false;
            }
            sb.append("#{").append(StringUtil.camelCaseName(name)).append("}");
        }
        sb.append("\n    </sql>\n\n");

        //插入criteria
        sb.append("    <sql id=\"criteria\">\n" +
            "        <where>\n");
        for(Map<String,Object> row:result){
            String name = row.get("name").toString();
            if("id".equals(name)) continue;
            sb.append("            <if test=\"").append(StringUtil.camelCaseName(name)).append(" != null\">AND `").append(name).append("` = #{").append(StringUtil.camelCaseName(name)).append("}</if>\n");
        }

        sb.append("            AND is_deleted = 0\n" +
            "        </where>\n" +
            "    </sql>\n\n");

        //插入create
        sb.append("    <insert id=\"create\" parameterType=\""+upperName+"\" useGeneratedKeys=\"true\" keyProperty=\"id\">\n" +
            "        INSERT INTO\n" +
            "        <include refid=\"table_name\"/>\n" +
            "        (<include refid=\"columns_exclude_id\"/>)\n" +
            "        VALUES\n" +
            "        (<include refid=\"values_exclude_id\"/>)\n" +
            "    </insert>\n\n");

        //插入creates
        sb.append("    <insert id=\"creates\" parameterType=\""+upperName+"\" useGeneratedKeys=\"true\">\n" +
            "        INSERT INTO\n" +
            "        <include refid=\"table_name\"/>\n" +
            "        (<include refid=\"columns_exclude_id\"/>)\n" +
            "        VALUES\n" +
            "        <foreach collection=\"list\" item=\"i\" index=\"index\" separator=\",\">\n            (");
        isFirst = true;
        for(Map<String,Object> row:result){
            String name = row.get("name").toString();
            if("id".equals(name)) continue;
            if(!isFirst){
                sb.append(",");
            }else {
                sb.append("");
                isFirst = false;
            }
            sb.append("#{").append(StringUtil.camelCaseName(name)).append("}");
        }
        sb.append(")\n        </foreach>\n" +
            "    </insert>\n\n");

        //插入findById
        sb.append("    <select id=\"findById\" parameterType=\"long\" resultMap=\""+upperName+"Map\">\n" +
            "        SELECT\n" +
            "        <include refid=\"columns_all\"/>\n" +
            "        FROM\n" +
            "        <include refid=\"table_name\"/>\n" +
            "        WHERE id = #{id}\n" +
            "        AND is_deleted = 0 LIMIT 1\n" +
            "    </select>\n\n");


        //插入findByIds
        sb.append("    <select id=\"findByIds\" parameterType=\"list\" resultMap=\""+upperName+"Map\">\n" +
            "        SELECT\n" +
            "        <include refid=\"columns_all\"/>\n" +
            "        FROM\n" +
            "        <include refid=\"table_name\"/>\n" +
            "        WHERE id IN\n" +
            "        <foreach item=\"id\" collection=\"list\" open=\"(\" separator=\",\" close=\")\">\n" +
            "            #{id}\n" +
            "        </foreach>\n" +
            "        AND is_deleted = 0\n" +
            "    </select>\n\n");

        //插入update
        sb.append("    <update id=\"update\" parameterType=\""+upperName+"\">\n" +
            "        UPDATE\n" +
            "        <include refid=\"table_name\"/>\n" +
            "        <set>\n");
        for(Map<String,Object> row:result){
            String name = row.get("name").toString();
            if("id".equals(name)) continue;
            sb.append("            <if test=\""+StringUtil.camelCaseName(name)+" != null\">`"+name+"` = #{"+StringUtil.camelCaseName(name)+"},</if>\n");
        }
        sb.append("            updated_at = now()\n" +
            "        </set>\n" +
            "        WHERE id = #{id}\n" +
            "    </update>\n\n");

        //插入delete
        sb.append("    <delete id=\"delete\" parameterType=\"long\">\n" +
            "        UPDATE\n" +
            "        <include refid=\"table_name\"/>\n" +
            "        SET is_deleted = 1\n" +
            "        WHERE id = #{id}\n" +
            "    </delete>\n");

        //插入count
        sb.append("    <select id=\"count\" parameterType=\"map\" resultType=\"long\">\n" +
            "        SELECT COUNT(1)\n" +
            "        FROM\n" +
            "        <include refid=\"table_name\"/>\n" +
            "        <include refid=\"criteria\"/>\n" +
            "    </select>\n\n");

        //插入paging
        sb.append("    <select id=\"paging\" parameterType=\"map\" resultMap=\"" + upperName + "Map\">\n" +
            "        SELECT\n" +
            "        <include refid=\"columns_all\"/>\n" +
            "        FROM\n" +
            "        <include refid=\"table_name\"/>\n" +
            "        <include refid=\"criteria\"/>\n" +
            "        ORDER BY `id` DESC\n" +
            "        LIMIT #{offset}, #{limit}\n" +
            "    </select>\n\n");

        //插入list
        sb.append("    <select id=\"list\" parameterType=\"map\" resultMap=\"" + upperName + "Map\">\n" +
            "        SELECT\n" +
            "        <include refid=\"columns_all\"/>\n" +
            "        FROM\n" +
            "        <include refid=\"table_name\"/>\n" +
            "        <include refid=\"criteria\"/>\n" +
            "        ORDER BY `id` DESC\n" +
            "    </select>\n\n");

        sb.append("</mapper>");
        return sb.toString();
    }
}

/**
 * Desc:
 * Mail: chenlaijian@terminus.io
 * Date: 2018/1/15
 */
class StringUtil {

    /**
     * 转换为下划线
     *
     * @param camelCaseName
     * @return
     */
    public static String underscoreName(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        if (camelCaseName != null && camelCaseName.length() > 0) {
            result.append(camelCaseName.substring(0, 1).toLowerCase());
            for (int i = 1; i < camelCaseName.length(); i++) {
                char ch = camelCaseName.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append("_");
                    result.append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }

    /**
     * aaa_sss -> aaaSss
     */
    public static String camelCaseName(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            for (int i = 0; i < name.length(); i++) {
                char ch = name.charAt(i);
                boolean should2Upper = false;
                if('_' == ch){
                    i ++;
                    ch = name.charAt(i);
                    should2Upper = true;
                }
                result.append(should2Upper? Character.toUpperCase(ch) : ch);
            }
        }
        return result.toString();
    }

    /**
     * asdKk -> KsdKk
     */
    public static String camelCaseNameForClassName(String name) {
        String camelClassName = camelCaseName(name);
        return Character.toUpperCase(camelClassName.charAt(0)) + camelClassName.substring(1);
    }
}