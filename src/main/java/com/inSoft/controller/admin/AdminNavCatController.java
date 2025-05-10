package com.inSoft.controller.admin;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.inSoft.constant.AdminConstant;
import com.inSoft.controller.BaseController;
import com.inSoft.pojo.WebNavCat;
import com.inSoft.pojo.result.PageResult;
import com.inSoft.pojo.result.Result;
import com.inSoft.pojo.vo.WebNavCatVo;
import com.inSoft.service.WebNavCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AdminConstant.PATH_PREFIX+"/nav_cat")
public class AdminNavCatController extends BaseController {

    private static String MODULE_PATH = AdminConstant.MODULE_PATH+"nav_cat/";

    @Autowired
    private WebNavCatService webNavCatService;

    //列表
    @GetMapping("/list")
    public ModelAndView list(ModelAndView model){
        model.setViewName(MODULE_PATH+"list");
        return model;
    }

    //数据
    @GetMapping("/data")
    public PageResult data(WebNavCat webNavCat, @RequestParam int page, @RequestParam int limit){
        PageHelper.startPage(page, limit);
        List<WebNavCat> webNavCatList = webNavCatService.listByCondition(webNavCat);
        List<WebNavCatVo> webNavCatVoList = webNavCatList.stream().map(WebNavCatVo::new).collect(Collectors.toList());
        PageInfo<WebNavCatVo> pageInfo = new PageInfo<>(webNavCatVoList);
        pageInfo.setTotal(((Page) webNavCatList).getTotal());
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
    public Result save(@RequestBody WebNavCat webNavCat){
        webNavCat.setCreatedAt(new Date());
        if (webNavCatService.save(webNavCat)){
            return Result.success("保存成功");
        }
        return Result.error("操作失败");
    }

    //编辑
    @GetMapping("/edit")
    public ModelAndView edit(ModelAndView model, Integer id){
        WebNavCat webNavCat = webNavCatService.getById(id);
        model.addObject("webNavCat", webNavCat);
        model.setViewName(MODULE_PATH+"edit");
        return model;
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody WebNavCat webNavCat){
        webNavCat.setUpdatedAt(new Date());
        if (webNavCatService.update(webNavCat)){
            return Result.success("更新成功");
        }
        return Result.error("操作失败");
    }

    //删除 备注：删除完毕的数据不会在导航类的工具类中检测出数据，会返回为空。
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Integer id){
        if (webNavCatService.remove(id)){
            return Result.success("删除成功");
        }
        return Result.error("操作失败");
    }

    //批量删除
    @DeleteMapping("/remove_batch")
    public Result removeBatch(@RequestBody List<Integer> ids){
        if (webNavCatService.removeBatch(ids)){
            return Result.success("删除成功");
        }
        return Result.error("操作失败");
    }

    //恢复
    @PutMapping("/recovery/{id}")
    public Result recovery(@PathVariable Integer id){
        if (webNavCatService.recovery(id)){
            return Result.success("恢复成功");
        }
        return Result.error("操作失败");
    }

    //批量恢复
    @PutMapping("/recovery_batch")
    public Result recoveryBatch(@RequestBody List<Integer> ids){
        if (webNavCatService.recoveryBatch(ids)){
            return Result.success("恢复成功");
        }
        return Result.error("操作失败");
    }

}
