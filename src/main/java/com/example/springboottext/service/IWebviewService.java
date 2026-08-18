package com.example.springboottext.service;

import com.example.springboottext.pojo.Webview;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 * 网站浏览量 服务类
 * </p>
 *
 * @author jianglei
 * @since 2024-11-07
 */
@Service
public interface IWebviewService  {

    public List<Webview> getList();

    public Webview getByid(Integer id);

    public Integer getAmount();

    public List<Webview> getPaginatedList(Integer index,Integer pages);

    public void deleteByid(Integer id);

    public void insert(Webview webview);

    public void updateByid(Webview webview);

    List<Integer> getEcharts();
}
