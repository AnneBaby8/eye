package cn.com.citydo.module.screen.controller;

import cn.com.citydo.common.ApiResponse;
import cn.com.citydo.module.screen.dto.NoticeDTO;
import cn.com.citydo.module.screen.enums.WarnTypeEnum;
import cn.com.citydo.module.screen.service.EventWarningService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author blackcat
 * @create 2021/5/12 14:37
 * @version: 1.0
 * @description: 华为视频分析服务的webhook，有消息会通知过来
 */
@Api(tags = "华为视频监测告警事件推送服务-通道占用（占道经营、出店经营）")
@Slf4j
@RestController
@RequestMapping("/v1/huawei/webhook")
public class HuaweiWebhook {

    @Autowired
    private EventWarningService eventWarningService;

    /**
     * 接收华为视频监测到的占道经营、出店经营事件信息
     * @param notice 告警事件内容
     * @return
     */
    @PostMapping
    public ApiResponse<String> webhook(@RequestBody NoticeDTO notice) {
        log.info("收到华为云的告警信息-通道占用>{}", notice);
        eventWarningService.saveNotice(notice, WarnTypeEnum.CHANNEL);
        log.info("收到华为云的告警信息-通道占用>>>成功入库");
        return ApiResponse.ofSuccess();
    }

}
