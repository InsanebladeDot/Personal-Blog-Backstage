package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName}  {

    public List<${entity}> getList();

    public ${entity} getByid(Integer id);

    public Integer getAmount();
    public List<${entity}> getPaginatedList(Integer index,Integer pages);

    public void deleteByid(Integer id);

    public void insert(${entity} ${table.entityPath});

    public void updateByid(${entity} ${table.entityPath});
 }
</#if>
