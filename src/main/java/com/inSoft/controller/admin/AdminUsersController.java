package com.inSoft.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inSoft.constant.AdminConstant;
import com.inSoft.controller.BaseController;
import com.inSoft.pojo.WebUsers;
import com.inSoft.pojo.WebUsersVipser;
import com.inSoft.pojo.result.PageResult;
import com.inSoft.pojo.result.Result;
import com.inSoft.pojo.vo.WebUsersVipserVo;
import com.inSoft.pojo.vo.WebUsersVo;
import com.inSoft.service.WebUsersService;
import com.inSoft.service.WebUsersVipserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AdminConstant.PATH_PREFIX+"/users")
public class AdminUsersController extends BaseController {

    private static String MODULE_PATH = AdminConstant.MODULE_PATH+"users/";

    @Autowired
    private WebUsersService webUsersService;

    @Autowired
    private WebUsersVipserService webUsersVipserService;

    @GetMapping("/list")
    public ModelAndView list(ModelAndView model){
        model.setViewName(MODULE_PATH+"list");
        return model;
    }

    @GetMapping("/data")
    public PageResult data(WebUsers webUsers, @RequestParam int page, @RequestParam int limit){
        // 设置分页参数
        PageHelper.startPage(page, limit);
        // 根据条件查询
        List<WebUsers> webUsersList = webUsersService.listByCondition(webUsers);
        // 查询
        List<WebUsersVo> webUsersVoList = webUsersList.stream().map(WebUsersVo::new).collect(Collectors.toList());
        // 封装分页信息
        PageInfo<WebUsersVo> pageInfo = new PageInfo<>(webUsersVoList);
        // 设置分页信息中的总记录数
        pageInfo.setTotal(((Page) webUsersList).getTotal());
        return PageResult.success("查询成功", pageInfo);
    }

    //详情
    @GetMapping("/details")
    public ModelAndView details(ModelAndView model,@RequestParam Integer id){
        WebUsers webUsers = webUsersService.getById(id);
        WebUsersVo webUsersVo = new WebUsersVo(webUsers);
        model.addObject("webUsers",webUsersVo);
        //获取会员信息，如果开通则显示会员情况
        WebUsersVipser webUsersVipsers = webUsersVipserService.getByUid(webUsers.getId());
        if (webUsersVipsers != null){
            WebUsersVipserVo webUsersVipserVo = new WebUsersVipserVo(webUsersVipsers);
            //写入会员信息到详情页
            model.addObject("webUsersVipser",webUsersVipserVo);
        }
        model.setViewName(MODULE_PATH+"details");
        return model;
    }

    //禁用
    @PutMapping("/disable")
    public Result disable(@RequestBody WebUsers webUsers){
        webUsers.setEnable(0);
        webUsers.setUpdatedAt(new Date());
        if(webUsersService.update(webUsers)) {
            return Result.success("禁用成功");
        }
        return Result.error("禁用失败");
    }

    //批量禁用
    @PutMapping("/disable_batch")
    public Result disableBatch(@RequestBody List<Integer> ids){
        if(webUsersService.disableBatch(ids)) {
            return Result.success("禁用成功");
        }
        return Result.error("禁用失败");
    }

    //启用
    @PutMapping("/enable")
    public Result enable(@RequestBody WebUsers webUsers){
        webUsers.setEnable(1);
        webUsers.setUpdatedAt(new Date());
        if(webUsersService.update(webUsers)) {
            return Result.success("启用成功");
        }
        return Result.error("启用失败");
    }

    //批量启用
    @PutMapping("/enable_batch")
    public Result enableBatch(@RequestBody List<Integer> ids){
        if(webUsersService.enableBatch(ids)) {
            return Result.success("启用成功");
        }
        return Result.error("启用失败");
    }



}
