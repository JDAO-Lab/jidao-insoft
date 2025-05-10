package com.inSoft.controller.admin;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inSoft.constant.AdminConstant;
import com.inSoft.controller.BaseController;
import com.inSoft.pojo.AppCat;
import com.inSoft.pojo.AppVersion;
import com.inSoft.pojo.result.PageResult;
import com.inSoft.pojo.result.Result;
import com.inSoft.pojo.vo.AppVersionVo;
import com.inSoft.service.AppCatService;
import com.inSoft.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户端版本管理
 */
@RestController
@RequestMapping(AdminConstant.PATH_PREFIX+"/app_version")
public class AdminAppVersionController extends BaseController {

    private static String MODULE_PATH = AdminConstant.MODULE_PATH+"app_version/";

    @Autowired
    private AppVersionService appVersionService;

    @Autowired
    private AppCatService appcatService;

    //列表
    @GetMapping("/list")
    public ModelAndView list(ModelAndView model){
        //获取id首位并赋值到web请求数据
        model.addObject("cid",appcatService.getFirstId());
        model.setViewName(MODULE_PATH+"list");
        return model;
    }

    @GetMapping("/select")
    public Result select(){
        //默认选中一个菜单信息，避免空
        List<AppCat> webNavs = appcatService.getSelect();
        return Result.success("获取成功",webNavs);
    }

    //数据
    @GetMapping("/data")
    public PageResult data(AppVersion appVersion, @RequestParam int page, @RequestParam int limit){
        PageHelper.startPage(page, limit);
        List<AppVersion> appVersionList = appVersionService.listByCondition(appVersion);
        List<AppVersionVo> appVersionVoList = appVersionList.stream().map(AppVersionVo::new).collect(Collectors.toList());
        PageInfo<AppVersionVo> pageInfo = new PageInfo<>(appVersionVoList);
        pageInfo.setTotal(((Page) appVersionList).getTotal());
        return PageResult.success("查询成功", pageInfo);
    }

    //添加
    @GetMapping("/add")
    public ModelAndView add(ModelAndView model,Integer cid){
        model.addObject("cid",cid);
        model.setViewName(MODULE_PATH+"add");
        return model;
    }

    //保存
    @PostMapping("/save")
    public Result save(@RequestBody AppVersion appVersion){
        appVersion.setCreatedAt(new Date());
        if (appVersionService.save(appVersion)){
            return Result.success("保存成功");
        }
        return Result.error("操作失败");
    }

    //编辑
    @GetMapping("/edit")
    public ModelAndView edit(ModelAndView model, Integer id){
        AppVersion appVersion = appVersionService.getById(id);
        model.addObject("appVersion", appVersion);
        model.setViewName(MODULE_PATH+"edit");
        return model;
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody AppVersion appVersion){
        appVersion.setUpdatedAt(new Date());
        if (appVersionService.update(appVersion)){
            return Result.success("更新成功");
        }
        return Result.error("操作失败");
    }

    //删除 备注：删除完毕的数据不会在导航类的工具类中检测出数据，会返回为空。
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Integer id){
        if (appVersionService.remove(id)){
            return Result.success("删除成功");
        }
        return Result.error("操作失败");
    }

    //批量删除
    @DeleteMapping("/remove_batch")
    public Result removeBatch(@RequestBody List<Integer> ids){
        if (appVersionService.removeBatch(ids)){
            return Result.success("删除成功");
        }
        return Result.error("操作失败");
    }

    //恢复
    @PutMapping("/recovery/{id}")
    public Result recovery(@PathVariable Integer id){
        if (appVersionService.recovery(id)){
            return Result.success("恢复成功");
        }
        return Result.error("操作失败");
    }

    //批量恢复
    @PutMapping("/recovery_batch")
    public Result recoveryBatch(@RequestBody List<Integer> ids){
        if (appVersionService.recoveryBatch(ids)){
            return Result.success("恢复成功");
        }
        return Result.error("操作失败");
    }

}
