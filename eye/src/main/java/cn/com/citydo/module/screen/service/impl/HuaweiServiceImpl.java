package cn.com.citydo.module.screen.service.impl;

import cn.com.citydo.module.screen.vo.SnapshotInfoVO;
import cn.com.citydo.common.BaseException;
import cn.com.citydo.common.EyeStatus;
import cn.com.citydo.common.Status;
import cn.com.citydo.module.screen.dto.CapturelDTO;
import cn.com.citydo.module.screen.service.HuaweiService;
import cn.hutool.json.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author blackcat
 * @create 2021/5/12 9:21
 * @version: 1.0
 * @description:
 */
@Service
@Slf4j
public class HuaweiServiceImpl implements HuaweiService {


    int SUCCESS = 200;

    int ZERO = 0;

    private final String RESULTCODE = "resultCode";

    @Value("${huawei.url}")
    private String url;

    @Value("${huawei.govExtUrl}")
    private String govExtUrl;

    private final String contentType = "application/json";

    private String loginUrl = "/loginInfo/login/v1.0";

    private String rstpUrl = "/video/rtspurl/v1.0";

    @Override
    public String login() {
        HttpRequest request = HttpRequest.post(url + loginUrl).contentType(contentType)
                .connectTimeout(3000).readTimeout(5000);
        request.trustAllCerts();
        request.trustAllHosts();
        JSONObject jsonObject = new JSONObject();
        //jsonObject.putIfAbsent("userName", "admin");
        //jsonObject.putIfAbsent("password", "Huawei12!@");

//        jsonObject.putIfAbsent("userName", "test02");
//        jsonObject.putIfAbsent("password", "huawei@123");

        jsonObject.putIfAbsent("userName", "test01");
        jsonObject.putIfAbsent("password", "Huawei@123");
        log.info("登陆的请求参数 [{}]", jsonObject.toString());

        try {
            request.send(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(Status.ERROR.getCode(), e.getMessage());
        }

        String body = request.body();
        log.info("{}", body);
        if (request.code() != SUCCESS) {
            throw new BaseException(EyeStatus.LOGIN);
        }
        JSONObject response = new JSONObject(body);
        int resultCode = response.getInt(RESULTCODE);
        if (ZERO != resultCode) {
            log.error("登陆接口的resultCode;[{}]", resultCode);
            throw new BaseException(resultCode, "登陆请求失败");
        }
        String cookie = request.header("Set-Cookie");
        return cookie;

    }

    @Override
    public String govExtLogin() {
        HttpRequest request = HttpRequest.post(govExtUrl + loginUrl).contentType(contentType)
                .connectTimeout(3000).readTimeout(5000);
        request.trustAllCerts();
        request.trustAllHosts();
        JSONObject jsonObject = new JSONObject();
        jsonObject.putIfAbsent("userName", "test01");
        jsonObject.putIfAbsent("password", "Huawei@12#$");
        log.info("请求地址[{}],登陆政务外网的请求参数 [{}]",govExtUrl + loginUrl, jsonObject.toString());

        try {
            request.send(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(Status.ERROR.getCode(), e.getMessage());
        }

        String body = request.body();
        log.info("登陆政务外网的返回参数{}", body);
        if (request.code() != SUCCESS) {
            throw new BaseException(EyeStatus.LOGIN);
        }
        JSONObject response = new JSONObject(body);
        int resultCode = response.getInt(RESULTCODE);
        if (ZERO != resultCode) {
            log.error("登陆政务外网的resultCode;[{}]", resultCode);
            throw new BaseException(resultCode, "登陆请求失败");
        }
        String cookie = request.header("Set-Cookie");
        return cookie;
    }

    @Override
    public String getRtspUrl(String cameraCode, String cookie, boolean isGov) {
        String requestUrl;
        if (isGov) {
            requestUrl = govExtUrl + rstpUrl;
        } else {
            requestUrl = url + rstpUrl;
        }
        HttpRequest videoRequest = HttpRequest.post(requestUrl).contentType(contentType)
                .connectTimeout(3000).readTimeout(5000);
        videoRequest.header("Cookie", cookie);
        videoRequest.trustAllCerts();
        videoRequest.trustAllHosts();
        JSONObject json = new JSONObject();
        json.putIfAbsent("cameraCode", cameraCode);
        JSONObject paramJson = new JSONObject();
        paramJson.putIfAbsent("serviceType", 1);
        paramJson.putIfAbsent("packProtocolType", 1);
        paramJson.putIfAbsent("broadCastType", 0);
        paramJson.putIfAbsent("protocolType", 2);
        paramJson.putIfAbsent("streamType", 1);
        paramJson.putIfAbsent("transMode", 0);
        paramJson.putIfAbsent("clientType", 1);
//        paramJson.putIfAbsent("dstIP", "172.30.1.229");
        paramJson.putIfAbsent("dstIP", "111.33.175.77");

        json.putIfAbsent("mediaURLParam", paramJson);

        log.info("json:{}", json.toString());
        try {
            videoRequest.send(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(Status.ERROR.getCode(), e.getMessage());
        }
        if (videoRequest.code() != SUCCESS) {
            throw new BaseException(Status.ERROR.getCode(), "获取视频流请求失败");
        }
        String body = videoRequest.body();
        log.info("获取视频流的请求参数{}", body);

        JSONObject response = new JSONObject(body);
        int resultCode = response.getInt(RESULTCODE);
        if (ZERO != resultCode) {
            log.error("获取视频流的resultCode;[{}]", resultCode);
            if ("129108001".equals(resultCode)) {
                throw new BaseException(resultCode, "设备不在线");
            }
            throw new BaseException(resultCode, "获取视频流请求失败");
        }
        log.info("{}", response.get("rtspURL"));
        String videoUrl = response.getStr("rtspURL");
        return videoUrl;
    }

    @Override
    public void capturel(String cameraCode, String cookie, String domainCode, boolean isGov) {
        String capUrl;
        if (isGov) {
            capUrl = govExtUrl + "/platform/platformSnapshot/" + cameraCode + "/" + domainCode;
        } else {
            capUrl = url + "/platform/platformSnapshot/" + cameraCode + "/" + domainCode;
        }

        HttpRequest request;
        try {
            request = HttpRequest.get(capUrl).connectTimeout(3000).readTimeout(5000);
            request.header("Cookie", cookie);
            request.trustAllCerts();
            request.trustAllHosts();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(Status.ERROR.getCode(), e.getMessage());
        }

        if (request.code() != SUCCESS) {
            throw new BaseException(Status.ERROR.getCode(), "抓拍请求失败");
        }
        String body = request.body();
        JSONObject response = new JSONObject(body);
        int resultCode = response.getInt(RESULTCODE);
        if (ZERO != resultCode) {
            log.error("抓拍接口的resultCode;[{}]", resultCode);
            throw new BaseException(Status.ERROR.getCode(), "抓拍请求失败");
        }
        log.info("----------------抓拍成功------------");
    }

    @Override
    public List<SnapshotInfoVO> getCapturel(String cookie, CapturelDTO capturelDTO, boolean isGov) {
        String capUrl = "/platform/snapshotlist/" + capturelDTO.getCameracode() + "/" + capturelDTO.getDomainCode()
                + "/" + capturelDTO.getStartTime() + "/" + capturelDTO.getEndTime() + "/" + capturelDTO.getFromIndex()
                + "/" + capturelDTO.getToIndex() + "/" + capturelDTO.getSnaptype();
        if (isGov) {
            capUrl = govExtUrl + capUrl;
        } else {
            capUrl = url + capUrl;
        }
        HttpRequest request;
        try {
            request = HttpRequest.get(capUrl).connectTimeout(3000).readTimeout(5000);
            request.header("Cookie", cookie);
            request.trustAllCerts();
            request.trustAllHosts();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(Status.ERROR.getCode(), e.getMessage());
        }

        if (request.code() != SUCCESS) {
            throw new BaseException(Status.ERROR.getCode(), "获取图片请求失败");
        }
        String body = request.body();
        JSONObject response = new JSONObject(body);
        int resultCode = response.getInt(RESULTCODE);
        if (ZERO != resultCode) {
            log.error("获取图片接口的resultCode;[{}]", resultCode);
            throw new BaseException(Status.ERROR.getCode(), "获取图片请求失败");
        }
        List<SnapshotInfoVO> list = new ArrayList<>(10);
        list = response.getBean("snapshotInfos", list.getClass());
        return list;
    }
}
