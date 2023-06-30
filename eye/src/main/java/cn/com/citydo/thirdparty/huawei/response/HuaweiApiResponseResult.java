package cn.com.citydo.thirdparty.huawei.response;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class HuaweiApiResponseResult {

    /** 返回码，0为成功，非0为不成功 */
    @XmlElement(name="code")
    private String code;
    /** 错误信息 */
    @XmlElement(name="errmsg")
    private String errmsg;

}
