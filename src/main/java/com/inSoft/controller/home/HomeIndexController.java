package com.inSoft.controller.home;

import com.inSoft.constant.HomeConstant;
import com.inSoft.controller.BaseController;
import com.inSoft.pojo.*;
import com.inSoft.pojo.result.Result;
import com.inSoft.pojo.vo.AppVersionVo;
import com.inSoft.pojo.vo.IncomeVo;
import com.inSoft.pojo.vo.WebPostVo;
import com.inSoft.service.*;
import com.inSoft.utils.CaptchaUtil;
import com.inSoft.utils.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(HomeConstant.PATH_PREFIX)
public class HomeIndexController extends BaseController {

    private static String MODULE_PATH = HomeConstant.MODULE_PATH;

    @Autowired
    private WebNavService webNavService;

    @Autowired
    private WebPushProductService webPushProductService;

    @Autowired
    private SysStaticService sysStaticService;

    @Autowired
    private WebCharacteristicService webCharacteristicService;

    @Autowired
    private CaptchaUtil captchaUtil;

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private AppCatService appCatService;

    @Autowired
    private AppVersionService appVersionService;

    @Autowired
    private WebPostCatService webPostCatService;

    @Autowired
    private WebPostService webPostService;

    @Autowired
    private IPUtil ipUtil;

    @Autowired
    private OpinionService opinionService;

    //首页
    @GetMapping("/")
    public ModelAndView index(ModelAndView model, HttpServletRequest request){
        //记录访问信息
        analysis(0,1);
        //获取安装量及使用次数
        model.addObject("totalPlayCount","...");
        model.addObject("totalInstallCount","...");
        //获取特性数据
        List<WebCharacteristic> webCharacteristics = webCharacteristicService.pushList();
        model.addObject("webCharacteristics",webCharacteristics);
        //获取推荐应用数据
        List<WebPushProduct> webPushProducts = webPushProductService.pushList();//最多9个
        model.addObject("webPushProducts",webPushProducts);
        model.setViewName(MODULE_PATH+"index");
        return model;
    }

    //实时数据
    @GetMapping("/get_static")
    public Map<String,Object> getStatic(){
        Map<String,Object> map = new HashMap<>();
        map.put("totalPlayCount",sysStaticService.sumTypeValue(7));
        map.put("totalInstallCount",sysStaticService.sumTypeValue(8));
        return map;
    }

    //定价
    @GetMapping("/price")
    public ModelAndView price(ModelAndView model){
        //记录访问信息
        analysis(0,1);
        //查询增值服务列表数据输出
        List<Income> incomeList = incomeService.getPricesToView(6);
        List<IncomeVo> incomeVoList = incomeList.stream().map(IncomeVo::new).collect(Collectors.toList());
        model.addObject("incomeList",incomeVoList);
        // 加入一个属性，用来在模板中读取
        model.setViewName(MODULE_PATH+"price");
        return model;
    }

    //版本
    @GetMapping("/edition")
    public ModelAndView edition(ModelAndView model){
        //记录访问信息
        analysis(0,1);
        // 根据客户端类型分别查询 客户端下的最近10条版本信息内容 appCat 和 appVersion
        List<AppCat> appCatList = appCatService.getSelect();
        // 声明页面数据
        List<Map<String, Object>> verCardList = new ArrayList<>();
        // 获取最新版本信息
        if (appCatList != null && appCatList.size()>0){
            for (AppCat appCat : appCatList) {
                // 获取appCatId
                Integer appCatId = appCat.getId();
                // 获取appCatName
                String appCatName = appCat.getName();
                // 查询appCatId下的最新版本信息
                List<AppVersion> versionList = appVersionService.getListByCid(appCatId,10);
                List<AppVersionVo> appVersionVoList = versionList.stream().map(AppVersionVo::new).collect(Collectors.toList());
                // 封装数据
                Map<String, Object> verCard = new HashMap<>();
                verCard.put("appCatId", appCatId);
                verCard.put("appCatName", appCatName);
                verCard.put("versionList", appVersionVoList);
                // 添加到集合中
                verCardList.add(verCard);
            }
        }
        model.addObject("verCardList", verCardList);
        model.setViewName(MODULE_PATH+"edition");
        return model;
    }

    //帮助
    @GetMapping("/help")
    public ModelAndView help(ModelAndView model){
        //记录访问信息
        analysis(0,1);
        //查询文章分类，并循环分类输出文章数据，每个分类显示10条内容
        List<WebPostCat> webPostCatList = webPostCatService.getSelect();
        //判断分类数据是否存在如果存在则进行查询文章信息
        List<Map<String, Object>> helpCardList = new ArrayList<>();
        if (webPostCatList != null && webPostCatList.size() > 0) {
            for (WebPostCat webPostCat : webPostCatList) {
                // 获取webPostCatId
                Integer webPostCatId = webPostCat.getId();
                // 获取webPostCatName
                String webPostCatName = webPostCat.getName();
                // 查询webPostCatId下的最新版本信息
                List<WebPost> webPostList = webPostService.getListByCid(webPostCatId, 10);
                List<WebPostVo> webPostVoList = webPostList.stream().map(WebPostVo::new).collect(Collectors.toList());
                // 封装数据
                Map<String, Object> helpCard = new HashMap<>();
                helpCard.put("webPostCatId", webPostCatId);
                helpCard.put("webPostCatName", webPostCatName);
                helpCard.put("webPostList", webPostVoList);
                // 添加到集合中
                helpCardList.add(helpCard);
            }
        }
        model.addObject("helpCardList", helpCardList);
        // 加入一个属性，用来在模板中读取
        model.setViewName(MODULE_PATH+"help");
        return model;
    }

    @GetMapping("/search")
    public ModelAndView about(ModelAndView model, @RequestParam(  name =  "keyword", required = false) String keyword){
        analysis(0,1);
        List<WebPost> webPostList = new ArrayList<>();
        //关键词如果为空则不检索
        if (keyword != null && !keyword.isEmpty()) {
           webPostList = webPostService.listBykeyword(keyword,100);
        }
        model.addObject("title", "检索");
        model.addObject("keywords", "搜索,检索");
        model.addObject("description", "检索信息");
        model.addObject("webPostList", webPostList);
        //传递关键词
        model.addObject("keyword", keyword);
        //检索到的条数
        model.addObject("total", webPostList.size());
        model.setViewName(MODULE_PATH+"search");
        return model;
    }

    //文章页
    @GetMapping("/article")
    public ModelAndView article(ModelAndView model, Integer postId) {
        analysis(0, 1);
        WebPost webPost = webPostService.getById(postId);
        if (webPost != null) {
            model.addObject("webPost", webPost);
            model.addObject("title", webPost.getTitle());
            model.addObject("keywords", webPost.getKeywords());
            model.addObject("description", webPost.getDescription());
        }
        model.setViewName(MODULE_PATH + "article");
        return model;
    }

    //单页
    @GetMapping("/node")
    public ModelAndView node(ModelAndView model, Integer id) {
        analysis(0, 1);
        WebNav webNav = webNavService.getById(id);
        if (webNav != null) {
            model.addObject("webNav", webNav);
            model.addObject("title", webNav.getName());
            model.addObject("keywords", webNav.getKeywords());
            model.addObject("description", webNav.getDescription());
        }

        model.setViewName(MODULE_PATH + "node");
        return model;
    }

    //验证码接口直接输出图片，可以根据time来进行变化
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        captchaUtil.generateAndSendCaptcha(request, response);
    }

    //意见反馈界面
    @GetMapping("/feedback")
    public ModelAndView feedback(ModelAndView model) {
        analysis(0, 1);
        model.setViewName(MODULE_PATH + "feedback");
        return model;
    }

    //保存意见
    @PostMapping("/saveFeed")
    @ResponseBody
    public Result saveFeed(@ModelAttribute Opinion opinion,@RequestParam(name = "code") String code) {

        //验证验证码
        if (!verificationCode(code)) {
            return Result.error("验证码错误");
        }

        //标题为空报错
        String title = opinion.getTitle();
        if (title == null || title.isEmpty()) {
            return Result.error("标题不能为空");
        }

        //内容描述为空报错
        String content = opinion.getContent();
        if (content == null || content.isEmpty()) {
            return Result.error("内容描述不能为空");
        }

        //url路径为空报错
        String url = opinion.getUrl();
        if (url == null || url.isEmpty()) {
            return Result.error("url路径不能为空");
        }

        //检查是否有同种类型的数据更具title和url进行查询 如果有则提示已经提交 如果没有则添加成功 Opinion实体类
        Opinion m = new Opinion();
        m.setTitle(title);
        m.setContent(content);
        m.setUrl(url);
        m.setIp(ipUtil.getIpAddress());
        m.setCreatedAt(new Date());
        m.setIsDeleted(0);

        //提交保存
        if (opinionService.save(m)) {
            return Result.success("提交成功");
        }

        return Result.error("异常错误");

    }


}
