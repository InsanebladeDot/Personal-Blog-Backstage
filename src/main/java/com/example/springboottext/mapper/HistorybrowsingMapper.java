package com.example.springboottext.mapper;

import com.example.springboottext.pojo.Historybrowsing;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.*;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-01
 */
public interface HistorybrowsingMapper {
   @Select("Select * from historybrowsing")
   List<Historybrowsing> getList();

   @Select("Select * from historybrowsing where history_id = #{id}")
   Historybrowsing getByid(Integer id);

   @Delete("Delete from historybrowsing where history_id = #{id}")
   void deleteByid(Integer id);

   void insert(Historybrowsing historybrowsing);

   void updateByid(Historybrowsing historybrowsing);

 }

