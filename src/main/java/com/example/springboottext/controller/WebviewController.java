package com.example.springboottext.controller;

import com.example.springboottext.pojo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springboottext.service.IWebviewService;
import com.example.springboottext.pojo.Webview;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
* <p>
    * 网站浏览量 前端控制器
    * </p>
*
* @author jianglei
* @since 2024-11-07
*/
@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/webview")
public class WebviewController {
@Qualifier("webviewServiceImpl")
@Autowired
private IWebviewService webviewService;
// 增删改查
//获取全部
@GetMapping
public Result getList() {
    List<Webview> webview = webviewService.getList();

    return Result.success(webview);
}
@GetMapping("/amount")
public Result getAmount() {
    //总共有多少条数据
    Integer number = webviewService.getAmount();

    return Result.success(number);
}
//获取博客网站所需要的值 顺序如下：
//访问次数，总用户数，总评论数量，文章数量
@GetMapping("/Echarts")
public Result getEcharts() {
    //总共有多少条数据
    List<Integer> list = webviewService.getEcharts();

    return Result.success(list);
}
//通过id 获取指定数据
@Transactional
@GetMapping("/{id}")
public Result getByid(@PathVariable Integer id)  {

    Webview webview = webviewService.getByid(id);

    return Result.success(webview);
}
//分页查询
@GetMapping("/getPaginatedList/{index}/{pages}")
public Result getPaginatedList(@PathVariable Integer index , @PathVariable Integer pages) {

    List<Webview> list = webviewService.getPaginatedList(index,pages);

    return Result.success(list);
}

 @DeleteMapping("/{id}")
 public Result deleteByid(@PathVariable Integer id){
    log.info("删除指定ID");
    log.info(String.valueOf(id));

    webviewService.deleteByid(id);

    return Result.success();
    }

@PostMapping
public Result insert(@RequestBody Webview webview) {
    log.info("WebView:{}", webview.toString());
    webviewService.insert(webview);
    return Result.success();
}
    //更新数据
@PutMapping
public Result updateByid(@RequestBody Webview webview){
    log.info("请求更改 "+webview);
    webviewService.updateByid(webview);
    return Result.success();
    }
}

