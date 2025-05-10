package com.inSoft.controller.admin;

import com.github.pagehelper.PageInfo;
import com.inSoft.constant.AdminConstant;
import com.inSoft.controller.BaseController;
import com.inSoft.pojo.SysRulesMenu;
import com.inSoft.pojo.result.Result;
import com.inSoft.pojo.result.ResultTree;
import com.inSoft.pojo.vo.SysRulesMenuVo;
import com.inSoft.service.SysRulesMenuService;
import com.inSoft.pojo.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AdminConstant.PATH_PREFIX+"/menus")
public class AdminMenusController extends BaseController {

    private static String MODULE_PATH = AdminConstant.MODULE_PATH+"menus/";

    @Autowired
    private SysRulesMenuService sysRulesMenuService;

    @GetMapping("/list")
    public ModelAndView list(ModelAndView model){
        model.setViewName(MODULE_PATH+"list");
        return model;
    }

    @GetMapping("/data")
    public PageResult data(){

        List<SysRulesMenu> sysRulesMenuList = sysRulesMenuService.list();

        List<SysRulesMenuVo> sysRulesMenuVoList = sysRulesMenuList.stream().map(SysRulesMenuVo::new).collect(Collectors.toList());

        PageInfo<SysRulesMenuVo> pageInfo = new PageInfo<>(sysRulesMenuVoList);

        return PageResult.success("查询成功", pageInfo);

    }

    @GetMapping("/add")
    public ModelAndView add(ModelAndView model){
        model.setViewName(MODULE_PATH+"add");
        return model;
    }

    @GetMapping("/edit")
    public ModelAndView edit(ModelAndView model, Integer id) {
        model.addObject("sysRulesMenu", sysRulesMenuService.getById(id));
        model.setViewName(MODULE_PATH+"edit");
        return model;
    }

    //查询父级select框
    @GetMapping("/selectParent")
    public ResultTree selectParent(){
        List<SysRulesMenu> sysRulesMenus = sysRulesMenuService.list();
        List<Object> list = sysRulesMenuService.getSelectTree(sysRulesMenus,""); // 生成菜单树
        SysRulesMenu sysRulesMenuList = new SysRulesMenu();
        sysRulesMenuList.setTitle("顶级菜单");
        sysRulesMenuList.setId(0);
        sysRulesMenuList.setPid(-1);
        list.add(sysRulesMenuList);
        return ResultTree.success(list);
    }

    //保存
    @PostMapping("/save")
    public Result save(@RequestBody SysRulesMenu sysRulesMenu){

        sysRulesMenu.setCreatedAt(new Date());
        if (sysRulesMenuService.save(sysRulesMenu)){
            return Result.success("保存完毕!");
        }

        return Result.error("保存失败!");
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody SysRulesMenu sysRulesMenu){

        sysRulesMenu.setUpdatedAt(new Date());
        if (sysRulesMenuService.update(sysRulesMenu)){
            return Result.success("保存完毕!");
        }

        return Result.error("保存失败!");
    }

    //删除
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Integer id){
        //检测是否存在下级
        if (sysRulesMenuService.getSonByPid(id)>0){
            return Result.error("请先删除子级");
        }else {
            if (sysRulesMenuService.remove(id)){
                return Result.success("删除完毕！");
            }
        }
        return Result.error("删除失败！");
    }

    //批量删除
    @DeleteMapping("/remove_batch")
    public Result removeBatch(@RequestBody List<Integer> ids) {

        //批量检测子级是否存在
        if (sysRulesMenuService.getSonByPidBatch(ids)>0){
            return Result.error("批量数据中有子级未删除！");
        }else {
            if (sysRulesMenuService.removeBatch(ids)){
                return Result.success("批量删除完毕！");
            }
        }

        return Result.error("批量删除失败！");
    }

    //启用
    @PutMapping("/enable")
    public Result enable(@RequestBody SysRulesMenu sysRulesMenu){
        sysRulesMenu.setEnable(1);
        sysRulesMenu.setUpdatedAt(new Date());
        if (sysRulesMenuService.update(sysRulesMenu)){
            return Result.success("已启用！");
        }
        return Result.error("启用失败!");
    }

    //禁用
    @PutMapping("/disable")
    public Result disable(@RequestBody SysRulesMenu sysRulesMenu){
        sysRulesMenu.setEnable(0);
        sysRulesMenu.setUpdatedAt(new Date());
        if (sysRulesMenuService.update(sysRulesMenu)){
            return Result.success("已禁用！");
        }
        return Result.error("禁用失败!");
    }

    //排序
    @PutMapping("/sort")
    public Result sort(@RequestBody SysRulesMenu sysRulesMenu){
        sysRulesMenu.setUpdatedAt(new Date());
        if (sysRulesMenuService.update(sysRulesMenu)){
            return Result.success("完成排序！");
        }
        return Result.error("排序失败!");
    }

}
