package com.example.springboottext.controller;

import com.example.springboottext.pojo.ArticleCategories;
import com.example.springboottext.pojo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springboottext.service.IArtistService;
import com.example.springboottext.pojo.Artist;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
* <p>
    *  前端控制器
    * </p>
*
* @author Baomidou
* @since 2024-07-06
*/
@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/artist")
public class ArtistController {
@Qualifier("artistServiceImpl")
@Autowired
private IArtistService artistService;
    // 增删改查
    //获取全部
@GetMapping
public Result getList() {
    List<Artist> artist = artistService.getList();

    return Result.success(artist);
}
    //通过id 获取指定数据
@Transactional
@GetMapping("/{id}")
public Result getByid(@PathVariable Integer id)  {

    Artist artist = artistService.getByid(id);

    return Result.success(artist);
 }
 @GetMapping("/user/{id}")
 public Result getuserByid(@PathVariable Integer id)  {

    Artist artist = artistService.getuserByid(id);
    return Result.success(artist);
}
 @Transactional
 @GetMapping("/findArtist/{id}")
 public Result findByid(@PathVariable Integer id)  {
    Artist artist = artistService.findByid(id);
    return Result.success(artist);
}

//分页查询
@GetMapping("/getPaginatedList/{index}/{pages}")
public Result getPaginatedList(@PathVariable Integer index , @PathVariable Integer pages) {
     List<Artist> list = artistService.getPaginatedList(index,pages);
     return Result.success(list);
}
 @DeleteMapping("/{id}")
 public Result deleteByid(@PathVariable Integer id){
    log.info("删除指定ID");
    log.info(String.valueOf(id));

    artistService.deleteByid(id);

    return Result.success();
}
@DeleteMapping("/user/{id}")
public Result deleteUserByid(@PathVariable Integer id){
    log.info("删除指定ID");

    log.info(String.valueOf(id));

    artistService.deleteUserByid(id);

    return Result.success();
}

@PostMapping
public Result insert(@RequestBody Artist artist) {
    try {
        artistService.insert(artist);
        return Result.success("插入成功！");
    }catch (Exception e){
        return Result.error("插入失败");
    }
}
    //更新数据
@PutMapping
public Result updateByid(@RequestBody Artist artist){
    log.info("请求更改 "+artist);
    artistService.updateByid(artist);
    return Result.success();
    }
}

