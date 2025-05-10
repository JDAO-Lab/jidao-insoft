package com.inSoft.controller.api;

import com.inSoft.constant.ApiConstant;
import com.inSoft.controller.BaseController;
import com.inSoft.pojo.result.Result;
import com.inSoft.utils.ApiUtils;
import com.inSoft.utils.DeBugUtils;
import com.inSoft.utils.SysInfoUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstant.PATH_PREFIX)
public class ApiClientController extends BaseController {

    //验证access_token 测试接口
    @GetMapping("/check_access_token")
    public Result check_access_token() {
        if (checkToken() && DeBugUtils.isEnable()){
            return Result.success("存在access_token,验证正确！");
        }
        return Result.error("错误：不存在access_token或验证错误！");
    }

    //获取access_token 仅限测试 正式环境时需删除
    @GetMapping("/get_access_token")
    public Result get_access_token() {
        if (DeBugUtils.isEnable()){
            return Result.success("获取测试用令牌",ApiUtils.createAccessToken());
        }
        return Result.error("错误：非测试环境！");
    }

}
