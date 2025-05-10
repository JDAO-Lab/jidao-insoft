package com.inSoft.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inSoft.constant.AdminConstant;
import com.inSoft.controller.BaseController;
import com.inSoft.pojo.WebPostCat;
import com.inSoft.pojo.result.PageResult;
import com.inSoft.pojo.result.Result;
import com.inSoft.pojo.vo.WebPostCatVo;
import com.inSoft.service.WebPostCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 图文类型管理控制器
 */
@RestController
@RequestMapping(AdminConstant.PATH_PREFIX+"/post_cat")
public class AdminPostCatController extends BaseController {

    private static String MODULE_PATH = AdminConstant.MODULE_PATH+"post_cat/";

    @Autowired
    private WebPostCatService webPostCatService;


    //列表
    @GetMapping("/list")
    public ModelAndView list(ModelAndView model) {
        model.setViewName(MODULE_PATH+"list");
        return model;
    }

    //数据
    @GetMapping("/data")
    public PageResult data(WebPostCat webPostCat, @RequestParam int page, @RequestParam int limit){
        PageHelper.startPage(page, limit);
        List<WebPostCat> webPostCatList = webPostCatService.listByCondition(webPostCat);
        // 查询
        List<WebPostCatVo> webPostCatVoList = webPostCatList.stream().map(WebPostCatVo::new).collect(Collectors.toList());
        // 封装分页信息
        PageInfo<WebPostCatVo> pageInfo = new PageInfo<>(webPostCatVoList);
        // 设置分页信息中的总记录数
        pageInfo.setTotal(((Page) webPostCatList).getTotal());
        return PageResult.success("查询成功", pageInfo);
    }

    //添加
    @GetMapping("/add")
    public ModelAndView add(ModelAndView model) {
        model.setViewName(MODULE_PATH+"add");
        return model;
    }

    //保存
    @PostMapping("/save")
    public Result save(@RequestBody WebPostCat webPostCat){
        webPostCat.setCreatedAt(new Date());
        if (webPostCatService.save(webPostCat)){
            return Result.success("保存成功");
        }
        return Result.error("操作失败");
    }


    //编辑
    @GetMapping("/edit")
    public ModelAndView edit(ModelAndView model, Integer id){
        WebPostCat webPostCat = webPostCatService.getById(id);
        model.addObject("webPostCat", webPostCat);
        model.setViewName(MODULE_PATH+"edit");
        return model;
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody WebPostCat webPostCat){
        webPostCat.setUpdatedAt(new Date());
        if (webPostCatService.update(webPostCat)){
            return Result.success("更新成功");
        }
        return Result.error("操作失败");
    }

    //删除 备注：删除完毕的数据不会在导航类的工具类中检测出数据，会返回为空。
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Integer id){
        if (webPostCatService.remove(id)){
            return Result.success("删除成功");
        }
        return Result.error("操作失败");
    }

    //批量删除
    @DeleteMapping("/remove_batch")
    public Result removeBatch(@RequestBody List<Integer> ids){
        if (webPostCatService.removeBatch(ids)){
            return Result.success("删除成功");
        }
        return Result.error("操作失败");
    }

    //恢复
    @PutMapping("/recovery/{id}")
    public Result recovery(@PathVariable Integer id){
        if (webPostCatService.recovery(id)){
            return Result.success("恢复成功");
        }
        return Result.error("操作失败");
    }

    //批量恢复
    @PutMapping("/recovery_batch")
    public Result recoveryBatch(@RequestBody List<Integer> ids){
        if (webPostCatService.recoveryBatch(ids)){
            return Result.success("恢复成功");
        }
        return Result.error("操作失败");
    }

}
