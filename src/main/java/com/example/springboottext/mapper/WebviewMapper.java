package com.example.springboottext.mapper;

import com.example.springboottext.pojo.Webview;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.*;


/**
 * <p>
 * 网站浏览量 Mapper 接口
 * </p>
 *
 * @author jianglei
 * @since 2024-11-07
 */
public interface WebviewMapper {
   @Select("Select * from webview")
   List<Webview> getList();

   @Select("SELECT COUNT(*) FROM webview")
   Integer getAmount();

   @Select("Select * from webview where id = #{id}")
   Webview getByid(Integer id);
    //分页查询 如果 creatime 不对应表中的 创建时间则自己去修改即可
   @Select("SELECT * FROM  webview  ORDER BY creatime DESC LIMIT #{pages} OFFSET #{offset}")
   List<Webview> getPaginatedList(Integer pages,Integer offset);

   @Delete("Delete from webview where id = #{id}")
   void deleteByid(Integer id);

   void insert(Webview webview);

   void updateByid(Webview webview);

}

