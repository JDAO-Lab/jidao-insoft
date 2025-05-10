package com.inSoft.controller.common;

import com.inSoft.controller.BaseController;
import com.inSoft.enums.DownTypeEnum;
import com.inSoft.enums.StaticTypeEnum;
import com.inSoft.pojo.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 枚举select框数据获取控制器，公共的，不限制。
 */
@Slf4j
@RestController
@RequestMapping("/enum")
public class CommonEnumController extends BaseController {

    //获取统计类型
    @GetMapping("/get_static_type")
    @ResponseBody
    public Result getStaticTypeEnum() {
        List<Map<String, Object>> enumDataList = Arrays.stream(StaticTypeEnum.values())
                .map(enumItem -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("code", enumItem.getCode());
                    data.put("value", enumItem.getValue());
                    return data;
                })
                .collect(Collectors.toList());
        return Result.success("获取枚举信息成功~",enumDataList);
    }

    //获取下载类型
    @GetMapping("/get_down_type")
    @ResponseBody
    public Result getDownTypeEnum() {
        List<Map<String, Object>> enumDataList = Arrays.stream(DownTypeEnum.values())
                .map(enumItem -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("code", enumItem.getCode());
                    data.put("value", enumItem.getValue());
                    return data;
                })
                .collect(Collectors.toList());
        return Result.success("获取枚举信息成功~",enumDataList);
    }
}
