package com.alibaba.vipvideo.controller;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.http.server.HttpServerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/*
* 控制器
* */
@Controller    //前后端不分离，用这个控制器
public class TVcontroller {

    @RequestMapping("/hellow")
    public String test (Model model){
        //第二步再填充：
        model.addAttribute("user","欧阳修");    //   model携带网页需要的数据了，此后模板引擎就会带着模型数据
                                            //找到视图网页，自动将user替换为欧阳修
        return "java";
    }

    @RequestMapping("/to")
    public String toPage (){
        return "play";
    }

    //编写一个处理播放请求的方法
    @RequestMapping("/play")
    public String playPage (String url, boolean isQR, HttpServletResponse response) throws IOException {  //播放逻辑：首先要接收到前端的数据，1、输入框的数据，2、勾选框的数据。
        //判断用户的行为需求
        if (isQR){ //扫码看
            //第一步，准备一个播放路径
            String playUrl="https://jx.bozrc.com:4433/player/?url="+url;
            //第二步,创建二维码对象
            BufferedImage image = QrCodeUtil.generate(playUrl, 400, 400);
            //第三步，把图片发到网页上，（通过IO流来传输）
            ImageIO.write(image,"png",response.getOutputStream());

        }else {  //在线看
           return "redirect:https://jx.bozrc.com:4433/player/?url="+url;         //需要把整个请求重定向到 我们的解析网址上，同时携带我们要看的网址。
        }
        return "play";
    }
}
