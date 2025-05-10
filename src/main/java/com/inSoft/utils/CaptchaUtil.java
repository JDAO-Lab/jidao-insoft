package com.inSoft.utils;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.inSoft.config.KaptchaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;

@Component
public class CaptchaUtil {

    private final Producer kaptchaProducer;
    private KaptchaConfig kaptchaConfig;

    @Autowired
    public CaptchaUtil(KaptchaConfig kaptchaConfig) {
        this.kaptchaConfig = kaptchaConfig;
        this.kaptchaProducer = createKaptchaProducer();
    }

    // 实例化验证码生成器
    private Producer createKaptchaProducer() {
        // 创建Properties对象并将KaptchaConfig中的配置放入其中
        Properties properties = new Properties();
        properties.putAll(kaptchaConfig.getConfig());
        // 使用Properties对象创建Config实例
        Config config = new Config(properties);
        // 使用无参构造函数创建DefaultKaptcha实例
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        // 设置Config到DefaultKaptcha实例
        kaptcha.setConfig(config);
        return kaptcha;
    }



    /**
     * 生成验证码图片，并将验证码文本存储到Session。
     * 注意：这里不再生成和使用captchaId，直接利用Session的唯一性。
     *
     * @param request  HTTP 请求
     * @param response HTTP 响应
     * @throws IOException 如果发生I/O错误
     */
    public void generateAndSendCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true); // 获取或创建Session
        String captchaText = kaptchaProducer.createText();

        // 存储验证码文本到Session
        session.setAttribute("captchaText", captchaText);

        BufferedImage image = kaptchaProducer.createImage(captchaText);
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setDateHeader("Expires", 0);

        // 将验证码图片写入响应输出流
        javax.imageio.ImageIO.write(image, "png", response.getOutputStream());
    }

    /**
     * 验证用户提交的验证码是否正确。
     *
     * @param request HTTP 请求，包含Session
     * @param input 用户提交的验证码文本
     * @return 验证结果，true为通过，false为失败
     */
    public boolean validateCaptcha(HttpServletRequest request, String input) {
        HttpSession session = request.getSession(false); // 尝试获取Session，如果不存在则返回null
        if (session == null) {
            return false;
        }

        String storedCaptcha = (String) session.getAttribute("captchaText");
        if (storedCaptcha == null || !storedCaptcha.equalsIgnoreCase(input)) {
            session.removeAttribute("captchaText"); // 验证失败后清理Session中的验证码
            return false;
        }

        session.removeAttribute("captchaText"); // 可选：验证成功后清理Session中的验证码
        return true;
    }
}
