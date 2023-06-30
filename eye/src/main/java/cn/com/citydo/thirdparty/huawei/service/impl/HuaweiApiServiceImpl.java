package cn.com.citydo.thirdparty.huawei.service.impl;

import cn.com.citydo.common.BaseException;
import cn.com.citydo.common.Consts;
import cn.com.citydo.common.Status;
import cn.com.citydo.thirdparty.huawei.response.SubscribeResponse;
import cn.com.citydo.thirdparty.huawei.response.Suspect;
import cn.com.citydo.thirdparty.huawei.response.SuspectTaskResponse;
import cn.com.citydo.thirdparty.huawei.response.WarnResponse;
import cn.com.citydo.thirdparty.huawei.response.facerepositories.FaceRepositoriesResponse;
import cn.com.citydo.thirdparty.huawei.response.login.LoginResponse;
import cn.com.citydo.thirdparty.huawei.service.HuaweiApiService;
import cn.com.citydo.utils.HttpUtil;
import cn.hutool.json.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HuaweiApiServiceImpl implements HuaweiApiService {

    @Value("${huawei2.baseUrl}${huawei2.loginUrl}")
    private String loginUrl;
    @Value("${huawei2.baseUrl}${huawei2.facerepositoriesUrl}")
    private String facerepositoriesUrl;
    @Value("${huawei2.huaweiAccount.account}")
    private String account;
    @Value("${huawei2.huaweiAccount.pwd}")
    private String pwd;

    @Value("${huawei2.baseUrl}${huawei2.keepLoginUrl}")
    private String keepLoginUrl;

    @Value("${huawei2.baseUrl}${huawei2.taskUrl}")
    private String taskUrl;

    @Value("${huawei2.baseUrl}${huawei2.subscribeUrl}")
    private String subscribeUrl;

    private final String contentType = "application/x-www-form-urlencoded";


    public String login() {
        log.info("****** 连接地址:" + loginUrl);
        log.info("登录账号:account=" + account);
        Map<String, String> huaweiAccount = new HashMap() {
            {
                put("account", account);
                put("pwd", pwd);
                put("dstIp","172.30.1.229");
            }
        };
        HttpRequest httpRequest = new HttpRequest(HttpUtil.buildHttpGetParam(loginUrl, huaweiAccount), "POST");
        httpRequest.contentType(contentType, "UTF-8");
        httpRequest.trustAllCerts();
        httpRequest.trustAllHosts();
        try {
            String body = httpRequest.body();
            JAXBContext context = JAXBContext.newInstance(LoginResponse.class);
            Unmarshaller unMar = context.createUnmarshaller();
            LoginResponse response = (LoginResponse) unMar.unmarshal(new StringReader(body));
            log.info("login response:{}", response);
            if (!Consts.ZERO.equals(response.getResult().getCode())) {
                throw new BaseException(Status.ERROR.getCode(), response.getResult().getErrmsg());
            }
        } catch (JAXBException e) {
            log.error(e.getMessage(), e);
        }
        return httpRequest.header("Set-Cookie");
    }

    @Override
    public FaceRepositoriesResponse queryPeople(Map<String, String> params) {
        // 登录
        String cookie = login();
        // 构造人员查询参数
        HttpRequest httpRequest = new HttpRequest(facerepositoriesUrl, "POST");
        httpRequest.contentType("application/xml", "UTF-8");
        httpRequest.header("Cookie", cookie);
        httpRequest.trustAllCerts();
        httpRequest.trustAllHosts();
        String str = "<request><page><no>1</no><orderName>time</orderName><size>1000</size><sort>desc</sort></page>" +
                "<repositorIds>" + params.get("repositorIds") + "</repositorIds>" +
                "<ids>" + params.get("ids") + "</ids>" + "</request>";
        httpRequest.send(str);
        try {
            if (!httpRequest.ok()) {
                throw new BaseException(Status.ERROR.getCode(), "queryPeople request fail");
            }
            String body = httpRequest.body();
            JAXBContext context = JAXBContext.newInstance(FaceRepositoriesResponse.class);
            Unmarshaller unMar = context.createUnmarshaller();
            FaceRepositoriesResponse response = (FaceRepositoriesResponse) unMar.unmarshal(new StringReader(body));
            log.info("queryPeople response:{}", response);
            if (!Consts.ZERO.equals(response.getResult().getCode())) {
                throw new BaseException(Status.ERROR.getCode(), response.getResult().getErrmsg());
            }
            return response;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BaseException(Status.ERROR.getCode(), e.getMessage());
        }
    }

    @Override
    public void keepLogin(String cookie) {
        try {
            HttpRequest httpRequest = new HttpRequest(keepLoginUrl, "POST").connectTimeout(3000).readTimeout(5000);
            httpRequest.contentType("text/plain", HttpRequest.CHARSET_UTF8);
            httpRequest.trustAllCerts();
            httpRequest.trustAllHosts();
            httpRequest.header("Cookie", cookie);
            if (!httpRequest.ok()) {
                throw new BaseException(Status.ERROR.getCode(), "keepLogin request fail");
            }
            String body = httpRequest.body();
            log.info("keepLogin result:{}", body);
            JAXBContext context = JAXBContext.newInstance(LoginResponse.class);
            Unmarshaller unMar = context.createUnmarshaller();
            LoginResponse response = (LoginResponse) unMar.unmarshal(new StringReader(body));
            log.info("keepLogin response:{}", response);
            if (!Consts.ZERO.equals(response.getResult().getCode())) {
                throw new BaseException(Status.ERROR.getCode(), response.getResult().getErrmsg());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BaseException(Status.ERROR.getCode(), e.getMessage());
        }
    }

    @Override
    public List<String> suspectTask(String cookie) {
        HttpRequest httpRequest = HttpRequest.post(taskUrl).contentType("text/plain")
                .connectTimeout(3000).readTimeout(5000);
        httpRequest.header("Cookie", cookie);
        httpRequest.trustAllCerts();
        httpRequest.trustAllHosts();

        try {
            httpRequest.send("<request>" +
                    " <page>" +
                    " <no>1</no>" +
                    " <pageSize>1000</pageSize>" +
                    " </page>" +
                    "</request>");
            if (!httpRequest.ok()) {
                throw new BaseException(Status.ERROR.getCode(), "suspectTask request fail");
            }
            String body = httpRequest.body();
            log.info("suspectTask result:{}", body);

            JAXBContext context = JAXBContext.newInstance(SuspectTaskResponse.class);
            Unmarshaller unMar = context.createUnmarshaller();
            SuspectTaskResponse response = (SuspectTaskResponse) unMar.unmarshal(new StringReader(body));
            log.info("suspectTask conver response:{}", response);
            if (!Consts.ZERO.equals(response.getResult().getCode())) {
                throw new BaseException(Status.ERROR.getCode(), response.getResult().getErrmsg());
            }
            return response.getSuspectList().stream().map(Suspect::getSuspectId).distinct().collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BaseException(Status.ERROR.getCode(), e.getMessage());
        }
    }


    @Value("${huawei2.pushUrl}")
    private  String pushDataUrl;

    @Override
    public List<String> subscribe(String cookie) {
        //调用布控任务
        List<String> list = this.suspectTask(cookie);
        String url = StringUtils.join(list, ",");

        HttpRequest httpRequest = HttpRequest.post(subscribeUrl).contentType("text/plain")
                .connectTimeout(3000).readTimeout(5000);
        httpRequest.header("Cookie", cookie);
        httpRequest.trustAllCerts();
        httpRequest.trustAllHosts();
        try {
            String param = "<request>" +
                    "<callbackUrl>" +
                    " <master>" + pushDataUrl + "</master>" +
                    " <slave>" + pushDataUrl + "</slave>" +
                    " </callbackUrl>" +
                    "<suspectId>" + url + "</suspectId>" +
                    "</request>";
            log.info(param);
            httpRequest.send(param);
            if (!httpRequest.ok()) {
                throw new BaseException(Status.ERROR.getCode(), "subscribe request fail");
            }
            String body = httpRequest.body();
            log.info("subscribe result:{}", body);

            JAXBContext context = JAXBContext.newInstance(SubscribeResponse.class);
            Unmarshaller unMar = context.createUnmarshaller();
            SubscribeResponse response = (SubscribeResponse) unMar.unmarshal(new StringReader(body));
            log.info("subscribe conver response:{}", response);
            if (!Consts.ZERO.equals(response.getResult().getCode())) {
                throw new BaseException(Status.ERROR.getCode(), response.getResult().getErrmsg());
            }
            return response.getSubscribeList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BaseException(Status.ERROR.getCode(), e.getMessage());
        }
    }

    @Override
    public WarnResponse getWarnDataList() {
        // 登录
        String cookie = login();
        // 构造人员查询参数
        String url = "https://burg.tj.chinatowercom.cn/wisdomeye/api/SqlSelect?sqlname=getTimeAlramList&parameter=1,1,1,2021-08-05,2021-08-11&limit=10";
        HttpRequest request;

        try {
            request = HttpRequest.get(url).connectTimeout(3000).readTimeout(5000);
            request.header("Cookie", cookie);
            request.trustAllCerts();
            request.trustAllHosts();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(Status.ERROR.getCode(), e.getMessage());
        }
        if (request.code() != 200) {
            log.info("接口调用失败，失败原因为：[{}]");
            throw new BaseException(Status.ERROR.getCode(),"，失败原因：接口调用失败");
        }
        String body = request.body();
        JSONObject response = new JSONObject(body);
        int code = response.getInt("code");
        if (200 != code) {
            throw new BaseException(Status.ERROR.getCode(), response.getStr("msg"));
        }
        return null;
    }

}
