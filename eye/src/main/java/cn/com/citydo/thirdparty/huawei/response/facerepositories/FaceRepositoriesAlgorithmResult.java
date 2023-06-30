package cn.com.citydo.thirdparty.huawei.response.facerepositories;

import cn.com.citydo.thirdparty.huawei.response.HuaweiApiResponseResult;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class FaceRepositoriesAlgorithmResult {

    /** 结果节点 */
    @XmlElement(name="result")
    private HuaweiApiResponseResult result;
    /** 记录数 */
    @XmlElement(name="number")
    private String number;
    /** 人脸算法编码 */
    @XmlElement(name="algorithmCode")
    private String algorithmCode;
    /** 原图质量分数 */
    @XmlElement(name="srcQuality")
    private String srcQuality;
    /** 人员名单列表 */
    @XmlElement(name="peopleList")
    private List<FaceRepositoriesPeople> peopleList;


}
