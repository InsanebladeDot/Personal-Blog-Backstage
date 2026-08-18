package ${package.Controller};

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Qualifier;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import org.springframework.beans.factory.annotation.Autowired;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${table.entityName};
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
* <p>
    * ${table.comment!} 前端控制器
    * </p>
*
* @author ${author}
* @since ${date}
*/
@Slf4j //加上这个注解会自动生成 一个日志记录
<#if restControllerStyle>
@RestController
<#else>
@RestController
</#if>
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
    class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
public class ${table.controllerName} {
    </#if>
@Qualifier("${table.entityPath}ServiceImpl")
@Autowired
private ${table.serviceName} ${table.entityPath}Service;
// 增删改查
//获取全部
@GetMapping
public Result getList() {
    List<${entity}> ${table.entityPath} = ${table.entityPath}Service.getList();

    return Result.success(${table.entityPath});
}
@GetMapping("/amount")
public Result getAmount() {
    //总共有多少条数据
    Integer number = ${table.entityPath}Service.getAmount();

    return Result.success(number);
}
//通过id 获取指定数据
@Transactional
@GetMapping("/{id}")
public Result getByid(@PathVariable Integer id)  {

    ${entity} ${table.entityPath} = ${table.entityPath}Service.getByid(id);

    return Result.success(${table.entityPath});
}
//分页查询
@GetMapping("/getPaginatedList/{index}/{pages}")
public Result getPaginatedList(@PathVariable Integer index , @PathVariable Integer pages) {
    //Index 代表着：你点击 1-n 的按钮传递的值 表示1页 还是n页
    //pages 代表着：一页你要多少个内容？
    List<${entity}> list = ${table.entityPath}Service.getPaginatedList(index,pages);

    return Result.success(list);
}

 @DeleteMapping("/{id}")
 public Result deleteByid(@PathVariable Integer id){
    log.info("删除指定ID");
    log.info(String.valueOf(id));

    ${table.entityPath}Service.deleteByid(id);

    return Result.success();
    }

@PostMapping
public Result insert(@RequestBody ${entity} ${table.entityPath}) {
    ${table.entityPath}Service.insert(${table.entityPath});
    return Result.success();
    }
    //更新数据
@PutMapping
public Result updateByid(@RequestBody ${entity} ${table.entityPath}){
    log.info("请求更改 "+${table.entityPath});
    ${table.entityPath}Service.updateByid(${table.entityPath});
    return Result.success();
    }
}
</#if>

