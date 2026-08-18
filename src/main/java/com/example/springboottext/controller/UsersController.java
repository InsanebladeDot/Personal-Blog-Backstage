package com.example.springboottext.controller;

import com.example.springboottext.exception.model.AccountNotFoundException;
import com.example.springboottext.pojo.*;
import com.example.springboottext.service.IArticleService;
import com.example.springboottext.service.IArtistService;
import com.example.springboottext.service.IProfilesService;
import com.example.springboottext.untill.CosConfig.COSUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springboottext.service.IUsersService;
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
* @since 2024-07-05
*/
@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/users")
public class UsersController {
@Qualifier("usersServiceImpl")
@Autowired
private IUsersService usersService;
@Qualifier("profilesServiceImpl")
@Autowired
private IProfilesService profilesService;
@Qualifier("artistServiceImpl")
@Autowired
private IArtistService artistService;
@Qualifier("articleServiceImpl")
@Autowired
private IArticleService articleService;

    // 增删改查
    //获取全部
@GetMapping
public Result getList() {
    List<Users> users = usersService.getList();

    return Result.success(users);
}
@GetMapping("/amount")
public Result getAmount() {
    //总共有多少条数据
    Integer number = usersService.getAmount();
   return Result.success(number);
}
//通过id 获取指定数据
@Transactional
@GetMapping("/{id}")
public Result getByid(@PathVariable Integer id)  {

    Users users = usersService.getByid(id);

    return Result.success(users);
 }
 @GetMapping("/postname/{name}")
 public Result getusername(@PathVariable String name)  {
    Users users = usersService.getusername(name);
    return Result.success(users);
}

@GetMapping("/open")
public Result getOpenTypeByid(@RequestBody Users user){

    try {
        Users users = usersService.getOpenTypeByid(user);
        return Result.success(users);
    }catch (AccountNotFoundException e){
        throw new AccountNotFoundException("在通过第三方标识 和登陆方查找用户时发生错误");
    }
}
//分页查询
@GetMapping("/getPaginatedList/{index}/{pages}")
public Result getPaginatedList(@PathVariable Integer index , @PathVariable Integer pages) {
    List<Users> list = usersService.getPaginatedList(index,pages);
    return Result.success(list);
}
 @DeleteMapping("/{id}")
 public Result deleteByid(@PathVariable Integer id){
    log.info("删除指定ID");
    log.info(String.valueOf(id));
    try{
        Users users = usersService.getByid(id);

        usersService.deleteByid(id);
        //在删除该用户的时候也要删除该用户的一切信息 保留评论能力
        //1.删除用户信息
        profilesService.deleteUserByid(id);
        //2.删除用户作者信息
        artistService.deleteUserByid(id);
        //3.删除用户作者对应的作品
        articleService.deleteUserByid(id);
        //调用数据库 删除对应的图片
        COSUtil.deleteFile(users.getProfilephoto());
    }catch (Exception e){
        log.error("处理用户请求时发生错误", e);
        throw new AccountNotFoundException("处理用户删除请求时发生错误");
    }

    return Result.success();
    }

@PostMapping
public Result insert(@RequestBody Users users) {
    try {
        log.info("新增用户{}", users);//Kaisa 密文
        String password = users.getPassword();//Kaisa 密文

        usersService.insert(users);
        // 同时添加Profiles 数据
        Profiles profile = new Profiles();
        // 注册
        users.setPassword(password);//Kaisa 密文

        Users foundUser = usersService.Loginfind(users);
        profile.setUserId(foundUser.getId());
        log.info("新增用户{}", foundUser);
        profilesService.insert(profile);

        // 创建关联的 Artist
        Artist artist = new Artist();
        artist.setUserId(foundUser.getId());
        artistService.insert(artist);

        return Result.success();
    } catch (Exception e) {
        log.error("处理用户请求时发生错误", e);
        throw new AccountNotFoundException("处理用户插入请求时发生错误");
    }
}
    //更新数据
@PutMapping
public Result updateByid(@RequestBody Users users){
    log.info("请求更改 "+users);
    try {
        usersService.updateByid(users);
        return Result.success();
    }catch (Exception e){
        throw new AccountNotFoundException("处理用户更新请求时发生错误");
    }
   }
}

