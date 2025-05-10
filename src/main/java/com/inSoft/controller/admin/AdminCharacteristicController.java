package com.inSoft.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inSoft.constant.AdminConstant;
import com.inSoft.controller.BaseController;
import com.inSoft.pojo.WebCharacteristic;
import com.inSoft.pojo.result.PageResult;
import com.inSoft.pojo.result.Result;
import com.inSoft.pojo.vo.WebCharacteristicVo;
import com.inSoft.service.WebCharacteristicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 特性配置管理
 */
@RestController
@RequestMapping(AdminConstant.PATH_PREFIX+"/characteristic")
public class AdminCharacteristicController extends BaseController {

    private static String MODULE_PATH = AdminConstant.MODULE_PATH+"characteristic/";

    @Autowired
    private WebCharacteristicService webCharacteristicService;


    //列表
    @GetMapping("/list")
    public ModelAndView list(ModelAndView model) {
        model.setViewName(MODULE_PATH+"list");
        return model;
    }

    //数据
    @GetMapping("/data")
    public PageResult data(WebCharacteristic webCharacteristic, @RequestParam int page, @RequestParam int limit){
        PageHelper.startPage(page, limit);
        List<WebCharacteristic> webCharacteristicList = webCharacteristicService.listByCondition(webCharacteristic);
        // 查询
        List<WebCharacteristicVo> webCharacteristicVoList = webCharacteristicList.stream().map(WebCharacteristicVo::new).collect(Collectors.toList());
        // 封装分页信息
        PageInfo<WebCharacteristicVo> pageInfo = new PageInfo<>(webCharacteristicVoList);
        // 设置分页信息中的总记录数
        pageInfo.setTotal(((Page) webCharacteristicList).getTotal());
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
    public Result save(@RequestBody WebCharacteristic webCharacteristic){
        webCharacteristic.setCreatedAt(new Date());
        if (webCharacteristicService.save(webCharacteristic)){
            return Result.success("保存成功");
        }
        return Result.error("操作失败");
    }


    //编辑
    @GetMapping("/edit")
    public ModelAndView edit(ModelAndView model, Integer id){
        WebCharacteristic webCharacteristic = webCharacteristicService.getById(id);
        model.addObject("webCharacteristic", webCharacteristic);
        model.setViewName(MODULE_PATH+"edit");
        return model;
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody WebCharacteristic webCharacteristic){
        webCharacteristic.setUpdatedAt(new Date());
        if (webCharacteristicService.update(webCharacteristic)){
            return Result.success("更新成功");
        }
        return Result.error("操作失败");
    }

    //删除 备注：删除完毕的数据不会在导航类的工具类中检测出数据，会返回为空。
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Integer id){
        if (webCharacteristicService.remove(id)){
            return Result.success("删除成功");
        }
        return Result.error("操作失败");
    }

    //批量删除
    @DeleteMapping("/remove_batch")
    public Result removeBatch(@RequestBody List<Integer> ids){
        if (webCharacteristicService.removeBatch(ids)){
            return Result.success("删除成功");
        }
        return Result.error("操作失败");
    }

    //排序
    @PutMapping("/sort")
    public Result sort(@RequestBody WebCharacteristic webCharacteristic){
        webCharacteristic.setUpdatedAt(new Date());
        if(webCharacteristicService.update(webCharacteristic)){
            return Result.success("排序成功");
        }
        return Result.error("排序失败");
    }

}
