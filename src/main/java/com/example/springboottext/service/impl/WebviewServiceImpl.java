package com.example.springboottext.service.impl;

import com.example.springboottext.mapper.*;
import com.example.springboottext.pojo.Webview;
import com.example.springboottext.service.IWebviewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 网站浏览量 服务实现类
 * </p>
 *
 * @author jianglei
 * @since 2024-11-07
 */
@Service
public class WebviewServiceImpl implements IWebviewService{
     @Autowired
     private WebviewMapper webviewmapper;
     @Autowired
     private ArticleMapper articlemapper;
     @Autowired
     private CommentsMapper commentsmapper;
     @Autowired
     private UsersMapper usersmapper;
    @Autowired
    private MessageBoardMapper messageBoardmapper;


     @Override
     public List<Webview> getList() {
          return webviewmapper.getList();
     }

     @Override
     public Webview getByid(Integer id) {
          return webviewmapper.getByid(id);
      }
     @Override
     public Integer getAmount() {
         return webviewmapper.getAmount();
     }

     @Override
     public void deleteByid(Integer id) {
           webviewmapper.deleteByid(id);
     }

     @Override
     public void insert(Webview webview) {
          webviewmapper.insert(webview);
     }

     @Override
     public void updateByid(Webview webview) {
         webviewmapper.updateByid(webview);
     }

    @Override
    public List<Integer> getEcharts() {
       List<Integer> list = new ArrayList<>();
       list.add(articlemapper.getAmount());
       list.add(commentsmapper.getAmount() + messageBoardmapper.getAmount());
       list.add(usersmapper.getAmount());
       list.add(webviewmapper.getAmount());

        return list;
    }

    @Override
    public List<Webview> getPaginatedList(Integer index, Integer pages) {
        Integer offset = (index - 1) * pages;
        //pages 总共要多少页？
        //index 按钮提供的
        return webviewmapper.getPaginatedList(pages,offset);
    }
}
