package cn.com.citydo.thirdparty.huawei.response.facerepositories;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * @author shaodl
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class FaceRepositoriesPeople {

    /** 人员姓名 */
    @XmlElement(name="name")
    private String name;
    /** 人员名单id */
    @XmlElement(name="peopleId")
    private String peopleId;
    /** 性别 */
    @XmlElement(name="gender")
    private Integer gender;
    /** 民族 */
    @XmlElement(name="nationality")
    private String nationality;
    /** 描述 */
    @XmlElement(name="description")
    private String description;
    /** 创建时间 */
    @XmlElement(name="createTime")
    private String createTime;
    /** 类型 */
    @XmlElement(name="type")
    private String type;
    /** 出生时间 */
    @XmlElement(name="bornTime")
    private String bornTime;
    /** 职业 */
    @XmlElement(name="occupation")
    private String occupation;
    /** 保留字段 */
    @XmlElement(name="tag")
    private String tag;
    /** 国家 */
    @XmlElement(name="country")
    private String country;
    /** 证件类型 */
    @XmlElement(name="credentialType")
    private String credentialType;
    /** 证件号码 */
    @XmlElement(name="credentialNumber")
    private String credentialNumber;
    /** 库id */
    @XmlElement(name="repositorId")
    private String repositorId;
    /** 人脸列表 */
    @XmlElementWrapper(name = "faceList")
    @XmlElement(name="face")
    private List<FaceRepositoriesPeopleFace> faceList;

}
