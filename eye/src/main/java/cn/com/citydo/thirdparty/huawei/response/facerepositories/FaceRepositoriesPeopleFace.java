package cn.com.citydo.thirdparty.huawei.response.facerepositories;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class FaceRepositoriesPeopleFace {

    /** 唯一标识 */
    @XmlElement(name="thirdId")
    private String thirdId;
    /** 图片id */
    @XmlElement(name="fileId")
    private String fileId;
    /** 人员图片url */
    @XmlElement(name="url")
    private String url;
    /** 特征值集合 */
    @XmlElement(name="featureList")
    private List<FaceRepositoriesPeopleFaceFeature> featureList;

}
