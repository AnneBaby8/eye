package cn.com.citydo.thirdparty.huawei.service;

import cn.com.citydo.thirdparty.huawei.response.WarnResponse;
import cn.com.citydo.thirdparty.huawei.response.facerepositories.FaceRepositoriesResponse;

import java.util.List;
import java.util.Map;

public interface HuaweiApiService {

    /**
     * 登录
     */
    String login();


    FaceRepositoriesResponse queryPeople(Map<String, String> params);


    void keepLogin(String cookies);


    List<String> suspectTask(String cookie);


    List<String> subscribe(String cookie);

    WarnResponse getWarnDataList();
}
