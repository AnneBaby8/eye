package cn.com.citydo.module.screen.controller;

import cn.com.citydo.common.BaseException;
import cn.com.citydo.common.EyeStatus;
import cn.com.citydo.common.Status;
import cn.com.citydo.common.enums.PlatformEnum;
import cn.com.citydo.module.core.entity.Device;
import cn.com.citydo.module.core.service.DeviceService;
import cn.com.citydo.module.screen.vo.CapturePicVO;
import cn.com.citydo.module.screen.vo.SnapshotInfoVO;
import cn.com.citydo.common.ApiResponse;
import cn.com.citydo.module.screen.dto.CapturelDTO;
import cn.com.citydo.module.screen.service.HuaweiService;
import cn.com.citydo.thirdparty.huawei.service.FfmpegService;
import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author blackcat
 * @create 2021/5/12 10:30
 * @version: 1.0
 * @description:获取实时的视频和图片的接口(华为接口的包装)
 */
@Api(tags = "华为接口的包装(获取实时的视频和图片的接口等)")
@Slf4j
@RestController
@RequestMapping("/api/media")
public class MediaController {

    @Value("${fileserver.url}${fileserver.faceImagesPath}")
    private String faceImagesPath;

    @Value("${fileserver.appUrl}${fileserver.faceImagesPath}")
    private String appFaceImagesPath;

    @Value("${fileserver.faceImagesPath}")
    private String imagesPath;

    @Autowired
    private HuaweiService huaweiService;
    @Autowired
    private FfmpegService ffmpegService;
    @Autowired
    private DeviceService deviceService;

    @ApiOperation(value = "根据设备编号获取视频流地址")
    @GetMapping("/getVideo")
    public ApiResponse<String> getVideo(@RequestParam("cameraCode") String cameraCode) {
        boolean isGov = isGovUrl(cameraCode);
        String cookie;
        if (isGov) {
            cookie = huaweiService.govExtLogin();
        } else {
            cookie = huaweiService.login();
        }
        return ApiResponse.ofSuccess(huaweiService.getRtspUrl(cameraCode, cookie, isGov));
    }

    private boolean isGovUrl(@RequestParam("cameraCode") String cameraCode) {
        List<Device> list = deviceService.getByStreamId(cameraCode);
        if (CollectionUtils.isEmpty(list)) {
            throw new BaseException(Status.ERROR, "错误的cameraCode");
        }
        return PlatformEnum.GOV.getMessage().equals(list.get(0).getPlatform());
    }

    @ApiOperation(value = "根据设备编号获取视频截图")
    @GetMapping("/getVideoPic")
    public ApiResponse<CapturePicVO> getVideoPic(@RequestParam("cameraCode") String cameraCode) {
        log.info("调取华为云设备[{}]视频流，并截图", cameraCode);
        CapturePicVO result = new CapturePicVO();
        boolean isGov = isGovUrl(cameraCode);
        try {

            String cookie;
            if (isGov) {
                cookie = huaweiService.govExtLogin();
            } else {
                cookie = huaweiService.login();
            }
            String rtspUrl = huaweiService.getRtspUrl(cameraCode, cookie, isGov);
            log.info("华为云返回的视频流地址（替换前）：" + rtspUrl);
            if (isGov) {
                log.info("====政务外网====");
                rtspUrl = rtspUrl.replaceAll("111.33.175.77", "10.165.23.226");
                rtspUrl = rtspUrl.replaceAll("192.168.255.2", "10.165.23.226");
            } else {
                rtspUrl = rtspUrl.replaceAll("111.33.175.77", "172.30.1.229");
                rtspUrl = rtspUrl.replaceAll("192.168.255.2", "172.30.1.229");
            }

            log.info("华为云返回的视频流地址（替换后）：" + rtspUrl);
            String uuid = UUID.randomUUID().toString();
            log.info("uuid======  "+uuid);
            // 构造shell命令
            StringBuilder sb = new StringBuilder();
            sb.append("ffmpeg -rtsp_transport tcp -y -i \"");
            sb.append(rtspUrl);
            sb.append("\" -ss 00:00:01 -vframes 1 -f image2 -vcodec png ");
            String filePath = "/home/citydo/faceImages/" + uuid + ".png";
            sb.append(filePath);
            sb.append(";echo end");
            String cmd = sb.toString();
            log.info("执行视频流截图，执行shell命令：" + cmd);
            // 执行shell命令
            String shellResult = ffmpegService.execShellScript("localhost", "citydo", "EyeEye@123456", cmd, 22);
            log.info("执行shell命令后的结果:{}", shellResult);
            File file = new File(filePath);
            if (!file.exists()) {
                log.error("视频流转换失败，文件路径为：[{}]",filePath);
                throw  new BaseException(EyeStatus.VIDEO_CONVER_ERROR);
            }
            result.setCapturePicUrl(faceImagesPath + "/" + uuid + ".png");
            result.setAppCapturePicUrl(appFaceImagesPath + "/" + uuid + ".png");
            result.setImage(imagesPath + "/" + uuid + ".png");
            result.setCaptureTime(DateUtil.formatDateTime(new Date()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BaseException(Status.ERROR.getCode(), e.getMessage());
        }
        return ApiResponse.ofSuccess(result);
    }


    @GetMapping("/getPictures")
    public ApiResponse<List<SnapshotInfoVO>> getPictures(@RequestBody CapturelDTO capturelDTO) {
        boolean isGov = isGovUrl(capturelDTO.getCameracode());
        String cookie;
        if (isGov) {
            cookie = huaweiService.govExtLogin();
        } else {
            cookie = huaweiService.login();
        }
        return ApiResponse.ofSuccess(huaweiService.getCapturel(cookie, capturelDTO, isGov));
    }
}
