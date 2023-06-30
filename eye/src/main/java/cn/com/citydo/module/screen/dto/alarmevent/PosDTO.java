package cn.com.citydo.module.screen.dto.alarmevent;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class PosDTO {

    @XmlElement(name="left")
    private int left;
    @XmlElement(name="top")
    private int top;
    @XmlElement(name="right")
    private int right;
    @XmlElement(name="bottom")
    private int bottom;

}
