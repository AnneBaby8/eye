package cn.com.citydo.module.screen.dto.alarmevent;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ThirdFieldDTO {

    @XmlElement(name="field-len")
    private int fieldLen;
    @XmlElement(name="field-value")
    private String fieldValue;

}
