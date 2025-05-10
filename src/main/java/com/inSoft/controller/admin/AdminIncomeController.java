package com.inSoft.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inSoft.constant.AdminConstant;
import com.inSoft.controller.BaseController;
import com.inSoft.pojo.Income;
import com.inSoft.pojo.result.PageResult;
import com.inSoft.pojo.result.Result;
import com.inSoft.pojo.vo.IncomeVo;
import com.inSoft.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AdminConstant.PATH_PREFIX+"/income")
public class AdminIncomeController extends BaseController {

    private static String MODULE_PATH = AdminConstant.MODULE_PATH+"income/";

    @Autowired
    private IncomeService incomeService;

    @GetMapping("/list")
    public ModelAndView list(ModelAndView model){
        model.setViewName(MODULE_PATH+"list");
        return model;
    }

    @GetMapping("/data")
    public PageResult data(Income income, @RequestParam int page, @RequestParam int limit){
        PageHelper.startPage(page, limit);
        List<Income> incomeList = incomeService.listByCondition(income);
        // 查询
        List<IncomeVo> incomeVoList = incomeList.stream().map(IncomeVo::new).collect(Collectors.toList());
        // 封装分页信息
        PageInfo<IncomeVo> pageInfo = new PageInfo<>(incomeVoList);
        // 设置分页信息中的总记录数
        pageInfo.setTotal(((Page) incomeList).getTotal());
        return PageResult.success("查询成功", pageInfo);
    }

    //添加页面
    @GetMapping("/add")
    public ModelAndView add(ModelAndView model){
        model.setViewName(MODULE_PATH+"add");
        return model;
    }

    //保存
    @PostMapping("/save")
    public Result save(@RequestBody Income income){
        income.setCreatedAt(new Date());
        if (incomeService.save(income)){
            return Result.success("保存成功");
        };
        return Result.success("保存失败");
    }

    //编辑界面
    @GetMapping("/edit")
    public ModelAndView edit(ModelAndView model, Integer id){
        Income income = incomeService.getById(id);
        model.addObject("income", income);
        model.setViewName(MODULE_PATH+"edit");
        return model;
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Income income){
        income.setUpdatedAt(new Date());
        if (incomeService.update(income)){
            return Result.success("更新成功");
        };
        return Result.success("更新失败");
    }

    //删除
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Integer id){
        if (incomeService.remove(id)){
            return Result.success("删除成功");
        };
        return Result.success("删除失败");
    }

    //批量删除
    @DeleteMapping("/remove_batch")
    public Result removeBatch(@RequestBody List<Integer> ids){
        if (incomeService.removeBatch(ids)){
            return Result.success("删除成功");
        };
        return Result.success("删除失败");
    }

    //恢复
    @PutMapping("/recovery/{id}")
    public Result recovery(@PathVariable Integer id){
        if (incomeService.recovery(id)){
            return Result.success("恢复成功");
        };
        return Result.success("恢复失败");
    }

    //批量恢复
    @PutMapping("/recovery_batch")
    public Result recoveryBatch(@RequestBody List<Integer> ids){
        if (incomeService.recoveryBatch(ids)){
            return Result.success("恢复成功");
        };
        return Result.success("恢复失败");
    }

    //排序
    @PutMapping("/sort")
    public Result sort(@RequestBody Income income){
        if (incomeService.update(income)){
            return Result.success("排序成功");
        };
        return Result.success("排序失败");
    }


}
