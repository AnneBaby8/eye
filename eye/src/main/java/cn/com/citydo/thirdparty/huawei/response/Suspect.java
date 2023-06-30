package cn.com.citydo.thirdparty.huawei.response;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * @Author blackcat
 * @create 2021/7/9 15:02
 * @version: 1.0
 * @description:
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Suspect {
    @XmlElement(name = "suspectId")
    private String suspectId;
    @XmlElement(name = "alarmLevel")
    private int alarmLevel;
}
