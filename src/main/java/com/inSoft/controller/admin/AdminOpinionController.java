package com.inSoft.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inSoft.constant.AdminConstant;
import com.inSoft.controller.BaseController;
import com.inSoft.pojo.Opinion;
import com.inSoft.pojo.result.PageResult;
import com.inSoft.pojo.result.Result;
import com.inSoft.pojo.vo.OpinionVo;
import com.inSoft.service.OpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AdminConstant.PATH_PREFIX+"/opinion")
public class AdminOpinionController extends BaseController {

    private static String MODULE_PATH = AdminConstant.MODULE_PATH+"opinion/";

    @Autowired
    private OpinionService opinionService;

    @GetMapping("/list")
    public ModelAndView list(ModelAndView model){
        model.setViewName(MODULE_PATH+"list");
        return model;
    }

    @GetMapping("/data")
    public PageResult data(Opinion opinion, @RequestParam int page, @RequestParam int limit){
        // 设置分页参数
        PageHelper.startPage(page, limit);
        // 根据条件查询
        List<Opinion> opinionList = opinionService.listByCondition(opinion);
        // 查询
        List<OpinionVo> opinionVoList = opinionList.stream().map(OpinionVo::new).collect(Collectors.toList());
        // 封装分页信息
        PageInfo<OpinionVo> pageInfo = new PageInfo<>(opinionVoList);
        // 设置分页信息中的总记录数
        pageInfo.setTotal(((Page) opinionList).getTotal());
        return PageResult.success("查询成功", pageInfo);
    }

    //详情
    @GetMapping("/details")
    public ModelAndView detail(ModelAndView model,@RequestParam Integer id){
        Opinion opinion = opinionService.getById(id);
        OpinionVo opinionVo = new OpinionVo(opinion);
        model.addObject("opinion",opinionVo);
        model.setViewName(MODULE_PATH+"details");
        return model;
    }

    //批量删除
    @DeleteMapping("/remove_batch")
    public Result removeBatch(@RequestBody List<Integer> ids){
        boolean result = opinionService.removeBatch(ids);
        if (result){
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }



}
