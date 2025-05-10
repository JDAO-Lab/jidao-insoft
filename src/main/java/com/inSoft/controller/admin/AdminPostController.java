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
import com.inSoft.service.WebPostCatService;
import com.inSoft.service.WebPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 图文管理控制器
 */
@RestController
@RequestMapping(AdminConstant.PATH_PREFIX+"/post")
public class AdminPostController extends BaseController {

    private static String MODULE_PATH = AdminConstant.MODULE_PATH+"post/";

    @Autowired
    private WebPostService webPostService;

    @Autowired
    private WebPostCatService webPostCatService;

    //列表
    @GetMapping("/list")
    public ModelAndView list(ModelAndView model){
        model.setViewName(MODULE_PATH+"list");
        return model;
    }

    //数据
    @GetMapping("/data")
    public PageResult data(WebPost webPost, @RequestParam int page, @RequestParam int limit){
        PageHelper.startPage(page, limit);
        List<WebPost> webPostList = webPostService.listByCondition(webPost);
        // 查询
        List<WebPostVo> webPostVoList = webPostList.stream().map(WebPostVo::new).collect(Collectors.toList());
        // 封装分页信息
        PageInfo<WebPostVo> pageInfo = new PageInfo<>(webPostVoList);
        // 设置分页信息中的总记录数
        pageInfo.setTotal(((Page) webPostList).getTotal());
        return PageResult.success("查询成功", pageInfo);
    }

    //添加
    @GetMapping("/add")
    public ModelAndView add(ModelAndView model) {
        model.setViewName(MODULE_PATH+"add");
        return model;
    }

    //分类select
    @GetMapping("/select")
    public Result select(){
        return Result.success("查询成功", webPostCatService.getSelect());
    }

    //保存
    @PostMapping("/save")
    public Result save(@RequestBody WebPost webPost){
        if (webPost.getCid() == null||webPost.getCid() == 0){
            return Result.error("请选择分类");
        }
        webPost.setCreatedAt(new Date());
        if (webPostService.save(webPost)){
            return Result.success("保存成功");
        }
        return Result.error("操作失败");
    }


    //编辑
    @GetMapping("/edit")
    public ModelAndView edit(ModelAndView model, Integer id){
        WebPost webPost = webPostService.getById(id);
        model.addObject("webPost", webPost);
        model.setViewName(MODULE_PATH+"edit");
        return model;
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody WebPost webPost){
        if (webPost.getCid() == null||webPost.getCid() == 0){
            return Result.error("请选择分类");
        }
        webPost.setUpdatedAt(new Date());
        if (webPostService.update(webPost)){
            return Result.success("更新成功");
        }
        return Result.error("操作失败");
    }

    //删除 备注：删除完毕的数据不会在导航类的工具类中检测出数据，会返回为空。
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Integer id){
        if (webPostService.remove(id)){
            return Result.success("删除成功");
        }
        return Result.error("操作失败");
    }

    //批量删除
    @DeleteMapping("/remove_batch")
    public Result removeBatch(@RequestBody List<Integer> ids){
        if (webPostService.removeBatch(ids)){
            return Result.success("删除成功");
        }
        return Result.error("操作失败");
    }

    //详情
    @GetMapping("/details")
    public ModelAndView details(ModelAndView model, Integer id){
        WebPost webPost = webPostService.getById(id);
        model.addObject("webPost", webPost);
        model.setViewName(MODULE_PATH+"details");
        return model;
    }
    

}
