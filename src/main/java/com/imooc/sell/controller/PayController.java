package com.imooc.sell.controller;

import com.imooc.sell.config.WechatAccountConfig;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @GetMapping("/create/redirect")
    public String create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                         HttpServletRequest request) throws UnsupportedEncodingException {
//        request.setAttribute("redirect", false);
        String url = "http://proxy.springboot.cn/pay?openid=oTgZpwSmF3qlWRCUOCI9rI_GcCPg&orderId=" + orderId + "&returnUrl="+ URLEncoder.encode(returnUrl,"utf-8");
        return "redirect:" + url;
    }

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String,Object> map,
                               HttpServletRequest request) throws UnsupportedEncodingException {
//        request.setAttribute("redirect", true);
        //查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //发起支付
        PayResponse payResponse = payService.create(orderDTO);
        map.put("payResponse", payResponse);
        map.put("returnUrl", URLDecoder.decode(returnUrl,"utf-8"));
//        map.put("orderId", orderId);
        return new ModelAndView("pay/create", map);
    }

    @PostMapping("notify")
    public ModelAndView notify(@RequestBody String notifyData){
        PayResponse payResponse = payService.notify(notifyData);

        //返回给微信处理结果
        return new ModelAndView("pay/success");
    }
}
