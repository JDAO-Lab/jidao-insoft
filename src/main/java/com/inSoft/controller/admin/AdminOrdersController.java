package com.inSoft.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inSoft.constant.AdminConstant;
import com.inSoft.controller.BaseController;
import com.inSoft.pojo.SysOrders;
import com.inSoft.pojo.result.PageResult;
import com.inSoft.pojo.vo.SysOrdersVo;
import com.inSoft.service.SysOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AdminConstant.PATH_PREFIX+"/orders")
public class AdminOrdersController extends BaseController {

    private static String MODULE_PATH = AdminConstant.MODULE_PATH+"orders/";

    @Autowired
    private SysOrdersService sysOrdersService;

    @GetMapping("/list")
    public ModelAndView list(ModelAndView model){
        model.setViewName(MODULE_PATH+"list");
        return model;
    }

    @GetMapping("/data")
    public PageResult data(SysOrders sysOrders, @RequestParam int page, @RequestParam int limit){
        PageHelper.startPage(page, limit);
        List<SysOrders> sysOrdersList = sysOrdersService.listByCondition(sysOrders);
        // 查询
        List<SysOrdersVo> sysOrdersVoList = sysOrdersList.stream().map(SysOrdersVo::new).collect(Collectors.toList());
        // 封装分页信息
        PageInfo<SysOrdersVo> pageInfo = new PageInfo<>(sysOrdersVoList);
        // 设置分页信息中的总记录数
        pageInfo.setTotal(((Page) sysOrdersList).getTotal());
        return PageResult.success("查询成功", pageInfo);
    }

}
