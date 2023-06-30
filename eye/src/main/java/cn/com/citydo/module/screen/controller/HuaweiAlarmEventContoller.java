package cn.com.citydo.module.screen.controller;

import cn.com.citydo.common.ApiResponse;
import cn.com.citydo.module.screen.dto.alarmevent.AlarmEventContentDTO;
import cn.com.citydo.module.screen.enums.EventAlarmTypeEnum;
import cn.com.citydo.module.screen.service.EventWarningService;
import cn.com.citydo.thirdparty.huawei.service.HuaweiApiService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 华为事件推送服务，北向外部接口
 *
 * @Author shaodl
 * @create 2021/6/6 14:41
 * @version: 1.0
 * @description:
 */
@Api(tags = "华为推送-告警信息")
@Slf4j
@RestController
@RequestMapping("/v1/huawei/alarmEvent")
public class HuaweiAlarmEventContoller {

    @Autowired
    private EventWarningService eventWarningService;
    @Autowired
    private HuaweiApiService huaweiApiService;

    /**
     * 接收华为平台推送的告警信息（重点人员监测等）
     *
     * @param eventType 事件类型
     * @param content   事件预警内容
     * @return
     */
    @PostMapping("/receive/{eventType}")
    public ApiResponse<String> receiveAlarmEvent(@PathVariable("eventType") String eventType, @RequestBody AlarmEventContentDTO content) {
        // 判断华为平台推送告警事件类型
        if (eventType.equals(EventAlarmTypeEnum.FACE_DATA.getKey())) {
            log.info("收到华为云的告警信息->人脸数据{}", content);
            // 保存华为北向外部对接推送过来的事件信息
            eventWarningService.saveHuaweiNorthOrientationEvent(content);
        }
        return ApiResponse.ofSuccess();
    }


    /**
     * 订阅华为监控数据，并获取连接
     *
     * @return
     */
    @PostMapping("/subscribeEvent")
    public ApiResponse<String> subscribeEvent() {
        //登陆
        String cookie = huaweiApiService.login();
        //8分钟登陆保活 抛出异常不执行
//        Timer timer = new Timer(true);
//        timer.schedule(new KeepLogin(cookie, huaweiApiService), 8 * 60 * 1000, 8 * 60 * 1000);

        //调用布控任务,数据订阅和获取长链接
        List<String> subscribeList = huaweiApiService.subscribe(cookie);
        //长链接地址保持链接的http服务
        //30s 长链接保持  失效重新调用接口 更新链接地址
   //      timer.schedule(new KeepConnection(cookie, huaweiApiService), 0, 30 * 1000);
        return ApiResponse.ofSuccess();
    }


    @PostMapping("/keep")
    public ApiResponse<String> keep() {
        //登陆
        String cookie = huaweiApiService.login();
        //8分钟登陆保活 抛出异常不执行
        Timer timer = new Timer(true);

        timer.schedule(new KeepLogin(cookie, huaweiApiService), 0, 60 * 1000);

        return ApiResponse.ofSuccess();
    }

    @PostMapping("/task")
    public ApiResponse<String> task() {
        List<String> list = huaweiApiService.suspectTask(huaweiApiService.login());
        String suspectString = StringUtils.join(list, ",");
        return ApiResponse.ofSuccess(suspectString);
    }

    @PostMapping("/subscribe")
    public ApiResponse<String> subscribe() {
        String cookies = huaweiApiService.login();
        List<String> subscribeList = huaweiApiService.subscribe(cookies);
        return ApiResponse.ofSuccess(subscribeList);
    }

    class KeepLogin extends TimerTask {

        private String cookie;

        private HuaweiApiService huaweiApiService;

        public KeepLogin(String cookie, HuaweiApiService huaweiApiService) {
            this.cookie = cookie;
            this.huaweiApiService = huaweiApiService;
        }


        @Override
        public void run() {
            log.info("start KeepLogin");
            try {
                huaweiApiService.keepLogin(cookie);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
            log.info("end KeepLogin");
        }
    }

    class KeepConnection extends TimerTask {

        private String cookie;

        private HuaweiApiService huaweiApiService;

        public KeepConnection(String cookie, HuaweiApiService huaweiApiService) {
            this.cookie = cookie;
            this.huaweiApiService = huaweiApiService;
        }

        //TODO
        @Override
        public void run() {
            log.info("start KeepConnection");
            try {

            } catch (Exception e) {

            }
            log.info("end KeepConnection");
        }
    }


}
