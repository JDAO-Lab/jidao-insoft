package com.inSoft.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inSoft.constant.AdminConstant;
import com.inSoft.controller.BaseController;
import com.inSoft.pojo.AppCat;
import com.inSoft.pojo.result.PageResult;
import com.inSoft.pojo.result.Result;
import com.inSoft.pojo.vo.AppCatVo;
import com.inSoft.service.AppCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户端类型管理
 */
@RestController
@RequestMapping(AdminConstant.PATH_PREFIX+"/app_cat")
public class AdminAppCatController extends BaseController {

    private static String MODULE_PATH = AdminConstant.MODULE_PATH+"app_cat/";

    @Autowired
    private AppCatService appCatService;

    //列表
    @GetMapping("/list")
    public ModelAndView list(ModelAndView model){
        model.setViewName(MODULE_PATH+"list");
        return model;
    }

    //数据
    @GetMapping("/data")
    public PageResult data(AppCat appCat, @RequestParam int page, @RequestParam int limit){
        PageHelper.startPage(page, limit);
        List<AppCat> appCatList = appCatService.listByCondition(appCat);
        List<AppCatVo> appCatVoList = appCatList.stream().map(AppCatVo::new).collect(Collectors.toList());
        PageInfo<AppCatVo> pageInfo = new PageInfo<>(appCatVoList);
        pageInfo.setTotal(((Page) appCatList).getTotal());
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
    public Result save(@RequestBody AppCat appCat){
        appCat.setCreatedAt(new Date());
        if (appCatService.save(appCat)){
            return Result.success("保存成功");
        }
        return Result.error("操作失败");
    }

    //编辑
    @GetMapping("/edit")
    public ModelAndView edit(ModelAndView model, Integer id){
        AppCat appCat = appCatService.getById(id);
        model.addObject("appCat", appCat);
        model.setViewName(MODULE_PATH+"edit");
        return model;
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody AppCat appCat){
        appCat.setUpdatedAt(new Date());
        if (appCatService.update(appCat)){
            return Result.success("更新成功");
        }
        return Result.error("操作失败");
    }

    //删除 备注：删除完毕的数据不会在导航类的工具类中检测出数据，会返回为空。
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Integer id){
        if (appCatService.remove(id)){
            return Result.success("删除成功");
        }
        return Result.error("操作失败");
    }

    //批量删除
    @DeleteMapping("/remove_batch")
    public Result removeBatch(@RequestBody List<Integer> ids){
        if (appCatService.removeBatch(ids)){
            return Result.success("删除成功");
        }
        return Result.error("操作失败");
    }

    //恢复
    @PutMapping("/recovery/{id}")
    public Result recovery(@PathVariable Integer id){
        if (appCatService.recovery(id)){
            return Result.success("恢复成功");
        }
        return Result.error("操作失败");
    }

    //批量恢复
    @PutMapping("/recovery_batch")
    public Result recoveryBatch(@RequestBody List<Integer> ids){
        if (appCatService.recoveryBatch(ids)){
            return Result.success("恢复成功");
        }
        return Result.error("操作失败");
    }


}
