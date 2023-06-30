package cn.com.citydo.module.screen.dto.alarmevent;


import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author shaodl
 * @create 2021/6/6 14:35
 * @version: 1.0
 * @description: 预警事件内容结构体
 */
@Data
@XmlRootElement(name="content")
@XmlAccessorType(XmlAccessType.FIELD)
public class AlarmEventContentDTO {

    @XmlElement(name="common-info")
    private CommonInfoDTO commonInfo;

    @XmlElement(name="private-info")
    private PrivateInfoDTO privateInfo;

    @XmlElement(name="third-field")
    private PrivateInfoDTO thirdField;

}
