package com.inSoft.controller.admin;

import com.inSoft.constant.AdminConstant;
import com.inSoft.controller.BaseController;
import com.inSoft.pojo.WebNav;
import com.inSoft.pojo.WebNavCat;
import com.inSoft.pojo.result.PageResult;
import com.inSoft.pojo.result.Result;
import com.inSoft.pojo.vo.WebNavVo;
import com.inSoft.service.WebNavCatService;
import com.inSoft.service.WebNavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AdminConstant.PATH_PREFIX+"/nav")
public class AdminNavController extends BaseController {

    private static String MODULE_PATH = AdminConstant.MODULE_PATH+"nav/";

    @Autowired
    private WebNavService webNavService;

    @Autowired
    private WebNavCatService webNavCatService;

    //列表
    @GetMapping("/list")
    public ModelAndView list(ModelAndView model)
    {
        //获取id首位并赋值到web请求数据
        model.addObject("cid",webNavCatService.getFirstId());
        model.setViewName(MODULE_PATH+"list");
        return model;
    }

    //数据 这是二级菜单逻辑，无限极需修改存储和显示逻辑
    @GetMapping("/data")
    public PageResult data(WebNav webNav){
        List<WebNav> webNavList = webNavService.listByCondition(webNav);
        List<WebNavVo> webNavVoList = webNavList.stream().map(WebNavVo::new).collect(Collectors.toList());
        return PageResult.success("查询成功", webNavVoList);
    }

    //位置筛选框
    @GetMapping("/select")
    public Result select(){
        //默认选中一个菜单信息，避免空
        List<WebNavCat> webNavs = webNavCatService.getSelect();
        return Result.success("获取成功",webNavs);
    }

    //添加 可选pid为0的数据
    @GetMapping("/add")
    public ModelAndView add(ModelAndView model,Integer cid){
        model.addObject("cid",cid);
        model.setViewName(MODULE_PATH+"add");
        return model;
    }

    //父级菜单select数据
    @GetMapping("/get_top_menu")
    public Result getTopMenu(@RequestParam(value = "cid" ,required = false) Integer cid){
        List<WebNav> webNavs = webNavService.getTopMenu(cid);
        return Result.success("获取成功",webNavs);
    }

    //保存
    @PostMapping("/save")
    public Result save(@RequestBody WebNav webNav){
        if(webNav.getCid()==null){
            return Result.error("导航位置未选择！");
        }
        webNav.setCreatedAt(new Date());
        if(webNavService.save(webNav)){
            return Result.success("保存成功");
        }
        return Result.error("保存失败");
    }

    //编辑 可选pid为0的数据
    @GetMapping("/edit")
    public ModelAndView edit(ModelAndView model, Integer id){
        WebNav webNav = webNavService.getById(id);
        model.addObject("webNav",webNav);
        model.setViewName(MODULE_PATH+"edit");
        return model;
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody WebNav webNav){
        if(webNav.getCid()==null){
            return Result.error("导航位置未选择！");
        }
        webNav.setUpdatedAt(new Date());
        if(webNavService.update(webNav)){
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }

    //删除
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Integer id){
        if (webNavService.countByPid(id)>0){
            return Result.error("请先删除子数据~");
        }
        if(webNavService.remove(id)){
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    //批量删除
    @DeleteMapping("/remove_batch")
    public Result removeBatch(@RequestBody List<Integer> ids){
        if (webNavService.countByPids(ids)>0){
            return Result.error("请先删除子数据~");
        }
        if(webNavService.removeBatch(ids)){
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    //禁用
    //启用
    @PutMapping("/enable")
    public Result enable(@RequestBody WebNav webNav){
        webNav.setEnable(1);
        webNav.setUpdatedAt(new Date());
        if (webNavService.update(webNav)){
            return Result.success("已启用！");
        }
        return Result.error("启用失败!");
    }

    //禁用
    @PutMapping("/disable")
    public Result disable(@RequestBody WebNav webNav){
        webNav.setEnable(0);
        webNav.setUpdatedAt(new Date());
        if (webNavService.update(webNav)){
            return Result.success("已禁用！");
        }
        return Result.error("禁用失败!");
    }

    //排序
    @PutMapping("/sort")
    public Result sort(@RequestBody WebNav webNav){
        webNav.setUpdatedAt(new Date());
        if(webNavService.update(webNav)){
            return Result.success("排序成功");
        }
        return Result.error("排序失败");
    }

}
