package com.inSoft.controller.home;


import com.inSoft.constant.HomeConstant;
import com.inSoft.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(HomeConstant.PATH_PREFIX)
public class HomeSmsController extends BaseController {

    private static String MODULE_PATH = HomeConstant.MODULE_PATH;

    //验证验证码并发送短信
    @GetMapping("/send_sms_code")
    public ModelAndView send_sms_code(ModelAndView model,
                                      @RequestParam(name = "phone", required = false) String phone,
                                      @RequestParam(name = "stype", required = false) Integer stype){
        model.addObject("title","发送短信验证码");
        model.addObject("stype",stype);
        model.addObject("phone",phone);
        model.setViewName(MODULE_PATH+"send_sms_code");
        return model;
    }

}
