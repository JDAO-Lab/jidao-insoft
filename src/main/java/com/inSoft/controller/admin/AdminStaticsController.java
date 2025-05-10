package com.inSoft.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inSoft.constant.AdminConstant;
import com.inSoft.controller.BaseController;
import com.inSoft.pojo.SysStatic;
import com.inSoft.pojo.result.PageResult;
import com.inSoft.pojo.vo.SysStaticVo;
import com.inSoft.service.SysStaticService;
import com.inSoft.utils.DateUtils;
import com.inSoft.utils.DeBugUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AdminConstant.PATH_PREFIX+"/statics")
public class AdminStaticsController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(AdminStaticsController.class);
    private static String MODULE_PATH = AdminConstant.MODULE_PATH+"statics/";

    @Autowired
    private SysStaticService sysStaticService;

    /**
     * 统计所有数据，可选择类型和时间区间
     * @param model
     * @return
     */
    @GetMapping("/all")
    public ModelAndView all(ModelAndView model){
        model.setViewName(MODULE_PATH+"all");
        return model;
    }

    /**
     * 获取分页的条件统计数据信息
     */
    @GetMapping("/all_data")
    public PageResult allData(@RequestParam(required = false) Integer type,
                              @RequestParam(required = false) String startDate,
                              @RequestParam(required = false) String endDate,
                              @RequestParam int page, @RequestParam int limit) {
        // 创建startDateAndEndTime Map
        Map<String, String> startDateAndEndTime = new HashMap<>();

        // 添加start和end键值对（如果非空）
        if (!StringUtils.isEmpty(startDate)) {
            startDateAndEndTime.put("start", startDate+" 00:00:00");
        }
        if (!StringUtils.isEmpty(endDate)) {
            startDateAndEndTime.put("end", endDate+" 23:59:59");
        }

        // 设置分页参数，pageNum为当前页码，pageSize为每页显示数量
        PageHelper.startPage(page, limit);

        // 根据条件查询
        List<SysStatic> sysStaticList = sysStaticService.listByCondition(type,startDateAndEndTime);

        // 查询
        List<SysStaticVo> sysStaticVoList = sysStaticList.stream().map(SysStaticVo::new).collect(Collectors.toList());

        // 封装分页信息
        PageInfo<SysStaticVo> pageInfo = new PageInfo<>(sysStaticVoList);
        // 设置分页信息中的总记录数
        pageInfo.setTotal(((Page) sysStaticList).getTotal());

        return PageResult.success("查询成功", pageInfo);
    }

    /**
     * 统计用户数据：日、周、月、年、累计
     * @param model
     * @return
     */
    @GetMapping("/user")
    public ModelAndView userStatics(ModelAndView model){
        //根据日期区间统计数据并传给前台输出
        Map<String, String> todayStartOfEnd = DateUtils.getStartDateAndEndTimeForDays(0, "yyyy-MM-dd HH:mm:ss");
        DeBugUtils.print("todayStartOfEnd:{}"+todayStartOfEnd);
        //获取今日注册用户数据
        double todayRegisterCount = sysStaticService.getTypeValueByDateRange(1,todayStartOfEnd);
        //获取今日付款订单数
        double todayPayCount = sysStaticService.getTypeValueByDateRange(4,todayStartOfEnd);
        //获取今日活跃人数
        double todayActiveCount = sysStaticService.getTypeValueByDateRange(2,todayStartOfEnd);
        //计算今日用户购买率 根据 今日付款订单数/今日注册用户数 需要进行空置判断如果为空则默认0.00%
        double todayBuyRate = todayPayCount/todayRegisterCount*100;
        //根据今日 活跃人数/活跃人数+注册人数
        double todayActiveRate = todayActiveCount/(todayActiveCount+todayRegisterCount)*100;
        //存入视图
        model.addObject("todayRegisterCount", todayRegisterCount);
        model.addObject("todayPayCount", todayPayCount);
        model.addObject("todayActiveCount", todayActiveCount);
        model.addObject("todayBuyRate", todayBuyRate);
        model.addObject("todayActiveRate", todayActiveRate);

        //获取近7日的日期区间数据
        Map<String, String> thisWeekSDateRange = DateUtils.getStartDateToEndTimeForDays(-7, "yyyy-MM-dd HH:mm:ss");
        DeBugUtils.print("thisWeekSDateRange:{}"+thisWeekSDateRange);
        //获取7日注册用户数
        double sevenDayRegisterCount = sysStaticService.getTypeValueByDateRange(1,thisWeekSDateRange);
        //获取7日付款订单数
        double sevenDayPayCount = sysStaticService.getTypeValueByDateRange(4,thisWeekSDateRange);
        //获取7日活跃人数
        double sevenDayActiveCount = sysStaticService.getTypeValueByDateRange(2,thisWeekSDateRange);
        //计算7日用户购买率 根据 7日付款订单数/7日注册用户数 需要进行空置判断如果为空则默认0.00%
        double sevenDayBuyRate = sevenDayPayCount/sevenDayRegisterCount*100;
        //根据7日 活跃人数/活跃人数+注册人数
        double sevenDayActiveRate = sevenDayActiveCount/(sevenDayActiveCount+sevenDayRegisterCount)*100;
        model.addObject("sevenDayRegisterCount", sevenDayRegisterCount);
        model.addObject("sevenDayPayCount", sevenDayPayCount);
        model.addObject("sevenDayActiveCount", sevenDayActiveCount);
        model.addObject("sevenDayBuyRate", sevenDayBuyRate);
        model.addObject("sevenDayActiveRate", sevenDayActiveRate);

        //获取近30日的日期区间数据
        Map<String, String> thisMonthSDateRange = DateUtils.getStartDateToEndTimeForDays(-30, "yyyy-MM-dd HH:mm:ss");
        DeBugUtils.print("thisMonthSDateRange:{}"+thisMonthSDateRange);
        double thirtyDayRegisterCount = sysStaticService.getTypeValueByDateRange(1,thisMonthSDateRange);
        double thirtyDayPayCount = sysStaticService.getTypeValueByDateRange(4,thisMonthSDateRange);
        double thirtyDayActiveCount = sysStaticService.getTypeValueByDateRange(2,thisMonthSDateRange);
        double thirtyDayBuyRate = thirtyDayPayCount/thirtyDayRegisterCount*100;
        double thirtyDayActiveRate = thirtyDayActiveCount/(thirtyDayActiveCount+thirtyDayRegisterCount)*100;
        model.addObject("thirtyDayRegisterCount", thirtyDayRegisterCount);
        model.addObject("thirtyDayPayCount", thirtyDayPayCount);
        model.addObject("thirtyDayActiveCount", thirtyDayActiveCount);
        model.addObject("thirtyDayBuyRate", thirtyDayBuyRate);
        model.addObject("thirtyDayActiveRate", thirtyDayActiveRate);

        //获取单个类型的历史累计数据
        double totalRegisterCount = sysStaticService.sumTypeValue(1);
        double totalPayCount = sysStaticService.sumTypeValue(4);
        double totalActiveCount = sysStaticService.sumTypeValue(2);
        double totalBuyRate = totalPayCount/totalRegisterCount*100;
        double totalActiveRate = totalActiveCount/(totalActiveCount+totalRegisterCount)*100;
        model.addObject("totalRegisterCount", totalRegisterCount);
        model.addObject("totalPayCount", totalPayCount);
        model.addObject("totalActiveCount", totalActiveCount);
        model.addObject("totalBuyRate", totalBuyRate);
        model.addObject("totalActiveRate", totalActiveRate);


        model.setViewName(MODULE_PATH+"user");
        return model;
    }

    /**
     * 统计订单数据：日、周、月、年、累计
     * @param model
     * @return
     */
    @GetMapping("/order")
    public ModelAndView orderStatics(ModelAndView model){

        //根据日期区间统计数据并传给前台输出
        Map<String, String> todayStartOfEnd = DateUtils.getStartDateAndEndTimeForDays(0, "yyyy-MM-dd HH:mm:ss");
        DeBugUtils.print("todayStartOfEnd:{}"+todayStartOfEnd);
        //获取今日订单总数
        double todayOrderCount = sysStaticService.getTypeValueByDateRange(3,todayStartOfEnd);
        //获取今日付款订单数
        double todayPayCount = sysStaticService.getTypeValueByDateRange(4,todayStartOfEnd);
        //获取今日付款收益
        double todayPayMoney = sysStaticService.getTypeValueByDateRange(6,todayStartOfEnd);
        //计算今日订单支付率
        double todayPayRate = todayPayCount == 0 || todayOrderCount == 0 ? 0 : todayPayCount / todayOrderCount * 100;
        //本日api调用数
        double todayApiCalls = sysStaticService.getTypeValueByDateRange(5,todayStartOfEnd);
        model.addObject("todayOrderCount", todayOrderCount);
        model.addObject("todayPayCount", todayPayCount);
        model.addObject("todayPayMoney", todayPayMoney);
        model.addObject("todayPayRate", todayPayRate);
        model.addObject("todayApiCalls", todayApiCalls);

        //7日数据统计
        Map<String, String> thisWeekStartOfEnd = DateUtils.getStartDateToEndTimeForDays(-7, "yyyy-MM-dd HH:mm:ss");
        DeBugUtils.print("thisWeekStartOfEnd:{}"+thisWeekStartOfEnd);
        double sevenDayOrderCount = sysStaticService.getTypeValueByDateRange(3,thisWeekStartOfEnd);
        double sevenDayPayCount = sysStaticService.getTypeValueByDateRange(4,thisWeekStartOfEnd);
        double sevenDayPayMoney = sysStaticService.getTypeValueByDateRange(6,thisWeekStartOfEnd);
        double sevenDayPayRate = sevenDayPayCount == 0 || sevenDayOrderCount == 0 ? 0 : sevenDayPayCount / sevenDayOrderCount * 100;
        double sevenDayApiCalls = sysStaticService.getTypeValueByDateRange(5,thisWeekStartOfEnd);
        model.addObject("sevenDayOrderCount", sevenDayOrderCount);
        model.addObject("sevenDayPayCount", sevenDayPayCount);
        model.addObject("sevenDayPayMoney", sevenDayPayMoney);
        model.addObject("sevenDayPayRate", sevenDayPayRate);
        model.addObject("sevenDayApiCalls", sevenDayApiCalls);

        //30日数据统计
        Map<String, String> thisMonthStartOfEnd = DateUtils.getStartDateToEndTimeForDays(-30, "yyyy-MM-dd HH:mm:ss");
        DeBugUtils.print("thisMonthStartOfEnd:{}"+thisMonthStartOfEnd);
        double thirtyDayOrderCount = sysStaticService.getTypeValueByDateRange(3,thisMonthStartOfEnd);
        double thirtyDayPayCount = sysStaticService.getTypeValueByDateRange(4,thisMonthStartOfEnd);
        double thirtyDayPayMoney = sysStaticService.getTypeValueByDateRange(6,thisMonthStartOfEnd);
        double thirtyDayPayRate = thirtyDayPayCount == 0 || thirtyDayOrderCount == 0 ? 0 : thirtyDayPayCount / thirtyDayOrderCount * 100;
        double thirtyDayApiCalls = sysStaticService.getTypeValueByDateRange(5,thisMonthStartOfEnd);
        model.addObject("thirtyDayOrderCount", thirtyDayOrderCount);
        model.addObject("thirtyDayPayCount", thirtyDayPayCount);
        model.addObject("thirtyDayPayMoney", thirtyDayPayMoney);
        model.addObject("thirtyDayPayRate", thirtyDayPayRate);
        model.addObject("thirtyDayApiCalls", thirtyDayApiCalls);

        //获取单个类型的历史累计数据
        double totalOrderCount = sysStaticService.sumTypeValue(3);
        double totalPayCount = sysStaticService.sumTypeValue(4);
        double totalPayMoney = sysStaticService.sumTypeValue(6);
        double totalPayRate = totalPayCount == 0 || totalOrderCount == 0 ? 0 : totalPayCount / totalOrderCount * 100;
        double totalApiCalls = sysStaticService.sumTypeValue(5);
        model.addObject("totalOrderCount", totalOrderCount);
        model.addObject("totalPayCount", totalPayCount);
        model.addObject("totalPayMoney", totalPayMoney);
        model.addObject("totalPayRate", totalPayRate);
        model.addObject("totalApiCalls", totalApiCalls);

        model.setViewName(MODULE_PATH+"order");
        return model;
    }

}
