package cn.com.citydo.thirdparty.huawei.response.facerepositories;

import cn.com.citydo.thirdparty.huawei.response.HuaweiApiResponseResult;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name="response")
@XmlAccessorType(XmlAccessType.FIELD)
public class FaceRepositoriesResponse {

    /** 结果数量 */
    @XmlElement(name="number")
    private String number;
    /** 结果节点 */
    @XmlElement(name="result")
    private HuaweiApiResponseResult result;
    /** 人员名单列表 */
    @XmlElementWrapper(name = "peopleList")
    @XmlElement(name="people")
    private List<FaceRepositoriesPeople> peopleList;
    /** 算法响应列表 */
    @XmlElement(name="algorithmResults")
    private List<FaceRepositoriesAlgorithmResult> algorithmResults;



}
