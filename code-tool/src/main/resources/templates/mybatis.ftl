<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${package_name}.dao.${table_name}Mapper">
    <#if model_column?exists>
    <resultMap id="BaseResultMap" type="${package_name}.entity.${table_name}">
    <#--<resultMap id="BaseResultMap" type="com.lanqi.common.entity.${table_name}">-->
        <#list model_column as model>
            <#if (model.columnType = 'INT')>
        <result column="${model.columnName}" property="${model.changeColumnName?uncap_first}" jdbcType="INTEGER"/>
            <#else>
        <result column="${model.columnName}" property="${model.changeColumnName?uncap_first}" jdbcType="${model.columnType}"/>
            </#if>
    </#list>
    </resultMap>
    </#if>

    <sql id="Base_Column_List">
    <#if model_column?exists>
        <#list model_column as model>
            <#if (model_has_next)>
        ${model.columnName},
            <#else>
        ${model.columnName}
            </#if>
        </#list>
    </#if>
    </sql>

    <!--根据id查询${table_annotation}-->
    <select id="findById" resultMap="BaseResultMap" parameterType="java.lang.String">
        select
        <include refid="Base_Column_List"/>
        from ${table_name_small}
        where id = ${r"#{recordId}"}
    </select>


    <!--根据id集合查询${table_annotation}-->
    <select id="findByParams" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from ${table_name_small}
        <if test="${table_name?uncap_first} !=null">
            <where>
                <#if model_column?exists>
                    <#list model_column as model>
                        <#if (model.columnType = 'VARCHAR' || model.columnType = 'text')>
                <if test="${table_name?uncap_first}.${model.changeColumnName?uncap_first} != null and ${table_name?uncap_first}.${model.changeColumnName?uncap_first} !='' ">AND ${model.columnName} like CONCAT('%',${r"#{"}${table_name?uncap_first}.${model.changeColumnName?uncap_first}},'%')</if>
                        <#else>
                <if test="${table_name?uncap_first}.${model.changeColumnName?uncap_first} != null">AND ${model.columnName} = ${r"#{"}${table_name?uncap_first}.${model.changeColumnName?uncap_first}}</if>
                        </#if>
                    </#list>
                </#if>
            </where>
        </if>
        <if test="pageRequest != null">
            <choose>
                <when test="pageRequest.sortList != null">
                    <foreach collection="pageRequest.sortList" open="order by" item="sort" separator=",">
                    ${r"${sort.jdbcField} ${sort.sortType}"}
                    </foreach>
                </when>
                <otherwise>
                    order by id desc
                </otherwise>
            </choose>
            <if test="pageRequest.offset != null and pageRequest.pageSize != null">
                limit ${r"#{pageRequest.offset}, #{pageRequest.pageSize}"}
            </if>
        </if>
    </select>


    <!--根据参数分页查询${table_annotation}-->
    <select id="findByIdArr" resultMap="BaseResultMap">
        SELECT
        <include refid="Base_Column_List"/>
        FROM ${table_name_small}
        WHERE id IN
        <foreach collection="idArr" open="(" close=")" item="recordId" separator=",">
            ${r"#{recordId}"}
        </foreach>
    </select>


    <!--根据条件计数-->
    <select id="count" resultType="int">
        SELECT
        COUNT(id)
        FROM ${table_name_small}
            <where>
                <#if model_column?exists>
                    <#list model_column as model>
                        <#if (model.columnType = 'VARCHAR' || model.columnType = 'text')>
                <if test="${model.changeColumnName?uncap_first} != null and ${model.changeColumnName?uncap_first} !='' ">
                    AND ${model.columnName} like CONCAT('%',${r"#{"}${model.changeColumnName?uncap_first}},'%')
                </if>
                        <#else>
                <if test="${model.changeColumnName?uncap_first} != null">AND ${model.columnName} = ${r"#{"}${model.changeColumnName?uncap_first}}</if>
                        </#if>
                    </#list>
                </#if>
            </where>
    </select>


    <!--添加${table_annotation}-->
    <insert id="save${table_name}" parameterType="${package_name}.entity.${table_name}" useGeneratedKeys="true"
    <#--<insert id="save${table_name}" parameterType="com.lanqi.common.entity.${table_name}" useGeneratedKeys="true"-->
            keyProperty="id">
        insert into ${table_name_small}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#if model_column?exists>
            <#list model_column as model>
                <#if (model.columnName = 'id')>
                <#elseif (model.columnType = 'VARCHAR' || model.columnType = 'text')>
            <if test="${model.changeColumnName?uncap_first} != null and ${model.changeColumnName?uncap_first} !=''">
                ${model.columnName},
            </if>
                <#else>
            <if test="${model.changeColumnName?uncap_first} != null">
                ${model.columnName},
            </if>
                </#if>
            </#list>
        </#if>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
        <#if model_column?exists>
            <#list model_column as model>
                <#if (model.columnName = 'id')>
                <#elseif (model.columnType = 'VARCHAR' || model.columnType = 'text')>
            <if test="${model.changeColumnName?uncap_first} != null and ${model.changeColumnName?uncap_first} !=''">
                ${r"#{"}${model.changeColumnName?uncap_first},jdbcType=${model.columnType}},
            </if>
                <#elseif (model.columnType = 'INT')>
            <if test="${model.changeColumnName?uncap_first} != null">
                ${r"#{"}${model.changeColumnName?uncap_first},jdbcType=INTEGER},
            </if>
                <#else>
            <if test="${model.changeColumnName?uncap_first} != null">
                ${r"#{"}${model.changeColumnName?uncap_first},jdbcType=${model.columnType}},
            </if>
                </#if>
            </#list>
        </#if>
        </trim>
    </insert>


    <!--修改${table_annotation}-->
    <update id="update${table_name}" parameterType="${package_name}.entity.${table_name}">
    <#--<update id="update${table_name}" parameterType="com.lanqi.common.entity.${table_name}">-->
        update ${table_name_small}
        <#if model_column?exists>
        <set>
            <#list model_column as model>
                <#if (model.columnName = 'id')>
                <#elseif (model.columnType = 'VARCHAR' || model.columnType = 'text')>
            <if test="${model.changeColumnName?uncap_first} != null and ${model.changeColumnName?uncap_first} !=''">
                ${model.columnName} = ${r"#{"}${model.changeColumnName?uncap_first},jdbcType=${model.columnType}},
            </if>
                <#elseif (model.columnType = 'INT')>
            <if test="${model.changeColumnName?uncap_first} != null">
                ${model.columnName} = ${r"#{"}${model.changeColumnName?uncap_first},jdbcType=INTEGER},
            </if>
                <#else>
            <if test="${model.changeColumnName?uncap_first} != null">
                ${model.columnName} = ${r"#{"}${model.changeColumnName?uncap_first},jdbcType=${model.columnType}},
            </if>
                </#if>
            </#list>
        </set>
        </#if>
        WHERE id = ${r"#{id}"}
    </update>


    <!--根据id删除${table_annotation}-->
    <delete id="deleteById" parameterType="java.lang.String">
        DELETE FROM ${table_name_small}
        WHERE id = ${r"#{recordId}"}
    </delete>


    <!--根据id集合删除${table_annotation}-->
    <delete id="deleteByIdArr">
        DELETE FROM ${table_name_small}
        WHERE id IN
        <foreach collection="idArr" open="(" close=")" item="recordId" separator=",">
            ${r"#{recordId}"}
        </foreach>
    </delete>

</mapper>