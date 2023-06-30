package cn.com.citydo.module.basic.controller;

import cn.com.citydo.module.basic.vo.CaptchaResponse;
import cn.com.citydo.common.ApiResponse;

import cn.com.citydo.common.BaseException;
import cn.com.citydo.common.Status;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static cn.com.citydo.common.Consts.REDIS_CODE_KEY_PREFIX;


/**
 * @Author blackcat
 * @create 2020/11/23 16:17
 * @version: 1.0
 * @description:
 */
@Api(tags = "获取验证码")
@Slf4j
@RestController
@RequestMapping("/api/kaptcha")
public class KaptchaController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @PostMapping(value = "/default")
    public ApiResponse<CaptchaResponse> defaultKaptcha(HttpServletResponse response)
            throws Exception {
        CaptchaResponse captchaResponse = new CaptchaResponse();
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            String uuid = UUID.randomUUID().toString().replace("-", "");;
            captchaResponse.setUuid(uuid);
            // 生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            stringRedisTemplate.opsForValue()
                    .set(REDIS_CODE_KEY_PREFIX + uuid, createText.toLowerCase(), 60000, TimeUnit.MILLISECONDS);
            // 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
           // response.sendError(HttpServletResponse.SC_NOT_FOUND);
            throw new BaseException(Status.ACCESS_CODE_ERROR);
        }
        BASE64Encoder encoder = new BASE64Encoder();
        // 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        String base64 = encoder.encode(captchaChallengeAsJpeg);
        String captchaBase64 = "data:image/jpeg;base64," + base64.replaceAll("\r\n", "");
        captchaResponse.setCaptcha(captchaBase64);
        return  ApiResponse.ofSuccess(captchaResponse);

    }

}
