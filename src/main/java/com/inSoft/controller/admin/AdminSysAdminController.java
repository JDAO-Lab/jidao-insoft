package com.inSoft.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inSoft.constant.AdminConstant;
import com.inSoft.controller.BaseController;
import com.inSoft.pojo.SysAdmin;
import com.inSoft.pojo.result.PageResult;
import com.inSoft.pojo.result.Result;
import com.inSoft.pojo.vo.SysAdminVo;
import com.inSoft.service.SysAdminService;
import com.inSoft.service.SysRulesService;
import com.inSoft.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AdminConstant.PATH_PREFIX+"/sys_admin")
public class AdminSysAdminController extends BaseController {

    private static String MODULE_PATH = AdminConstant.MODULE_PATH+"sys_admin/";

    @Autowired
    private SysAdminService sysAdminService;

    @Autowired
    private SysRulesService sysRulesService;

    //列表
    @GetMapping("/list")
    public ModelAndView list(ModelAndView model){
        model.setViewName(MODULE_PATH+"list");
        return model;
    }

    //数据
    @GetMapping("/data")
    public PageResult data(SysAdmin sysAdmin, @RequestParam int page, @RequestParam int limit){
        // 设置分页参数
        PageHelper.startPage(page, limit);

        // 根据条件查询
        List<SysAdmin> sysAdminList = sysAdminService.listByCondition(sysAdmin);

        // 查询
        List<SysAdminVo> sysAdminVoList = sysAdminList.stream().map(SysAdminVo::new).collect(Collectors.toList());

        // 封装分页信息
        PageInfo<SysAdminVo> pageInfo = new PageInfo<>(sysAdminVoList);
        // 设置分页信息中的总记录数
        pageInfo.setTotal(((Page) sysAdminList).getTotal());

        return PageResult.success("查询成功", pageInfo);

    }

    //添加
    @GetMapping("/add")
    public ModelAndView add(ModelAndView model){
        model.setViewName(MODULE_PATH+"add");
        return model;
    }

    //保存
    @PostMapping("/save")
    public Result save(@RequestBody SysAdmin sysAdmin){
        //验证权限
        if (sysAdmin.getRuleId()==0||sysAdmin.getRuleId()==null){
            return Result.error("权限不能为空。");
        }
        //需要判断用户名是否已经存在，如果存在则不可进行创建！
        String username = sysAdmin.getUsername();
        if (username==null||username.trim().isEmpty()){
            return Result.error("用户名不能为空。");
        }
        if (sysAdminService.isOnlyUsername(username)>0){
            return Result.error("用户名重复。");
        }
        //密码不能为空
        if (sysAdmin.getPassword()==null||sysAdmin.getPassword().trim().isEmpty()){
            return Result.error("密码不能为空。");
        }
        String md5Pwd = MD5Util.md5(sysAdmin.getPassword());
        sysAdmin.setPassword(md5Pwd);
        sysAdmin.setCreatedAt(new Date());
        if(sysAdminService.save(sysAdmin)) {
            return Result.success("保存成功");
        }
        return Result.error("保存失败");
    }

    //获取权限select数据
    @GetMapping("/select_rules")
    public Result selectRules(){
        return Result.success("获取成功",sysRulesService.selectRules());
    }

    //编辑
    @GetMapping("/edit")
    public ModelAndView edit(ModelAndView model,Integer id){
        model.addObject("sysAdmin", sysAdminService.getById(id));
        model.setViewName(MODULE_PATH+"edit");
        return model;
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody SysAdmin sysAdmin){
        //验证权限
        if (sysAdmin.getRuleId()==0||sysAdmin.getRuleId()==null){
            return Result.error("权限不能为空。");
        }
        //密码不为空时执行更新密码
        if (!sysAdmin.getPassword().trim().isEmpty()){
            String md5Pwd = MD5Util.md5(sysAdmin.getPassword());
            sysAdmin.setPassword(md5Pwd);
        }else {
            //为空字符串时不进行提交
            sysAdmin.setPassword(null);
        }
        sysAdmin.setUpdatedAt(new Date());
        if(sysAdminService.update(sysAdmin)) {
            return Result.success("保存成功");
        }
        return Result.error("保存失败");
    }

    //删除
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Integer id){
        if (id==1){
            return Result.error("超级管理员不能删除。");
        }
        if(sysAdminService.remove(id)) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    //批量删除
    @DeleteMapping("/remove_batch")
    public Result removeBatch(@RequestBody List<Integer> ids){
        if (ids.contains(1)){
            return Result.error("超级管理员不能删除。");
        }
        if(sysAdminService.removeBatch(ids)) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    //禁用
    @PutMapping("/disable")
    public Result disable(@RequestBody SysAdmin sysAdmin){
        sysAdmin.setEnable(0);
        sysAdmin.setUpdatedAt(new Date());
        if(sysAdminService.update(sysAdmin)) {
            return Result.success("禁用成功");
        }
        return Result.error("禁用失败");
    }


    //启用
    @PutMapping("/enable")
    public Result enable(@RequestBody SysAdmin sysAdmin){
        sysAdmin.setEnable(1);
        sysAdmin.setUpdatedAt(new Date());
        if(sysAdminService.update(sysAdmin)) {
            return Result.success("启用成功");
        }
        return Result.error("启用失败");
    }

    //资料修改
    @GetMapping( "/profile")
    public ModelAndView profile(ModelAndView model){
        Integer id = getUserId();
        model.addObject("sysAdmin", sysAdminService.getById(id));
        model.setViewName(MODULE_PATH+"profile");
        return model;
    }

}
