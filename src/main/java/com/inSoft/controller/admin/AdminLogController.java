package com.inSoft.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inSoft.constant.AdminConstant;
import com.inSoft.controller.BaseController;
import com.inSoft.pojo.SysLog;
import com.inSoft.pojo.vo.SysLogVo;
import com.inSoft.service.SysLogService;
import com.inSoft.pojo.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AdminConstant.PATH_PREFIX+"/log")
public class AdminLogController extends BaseController {

    private static String MODULE_PATH = AdminConstant.MODULE_PATH+"log/";


    @Autowired
    private SysLogService sysLogService;

    @GetMapping("/sys_log")
    public ModelAndView sysLog(ModelAndView model){
        model.setViewName(MODULE_PATH+"sys_log");
        return model;
    }

    @GetMapping("/sys_log/list")
    public PageResult sysLogList(SysLog sysLog,@RequestParam int page,@RequestParam int limit){

        // 设置分页参数，pageNum为当前页码，pageSize为每页显示数量
        PageHelper.startPage(page, limit);

        // 根据条件查询日志列表
        List<SysLog> sysLogList = sysLogService.listByCondition(sysLog);

        // 将查询到的SysLog对象转换为SysLogVO对象
        List<SysLogVo> sysLogVoList = sysLogList.stream().map(SysLogVo::new).collect(Collectors.toList());

        // 封装分页信息
        PageInfo<SysLogVo> pageInfo = new PageInfo<>(sysLogVoList);
        // 设置分页信息中的总记录数
        pageInfo.setTotal(((Page) sysLogList).getTotal());

        return PageResult.success("查询成功", pageInfo);
    }

}