package com.inSoft.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inSoft.constant.AdminConstant;
import com.inSoft.controller.BaseController;
import com.inSoft.pojo.WebPost;
import com.inSoft.pojo.result.PageResult;
import com.inSoft.pojo.result.Result;
import com.inSoft.pojo.vo.WebPostVo;
import com.inSoft.service.WebPostRecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 图文回收站控制器
 */
@RestController
@RequestMapping(AdminConstant.PATH_PREFIX+"/post_recovery")
public class AdminPostRecoveryController extends BaseController {

    private static String MODULE_PATH = AdminConstant.MODULE_PATH+"post_recovery/";

    @Autowired
    private WebPostRecoveryService webPostRecoveryService;

    //列表
    @GetMapping("/list")
    public ModelAndView list(ModelAndView model)
    {
        model.setViewName(MODULE_PATH+"list");
        return model;
    }

    //数据
    @GetMapping("/data")
    public PageResult data(WebPost webPost, @RequestParam int page, @RequestParam int limit){
        PageHelper.startPage(page, limit);
        List<WebPost> webPostList = webPostRecoveryService.listByCondition(webPost);
        // 查询
        List<WebPostVo> webPostVoList = webPostList.stream().map(WebPostVo::new).collect(Collectors.toList());
        // 封装分页信息
        PageInfo<WebPostVo> pageInfo = new PageInfo<>(webPostVoList);
        // 设置分页信息中的总记录数
        pageInfo.setTotal(((Page) webPostList).getTotal());
        return PageResult.success("查询成功", pageInfo);
    }

    //删除 备注：删除完毕的数据不会在导航类的工具类中检测出数据，会返回为空。
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Integer id){
        if (webPostRecoveryService.remove(id)){
            return Result.success("删除成功");
        }
        return Result.error("操作失败");
    }

    //恢复
    @PutMapping("/recovery/{id}")
    public Result recovery(@PathVariable Integer id){
        if (webPostRecoveryService.recovery(id)){
            return Result.success("恢复成功");
        }
        return Result.error("操作失败");
    }

    //批量恢复
    @PutMapping("/recovery_batch")
    public Result recoveryBatch(@RequestBody List<Integer> ids){
        if (webPostRecoveryService.recoveryBatch(ids)){
            return Result.success("恢复成功");
        }
        return Result.error("操作失败");
    }

}
