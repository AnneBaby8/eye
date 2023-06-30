package cn.com.citydo.thirdparty.huawei.response;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;

/**
 * <p>
 *
 * </p>
 *
 * @author wx227336
 * @Date 2021/8/23 14:49
 * @Version 1.0
 */
@Data
public class WarnResponse {

    /** 返回码，0为成功，非0为不成功 */
    @XmlElement(name="code")
    private String code;
    /** 错误信息 */
    @XmlElement(name="errmsg")
    private String errmsg;
}
