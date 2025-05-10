package com.inSoft.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inSoft.constant.AdminConstant;
import com.inSoft.controller.BaseController;
import com.inSoft.pojo.SysRules;
import com.inSoft.pojo.SysRulesMenu;
import com.inSoft.pojo.result.PageResult;
import com.inSoft.pojo.result.Result;
import com.inSoft.pojo.result.ResultTree;
import com.inSoft.pojo.vo.SysRulesVo;
import com.inSoft.service.SysRulesMenuService;
import com.inSoft.service.SysRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AdminConstant.PATH_PREFIX+"/rules")
public class AdminRulesController extends BaseController {

    private static String MODULE_PATH = AdminConstant.MODULE_PATH+"rules/";

    @Autowired
    private SysRulesService sysRulesService;

    @Autowired
    private SysRulesMenuService sysRulesMenuService;

    @GetMapping("/list")
    public ModelAndView list(ModelAndView model){
        model.setViewName(MODULE_PATH+"list");
        return model;
    }

    //获取列表数据data方法
    @GetMapping("/data")
    public PageResult data(SysRules sysRules, @RequestParam int page, @RequestParam int limit){

        // 设置分页参数，pageNum为当前页码，pageSize为每页显示数量
        PageHelper.startPage(page, limit);

        // 根据条件查询日志列表
        List<SysRules> sysRulesList = sysRulesService.listByCondition(sysRules);

        // 查询
        List<SysRulesVo> sysRulesVoList = sysRulesList.stream().map(SysRulesVo::new).collect(Collectors.toList());

        // 封装分页信息
        PageInfo<SysRulesVo> pageInfo = new PageInfo<>(sysRulesVoList);
        // 设置分页信息中的总记录数
        pageInfo.setTotal(((Page) sysRulesList).getTotal());

        return PageResult.success("查询成功", pageInfo);
    }

    //添加界面
    @GetMapping("/add")
    public ModelAndView add(ModelAndView model){
        model.setViewName(MODULE_PATH+"add");
        return model;
    }

    //保存
    @PostMapping("/save")
    public Result save(@RequestBody SysRules sysRules){
        if (sysRules.getRules()==null||sysRules.getRules().isEmpty()){
            return Result.error("请选择权限");
        }
        sysRules.setCreatedAt(new Date());
        if (sysRulesService.save(sysRules)){
            return Result.success("保存成功");
        }
        return Result.error("保存失败");
    }

    //编辑界面，根据id检索数据并赋值
    @GetMapping("/edit")
    public ModelAndView edit(ModelAndView model, Integer id){
        model.addObject("sysRule", sysRulesService.getById(id));
        model.setViewName(MODULE_PATH+"edit");
        return model;
    }

    //根据id获取rules的参数信息
    @GetMapping("/get_role_dept")
    public ResultTree getRoleDept(@RequestParam(required = false) Integer id) {
        List<SysRulesMenu> sysRulesMenus = sysRulesMenuService.list();
        //如果id传递了，那么则进行检索默认选中的状态数据
        String rules = "";
        if (id!=null&&id>0){
             rules = sysRulesService.getRulesById(id);
        }
        List<Object> list = sysRulesMenuService.getSelectTree(sysRulesMenus,rules);
        return ResultTree.success(list);
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody SysRules sysRules){
        //判断权限是否为空
        if (sysRules.getRules()==null||sysRules.getRules().isEmpty()){
            return Result.error("请选择权限");
        }
        sysRules.setUpdatedAt(new Date());
        if (sysRulesService.update(sysRules)){
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }

    //删除
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Integer id){
        //验证是否存在超级管理员权限
        if (id==1){
            return Result.error("管理员权限禁止删除！");
        }
        if (sysRulesService.remove(id)){
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    //批量删除
    @DeleteMapping("/remove_batch")
    public Result removeBatch(@RequestBody List<Integer> ids){
        //验证是否存在超级管理员权限
        if (ids.contains(1)){
            return Result.error("管理员权限禁止删除！");
        }
        if (sysRulesService.removeBatch(ids)){
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    //恢复
    @PutMapping("/recovery/{id}")
    public Result recovery(@PathVariable Integer id){
        if (sysRulesService.recovery(id)){
            return Result.success("恢复成功");
        }
        return Result.error("恢复失败");
    }

    //批量恢复
    @PutMapping("/recovery_batch")
    public Result recoveryBatch(@RequestBody List<Integer> ids){
        if (sysRulesService.recoveryBatch(ids)){
            return Result.success("恢复成功");
        }
        return Result.error("恢复失败");
    }

}
