package cn.com.citydo.thirdparty.huawei.response;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;


/**
 * @Author blackcat
 * @create 2021/7/12 9:50
 * @version: 1.0
 * @description:
 */
@Data
@XmlRootElement(name="response")
@XmlAccessorType(XmlAccessType.FIELD)
public class SubscribeResponse {

    /** 结果节点 */
    @XmlElement(name="result")
    private HuaweiApiResponseResult result;

    @XmlElementWrapper(name = "http-alarm-url-list")
    @XmlElement(name="http-alarm-url")
    private List<String> subscribeList;
}
