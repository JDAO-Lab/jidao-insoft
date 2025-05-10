package com.inSoft.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

@Configuration
@Component
public class KaptchaConfig {


    //边框设置
    @Value("${kaptcha.border}")
    private String border;
    //kaptcha.textproducer.font.color  设置验证码文本字符颜色为黑色
    @Value("${kaptcha.textproducer.font.color}")
    private String fontColor;
    // 设置验证码每个字符之间的间距 kaptcha.textproducer.char.space 4
    @Value("${kaptcha.textproducer.char.space}")
    private String charSpace;
    // 设置验证码图片的宽度kaptcha.image.width 100
    @Value("${kaptcha.image.width}")
    private String imageWidth;
    // 设置验证码图片的高度kaptcha.image.height 40
    @Value("${kaptcha.image.height}")
    private String imageHeight;
    // 设置验证码文本字符的长度kaptcha.textproducer.char.length 5
    @Value("${kaptcha.textproducer.char.length}")
    private String charLength;
    // 设置验证码文本使用的字体名称列表kaptcha.textproducer.font.names "Arial,Courier,monospace,sans-serif"
    @Value("${kaptcha.textproducer.font.names}")
    private String fontNames;

    public String getBorder() {
        return border;
    }

    public String getFontColor() {
        return fontColor;
    }

    public String getCharSpace() {
        return charSpace;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public String getCharLength() {
        return charLength;
    }

    public String getFontNames() {
        return fontNames;
    }

    public Map<?,?> getConfig() {
        return Map.of(
                "kaptcha.border", border,
                "kaptcha.textproducer.font.color", fontColor,
                "kaptcha.textproducer.char.space", charSpace,
                "kaptcha.image.width", imageWidth,
                "kaptcha.image.height", imageHeight,
                "kaptcha.textproducer.char.length", charLength,
                "kaptcha.textproducer.font.names", fontNames
        );
    }
}
