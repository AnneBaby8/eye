package cn.com.citydo.module.screen.service;

import cn.com.citydo.module.screen.vo.SnapshotInfoVO;
import cn.com.citydo.module.screen.dto.CapturelDTO;

import java.util.List;

/**
 * @Author blackcat
 * @create 2021/5/12 9:20
 * @version: 1.0
 * @description: 对接华为的相关接口
 */
public interface HuaweiService {
    /**
     * 华为登陆的接口
     *
     * @return
     */
    String login();



    /**
     * 华为政务外网登陆的接口
     *
     * @return
     */
    String govExtLogin();

    /**
     * 获取视频的接口（返回时rtsp格式的）
     *
     * @param cameraCode
     * @param cookie
     * @return
     */
    String getRtspUrl(String cameraCode, String cookie,boolean isGov);

    /**
     * 平台抓拍接口
     *
     * @param cameraCode
     * @param cookie
     * @param domainCode
     */
    void capturel(String cameraCode, String cookie, String domainCode,boolean isGov);


    List<SnapshotInfoVO> getCapturel(String cookie, CapturelDTO capturelDTO,boolean isGov);
}
