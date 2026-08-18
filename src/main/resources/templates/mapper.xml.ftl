<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

<#if enableCache>
    <!-- 开启二级缓存 -->
    <cache type="${cacheClassName}"/>

</#if>
<#if baseResultMap>
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
<#list table.fields as field>
<#if field.keyFlag><#--生成主键排在第一位-->
        <id column="${field.name}" property="${field.propertyName}" />
</#if>
</#list>
<#list table.commonFields as field><#--生成公共字段 -->
        <result column="${field.name}" property="${field.propertyName}" />
</#list>
<#list table.fields as field>
<#if !field.keyFlag><#--生成普通字段 -->
        <result column="${field.name}" property="${field.propertyName}" />
</#if>
</#list>
    </resultMap>

</#if>
<#if baseColumnList>
    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
<#list table.commonFields as field>
        ${field.columnName},
</#list>
        ${table.fieldNames}
    </sql>

</#if>
    <#assign Aini = true/>

<#--  简单插入  -->
    <insert id="insert">
        INSERT INTO ${table.name}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list table.fields as field>
                <if test="${field.propertyName} != null">
                    ${field.columnName},
                </if>
            </#list>
        </trim>
        VALUES
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list table.fields as field>
                <if test="${field.propertyName} != null">
                    <#if Aini = true>#</#if>{${field.propertyName}},
                </if>
            </#list>
        </trim>
    </insert>
<#--    非主键   -->
    <#assign tableSize = 0/>
    <#list table.fields as field>
        <#if !field.keyFlag>
            <#assign tableSize = tableSize + 1/>
        </#if>
    </#list>
<#--每过一轮字段数减少 1-->
    <#assign cnt = 1/>
    <update id="updateByid">
        UPDATE ${table.name}
        <set>
            <#list table.fields as field>
                <#if !field.keyFlag>
                    <if test="${field.propertyName} != null">
                        ${field.columnName} = <#if Aini = true>#</#if>{${field.propertyName}}
                        <#assign cnt = cnt+1/>
                        <#assign columnName = field.columnName/>
                        <#assign CheckMe = true/>
                        <#assign tableLenght = tableSize-cnt />
                        <#if tableLenght !=-1 >
                           <if test="<#list table.fields as field><#if !CheckMe>${field.propertyName } != null<#if tableLenght !=0> or <#assign tableLenght = tableLenght-1 /></#if></#if><#if field.columnName == columnName ><#assign CheckMe = false/></#if></#list>">,</if>
                        </#if>
                    </if>
                </#if>
            </#list>
        </set>
        <where>
            <#list table.fields as field>
                <#if field.keyFlag>
                    ${field.columnName} = <#if Aini = true>#</#if>{${field.propertyName}}
                </#if>
            </#list>
        </where>
    </update>
</mapper>
