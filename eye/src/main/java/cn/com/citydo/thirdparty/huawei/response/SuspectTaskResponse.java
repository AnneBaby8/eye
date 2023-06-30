package cn.com.citydo.thirdparty.huawei.response;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @Author blackcat
 * @create 2021/7/9 15:01
 * @version: 1.0
 * @description:
 */
@Data
@XmlRootElement(name="response")
@XmlAccessorType(XmlAccessType.FIELD)
public class SuspectTaskResponse {

    /** 结果节点 */
    @XmlElement(name="result")
    private HuaweiApiResponseResult result;

    @XmlElementWrapper(name = "suspectList")
    @XmlElement(name="suspect")
    private List<Suspect> suspectList;
}
