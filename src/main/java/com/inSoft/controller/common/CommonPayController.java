package com.inSoft.controller.common;

import com.inSoft.config.PayConfig;
import com.inSoft.pojo.Income;
import com.inSoft.pojo.SysOrders;
import com.inSoft.pojo.WebUsersVipser;
import com.inSoft.pojo.result.Result;
import com.inSoft.service.IncomeService;
import com.inSoft.service.SysOrdersService;
import com.inSoft.service.WebUsersVipserService;
import com.inSoft.utils.*;
import com.inSoft.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class CommonPayController extends BaseController{

    private static final String DEFAULT_PAY_TYPE = PayConfig.DEFAULT_PAY_TYPE;

    @Autowired
    private SysOrdersService sysOrdersService;

    @Autowired
    private WebUsersVipserService webUsersVipserService;

    @Autowired
    private IncomeService incomeService;

    /**
     * 创建订单
     * @param vtype 通用创建订单接口 传递vtype 进行区分api和web端的请求
     * @param uPayType 用户选择的支付方式
     * @param formData 提交的表单数据
     * @return
     */
    @PostMapping("/create_order")
    public Result createOrder(@RequestParam(name = "vtype", required = false) String vtype,
                              @RequestParam(name = "uPayType", required = false) String uPayType,
                              @RequestBody Map<String, Object> formData) {
        try {
            //检查参数
            if (StringUtils.isEmpty(uPayType)){
                return Result.error("缺少必要参数！");
            }
            String outTradeNo = StringUtils.getOutTradeNo();
            Map<String,Object> orderRes = new HashMap<>();
            orderRes.put("outTradeNo",outTradeNo);
            //订单参数
            SysOrders custOrder = new SysOrders();
            custOrder.setStatus(0);
            custOrder.setUid(getUserId());
            custOrder.setIncomeId(MapUtils.get(formData, "incomeId",Integer.class));
            custOrder.setAmount(MapUtils.get(formData, "totalAmount",Double.class));
            custOrder.setOutTradeNo(outTradeNo);
            custOrder.setCreatedAt(new Date());
            //分流支付方式
            switch (uPayType) {
                case "alipay":
                    String alipayOrder = AliPayUtils.createOrder(
                            outTradeNo,
                            MapUtils.get(formData, "subject"),
                            MapUtils.get(formData, "totalAmount")
                    );
                    orderRes.put("infoBody",alipayOrder);
                    //保存订单数据到数据库
                    custOrder.setRemarks("开通："+MapUtils.get(formData, "subject"));
                    custOrder.setTradeNo("ALIPAY_"+outTradeNo);
                    custOrder.setPayType(2);
                    if(sysOrdersService.save(custOrder)){
                        return Result.success("订单创建成功~",orderRes);
                    };
                    return Result.error("订单创建失败");
                case "wxpay":
                    String wxpayOrder = WxPayUtils.createOrder(
                            outTradeNo,
                            MapUtils.get(formData, "body"),
                            MapUtils.get(formData, "totalFee")
                    );
                    orderRes.put("infoBody",wxpayOrder);
                    //保存订单数据到数据库
                    custOrder.setRemarks("开通："+MapUtils.get(formData, "body"));
                    custOrder.setTradeNo("WXPAY_"+outTradeNo);
                    custOrder.setPayType(1);
                    if(sysOrdersService.save(custOrder)){
                        return Result.success("订单创建成功",orderRes);
                    }
                    return Result.error("订单创建失败");
                default:
                    return Result.error("暂不支持的支付方式");
            }
        } catch (Exception e) {
            return Result.error("创建订单失败：" + e.getMessage());
        }
    }

    //通用退款接口
    @PostMapping("/refund")
    public Result refund(@RequestParam(name = "vtype", required = false) String vtype,
                         @RequestParam(name = "uPayType", required = false) String uPayType,
                         @RequestBody Map<String, Object> formData) {
        try {
            //检查参数
            if (StringUtils.isEmpty(uPayType)){
                return Result.error("缺少必要参数！");
            }
            //验证登录
            if (!checkLogin()){
                return Result.error("请先登录！");
            };
            //查询此订单数据
            Integer orderId = null;
            SysOrders custOrder = new SysOrders();
            custOrder.setStatus(3);
            custOrder.setUid(getUserId());
            custOrder.setOutTradeNo(MapUtils.get(formData, "outTradeNo",String.class));
            custOrder.setUpdatedAt(new Date());
            switch (uPayType) {
                case "alipay":
                    //查询微信数据下的订单数据
                    orderId = sysOrdersService.getOrderIdByOutTradeNo(MapUtils.get(formData, "outTradeNo"),2);
                    if (orderId == null){
                        return Result.error("订单不存在");
                    }
                    custOrder.setId(orderId);
                    if(sysOrdersService.update(custOrder)){
                        AliPayUtils.handleRefund(
                            MapUtils.get(formData, "outTradeNo"),
                            MapUtils.get(formData, "refundAmount")
                        );
                        return Result.success("支付宝订单退款成功");
                    };
                    return Result.error("支付宝订单状态更新失败");
                case "wxpay":
                    orderId = sysOrdersService.getOrderIdByOutTradeNo(MapUtils.get(formData, "outTradeNo"),1);
                    if (orderId == null){
                        return Result.error("订单不存在");
                    }
                    custOrder.setId(orderId);
                    if(sysOrdersService.update(custOrder)){
                       WxPayUtils.handleRefund(
                            MapUtils.get(formData, "outTradeNo"),
                            MapUtils.get(formData, "refundFee")
                        );
                        return Result.success("微信订单退款成功");
                    };
                    return Result.error("微信订单状态更新失败");
                default:
                    return Result.error("暂不支持的支付方式");
            }
        } catch (Exception e) {
            return Result.error("退款失败：" + e.getMessage());
        }
    }

    //微信及支付宝通用的支付反馈接口
    @RequestMapping("/notify/{uPayType}")
    public Result notify_url(HttpServletRequest request,
                             @PathVariable(name = "uPayType", required = false) String uPayType) {
        try {
            //检查参数
            if (StringUtils.isEmpty(uPayType)){
                return Result.error("缺少必要参数！");
            }
            //获取request中的custCheckz字段
            String custCheckStr = request.getParameter("custCheck");
            boolean custCheck = Boolean.parseBoolean(custCheckStr);
            String outTradeNo = request.getParameter("out_trade_no");


            //整理订单参数信息
            switch (uPayType) {
                case "alipay":
                    DeBugUtils.println("支付宝支付成功反馈开始~");
                    //如果不是自定义查询状态则执行接口验证
                    if (!custCheck){
                        if(AliPayUtils.listenForPaymentResult(request)){
                            //更新订单状态 开通会员
                            DeBugUtils.println("支付宝支付验证成功",outTradeNo);
                            SysOrders nowOrderInfo = sysOrdersService.getOrderByOutTradeNo(outTradeNo,2);
                            DeBugUtils.println("订单信息：",nowOrderInfo);
                            //更新订单状态 根据uid 支付类型 及单号
                            SysOrders custOrder = new SysOrders();
                            if (nowOrderInfo != null){
                                custOrder.setId(nowOrderInfo.getId());
                                custOrder.setStatus(1);
                                custOrder.setPayAt(new Date());
                                custOrder.setUpdatedAt(new Date());
                                if (sysOrdersService.update(custOrder)){
                                    //开通会员
                                    if (openVip(nowOrderInfo.getIncomeId(),nowOrderInfo.getUid())){
                                        DeBugUtils.println("开通成功");
                                        return Result.success("开通成功~");
                                    }
                                }
                                //直接退款 并取消订单
                                String totalAmount = request.getParameter("total_amount");
                                if(AliPayUtils.handleRefund(outTradeNo,totalAmount)){
                                    //取消订单
                                    sysOrdersService.upStatusById(nowOrderInfo.getId(),3);
                                    DeBugUtils.println("开通失败，已退款");
                                    return Result.success("开通失败，已退款~");
                                };
                                DeBugUtils.println("开通失败，退款失败");
                                return Result.success("开通失败，退款失败，请联系管理员");
                            }
                            return Result.error("订单不存在");
                        };
                        return Result.error("支付宝支付反馈失败");
                    }else {
                        //检查订单是否支付
                        if (sysOrdersService.checkIsPay(outTradeNo,2)){
                            return Result.success("开通成功~");
                        }
                        return Result.error("暂未支付");
                    }
                case "wxpay":
                    DeBugUtils.println("微信支付成功反馈开始~");
                    if (!custCheck){
                        Map<String, Object> xmlData = StringUtils.getXmlData(request);
                        outTradeNo = MapUtils.get(xmlData, "out_trade_no");
                        if (WxPayUtils.listenForPaymentResult(xmlData)){
                            //更新订单状态 开通会员
                            DeBugUtils.println("微信支付验证成功",outTradeNo);
                            SysOrders nowOrderInfo = sysOrdersService.getOrderByOutTradeNo(outTradeNo,1);
                            DeBugUtils.println("订单信息：",nowOrderInfo);
                            //更新订单状态 根据uid 支付类型 及单号
                            SysOrders custOrder = new SysOrders();
                            if (nowOrderInfo != null){
                                custOrder.setId(nowOrderInfo.getId());
                                custOrder.setStatus(1);
                                custOrder.setPayAt(new Date());
                                custOrder.setUpdatedAt(new Date());
                                if (sysOrdersService.update(custOrder)){
                                    //开通会员
                                    if (openVip(nowOrderInfo.getIncomeId(),nowOrderInfo.getUid())){
                                        DeBugUtils.println("开通成功");
                                        return Result.success("开通成功~");
                                    }
                                }
                                //直接退款 并取消订单
                                String total_fee = request.getParameter("total_fee");
                                if(WxPayUtils.handleRefund(outTradeNo,total_fee)){
                                    //取消订单
                                    sysOrdersService.upStatusById(nowOrderInfo.getId(),3);
                                    DeBugUtils.println("开通失败，已退款");
                                    return Result.success("开通失败，已退款~");
                                }
                                DeBugUtils.println("开通失败，退款失败,请联系管理员！");
                            }
                            return Result.error("订单不存在");
                        };
                    }else{
                        //检查订单是否支付
                        if (sysOrdersService.checkIsPay(outTradeNo,1)){
                            return Result.success("开通成功~");
                        }
                        return Result.error("暂未支付");
                    }
                default:
                    return Result.error("暂不支持的支付方式");
            }
        } catch (Exception e) {
            return Result.error("支付反馈处理失败：" + e.getMessage());
        }
    }

    //return url 处理接口 只有支付宝
    @RequestMapping("/return/{uPayType}")
    public Result return_url(HttpServletRequest request,
                               @PathVariable(name = "uPayType", required = false) String uPayType) {
        try {
            //检查参数
            if (StringUtils.isEmpty(uPayType)){
                return Result.error("缺少必要参数！");
            }
            switch (uPayType) {
                case "alipay":
                    return Result.success("支付宝支付反馈测试完毕",request.getParameterMap());
                default:
                    return Result.error("暂不支持的支付方式");
            }
        }catch (Exception e){
            return Result.error("支付反馈处理失败：" + e.getMessage());
        }
    }

    //私有函数开通会员
    private boolean openVip(int incomeId,int uid){

        //获取当前用户的会员基础信息
        WebUsersVipser webUsersVipser = webUsersVipserService.getByUid(uid);
        DeBugUtils.println("webUsersVipser:",webUsersVipser);
        Income incomeInfo = incomeService.getById(incomeId);
        DeBugUtils.println("incomeInfo:",incomeInfo);
        if (incomeInfo!=null){
        //如果没有则直接更具incomeInfo 创建一条会员信息
        WebUsersVipser newWebUsersVipser = new WebUsersVipser();
        newWebUsersVipser.setUid(uid);
        newWebUsersVipser.setStatus(1);
        newWebUsersVipser.setRenewals(1);
        newWebUsersVipser.setLastRenewalTime(new Date());
        newWebUsersVipser.setTotalDuration(DateUtils.getSecondsByDays(incomeInfo.getDuration()));
        newWebUsersVipser.setIncomeId(incomeId);
            if (webUsersVipser == null && incomeInfo != null){
                //添加一条会员信息
                newWebUsersVipser.setStartTime(new Date());
                newWebUsersVipser.setEndTime(DateUtils.getDateAfterDays(incomeInfo.getDuration()));
                DeBugUtils.println("添加会员信息:",newWebUsersVipser);
                if (webUsersVipserService.save(newWebUsersVipser)){
                    return true;
                }
                return false;
            }else {
                //根据现有的会员信息及incomeInfo信息 直接sql增加现有的过期时间并变更incomeId
                newWebUsersVipser.setId(webUsersVipser.getId());
                newWebUsersVipser.setEndTime(DateUtils.getDateAfterDays(webUsersVipser.getEndTime(),incomeInfo.getDuration()));
                DeBugUtils.println("更新会员信息:",newWebUsersVipser);
                if (webUsersVipserService.update(newWebUsersVipser)){
                    return true;
                }
                return false;
            }
        }
        return false;
    }

}
