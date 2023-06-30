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
 * @create 2021/5/12 14:41
 * @version: 1.0
 * @description: 华为视频分析服务的webhook，
 * 边缘占道经营，有时候称作垃圾分类，识别垃圾
 */
@Api(tags = "华为推送-识别垃圾")
@Slf4j
@RestController
@RequestMapping("/v1/huawei/webhook/carriy")
public class HuaweiWebhookCarriy {


    @Autowired
    private EventWarningService eventWarningService;

    @PostMapping
    public ApiResponse<String> webhook(@RequestBody NoticeDTO notice) {
        log.info("收到华为云的告警信息-暴露垃圾->{}", notice);
        eventWarningService.saveNotice(notice,WarnTypeEnum.GARBAGE);
        log.info("收到华为云的告警信息-暴露垃圾->>>成功入库");
        return ApiResponse.ofSuccess();
    }
}
