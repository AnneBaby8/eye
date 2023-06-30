package cn.com.citydo.thirdparty.huawei.response.facerepositories;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class FaceRepositoriesPeopleFaceFeature {

    /** 图像状态（0：成功，1：特征提取中，4：添加失败，7：非人脸） */
    @XmlElement(name="faceState")
    private String faceState;
    /** 人脸特征id */
    @XmlElement(name="featureId")
    private String featureId;
    /** 人脸算法 */
    @XmlElement(name="algorithmCode")
    private String algorithmCode;
    /** 图片质量分数 */
    @XmlElement(name="quality")
    private String quality;
    /** 相似度 */
    @XmlElement(name="similarity")
    private String similarity;
    /** 相似度 */
    @XmlElement(name="dSimilarity")
    private String dSimilarity;


}
