package com.inSoft.controller.admin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.JsonObject;
import com.inSoft.constant.AdminConstant;
import com.inSoft.controller.BaseController;
import com.inSoft.pojo.SysLog;
import com.inSoft.pojo.SysRulesMenu;
import com.inSoft.pojo.vo.SysLogVo;
import com.inSoft.service.SysLogService;
import com.inSoft.service.SysRulesMenuService;
import com.inSoft.service.SysRulesService;
import com.inSoft.service.SysStaticService;
import com.inSoft.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(AdminConstant.PATH_PREFIX)
public class AdminIndexController extends BaseController {

    private static String MODULE_PATH = AdminConstant.MODULE_PATH;

    @Autowired
    private IPUtil ipUtil;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private SysRulesService sysRulesService;

    @Autowired
    private SysStaticService sysStaticService;

    @Autowired
    private SysRulesMenuService sysRulesMenuService;

    @GetMapping("")
    public ModelAndView index(ModelAndView model){
        model.setViewName(MODULE_PATH+"index");
        return model;
    }

    @GetMapping("/console")
    public ModelAndView console(ModelAndView model){
        // 创建 SysInfoUtil 实例
        SysInfoUtil sysInfoUtil = new SysInfoUtil();
        // 调用工具类方法获取系统信息
        String systemNameAndVersion = sysInfoUtil.getSystemNameAndVersion();
        String javaSDKVersion = sysInfoUtil.getJavaSDKVersion();
        long memorySize = sysInfoUtil.getMemorySize();
        String diskPath = "/"; // 替换为实际要检查的磁盘路径
        long diskFreeSpace;
        try {
            diskFreeSpace = sysInfoUtil.getDiskFreeSpace(diskPath);
        } catch (IllegalArgumentException e) {
            diskFreeSpace = -1L; // 处理无效或无法访问的路径
            log.info("获取磁盘信息错误~");
        }
        String mysqlVersion = "Unknown";
        Connection mysqlConnection = null;
        try {
            mysqlConnection = DataSourceUtils.getConnection(dataSource);
            mysqlVersion = sysInfoUtil.getMySQLVersion(mysqlConnection);
        } catch (SQLException e) {
            log.error("获取MySQL版本错误~", e);
        } finally {
            if (mysqlConnection != null) {
                try {
                    mysqlConnection.close();
                } catch (SQLException e) {
                    log.error("关闭连接时发生错误", e);
                }
            }
        }

        // 添加到 ModelAndView
        double memorySizeMb = sysInfoUtil.bytesToMb(memorySize);
        double diskFreeSpaceGb = sysInfoUtil.bytesToGb(diskFreeSpace);
        model.addObject("memorySizeMb", memorySizeMb);
        model.addObject("diskFreeSpaceGb", diskFreeSpaceGb);
        model.addObject("systemVersion", systemNameAndVersion);
        model.addObject("javaSDKVersion", javaSDKVersion);
        model.addObject("mysqlVersion", mysqlVersion);
        model.addObject("activeThreadNum", sysInfoUtil.getActiveThreadNum());
        model.addObject("cpuNum", sysInfoUtil.getCpuNum());

        // 统计四个卡片数据
        //获取今日浏览量
        Map<String, String> todayStartOfEnd = DateUtils.getStartDateAndEndTimeForDays(0, "yyyy-MM-dd HH:mm:ss");
//        DeBugUtils.print("今日日期查询区间："+todayStartOfEnd);
        double todayViewCount = sysStaticService.getTypeValueByDateRange(0,todayStartOfEnd);
        //获取今日注册用户数据
        double todayRegisterCount = sysStaticService.getTypeValueByDateRange(1,todayStartOfEnd);
        //获取今日付款订单数
        double todayPayCount = sysStaticService.getTypeValueByDateRange(4,todayStartOfEnd);
        //获取今日api调用数
        double todayApiCount = sysStaticService.getTypeValueByDateRange(5,todayStartOfEnd);
        //存入视图
        model.addObject("todayViewCount", todayViewCount);
        model.addObject("todayRegisterCount", todayRegisterCount);
        model.addObject("todayPayCount", todayPayCount);
        model.addObject("todayApiCount", todayApiCount);

        //昨日数据统计
        Map<String, String> yesterdayStartOfEnd = DateUtils.getStartDateAndEndTimeForDays(-1, "yyyy-MM-dd HH:mm:ss");
//        DeBugUtils.print("昨日日期查询区间："+yesterdayStartOfEnd);
        double yesterdayViewCount = sysStaticService.getTypeValueByDateRange(0,yesterdayStartOfEnd);
        //获取昨日注册用户数据
        double yesterdayRegisterCount = sysStaticService.getTypeValueByDateRange(1,yesterdayStartOfEnd);
        //获取昨日订单付款数
        double yesterdayPayCount = sysStaticService.getTypeValueByDateRange(4,yesterdayStartOfEnd);
        //获取昨日api调用数
        double yesterdayApiCount = sysStaticService.getTypeValueByDateRange(5,yesterdayStartOfEnd);
        //存入视图
        model.addObject("yesterdayViewCount", yesterdayViewCount);
        model.addObject("yesterdayRegisterCount", yesterdayRegisterCount);
        model.addObject("yesterdayPayCount", yesterdayPayCount);
        model.addObject("yesterdayApiCount", yesterdayApiCount);

        // 检索ip
        String ip = ipUtil.getIpAddress();
        model.addObject("ip", ip);

        // 检索日志数据
        List<SysLog> sysLogs = sysLogService.listByIpAndLimit(ip, 12);
        List<SysLogVo> operationLogs = sysLogs.stream().map(SysLogVo::new).collect(Collectors.toList());
        model.addObject("operationLogs", operationLogs);

        // 获取当前日期
        LocalDateTime currentDateAndTime = LocalDateTime.now();
        String formattedCurrentDateAndTime = currentDateAndTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        model.addObject("currentDate", formattedCurrentDateAndTime);

        // 获取当前秒级时间戳
        long currentTimestampInSeconds = Instant.now().getEpochSecond();
        model.addObject("currentTimestampInSeconds", currentTimestampInSeconds);

        model.setViewName(MODULE_PATH+"console");
        return model;
    }

    /**
     * 返回主题配置json信息
     * @return
     */
    @GetMapping("/get_the_theme")
    public Object getTheTheme(HttpServletRequest request){
        Map sys = (Map) request.getAttribute("sys");
//        DeBugUtils.print(sys.get("theme_config"));
        return MapUtils.get(sys,"theme_config");
    }


    /**
     * 读取当前用户关联的权限组菜单信息
     * @param request
     * @return
     */
    @GetMapping("/get_menu")
    public Object getMenu(HttpServletRequest request){
        Map userInfo = (Map) request.getAttribute("userInfo");
        Integer ruleId = Integer.parseInt((String) userInfo.get("ruleId"));
        String rules = sysRulesService.getRulesById(ruleId);
//        DeBugUtils.print(rules);
        List<SysRulesMenu> userRulesMenu = sysRulesMenuService.getUserRulesByIds(rules);
//        DeBugUtils.print(userRulesMenu);
        List<Object> menuTree = sysRulesMenuService.generateMenuTree(userRulesMenu); // 生成菜单树
//        DeBugUtils.print("菜单树状结构："+menuTree);
        return menuTree;
    }

}
