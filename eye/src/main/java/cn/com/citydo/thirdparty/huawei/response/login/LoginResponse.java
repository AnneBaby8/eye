package cn.com.citydo.thirdparty.huawei.response.login;

import cn.com.citydo.thirdparty.huawei.response.HuaweiApiResponseResult;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 登录认证响应
 * @author
 */
@Data
@XmlRootElement(name="response")
@XmlAccessorType(XmlAccessType.FIELD)
public class LoginResponse {

    /** 结果节点 */
    @XmlElement(name="result")
    private HuaweiApiResponseResult result;
    /** 剩余锁定时间 */
    @XmlElement(name="leftLockedTime")
    private String leftLockedTime;
    /** 距离密码失效天数 */
    @XmlElement(name="pwdExpireDays")
    private String pwdExpireDays;

}
