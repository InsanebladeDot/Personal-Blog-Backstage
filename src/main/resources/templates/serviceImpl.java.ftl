package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
<#if generateService>
import ${package.Service}.${table.serviceName};
</#if>
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>()<#if generateService>, ${table.serviceName}</#if> {

}
<#else>
public class ${table.serviceImplName} implements ${table.serviceName }{
     @Autowired
     private ${table.mapperName} ${table.entityPath}mapper;

     @Override
     public List<${entity}> getList() {
          return ${table.entityPath}mapper.getList();
     }

     @Override
     public ${entity} getByid(Integer id) {
          return ${table.entityPath}mapper.getByid(id);
      }
     @Override
     public Integer getAmount() {
         return ${table.entityPath}mapper.getAmount();
     }

     @Override
     public void deleteByid(Integer id) {
           ${table.entityPath}mapper.deleteByid(id);
     }

     @Override
     public void insert(${entity} ${table.entityPath}) {
          ${table.entityPath}mapper.insert(${table.entityPath});
     }

     @Override
     public void updateByid(${entity} ${table.entityPath}) {
         ${table.entityPath}mapper.updateByid(${table.entityPath});
     }

    @Override
    public List<${entity}> getPaginatedList(Integer index, Integer pages) {
        Integer offset = (index - 1) * pages;
        //pages 总共要多少页？
        //index 按钮提供的
        return ${table.entityPath}mapper.getPaginatedList(pages,offset);
    }
}
</#if>
