package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import java.util.List;
import org.apache.ibatis.annotations.*;

<#if mapperAnnotationClass??>
import ${mapperAnnotationClass.name};
</#if>

/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if mapperAnnotationClass??>
@${mapperAnnotationClass.simpleName}
</#if>
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} {
   @Select("Select * from ${table.name}")
   List<${entity}> getList();

   @Select("SELECT COUNT(*) FROM ${table.name}")
   Integer getAmount();

   @Select("Select * from ${table.name} where <#list table.fields as field><#if field.keyFlag>${field.annotationColumnName}</#if></#list> = <#if true>#</#if>{id}")
   ${entity} getByid(Integer id);
    //分页查询 如果 creatime 不对应表中的 创建时间则自己去修改即可
   @Select("SELECT * FROM  ${table.name}  ORDER BY creatime DESC LIMIT <#if true>#</#if>{pages} OFFSET <#if true>#</#if>{offset}")
   List<${entity}> getPaginatedList(Integer pages,Integer offset);

   @Delete("Delete from ${table.name} where <#list table.fields as field><#if field.keyFlag>${field.annotationColumnName}</#if></#list> = <#if true>#</#if>{id}")
   void deleteByid(Integer id);

   void insert(${entity} ${table.entityPath});

   void updateByid(${entity} ${table.entityPath});

 }

</#if>
