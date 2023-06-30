package cn.com.citydo.module.screen.dto.alarmevent;

import cn.com.citydo.module.screen.dto.alarmevent.focusgroup.FocusgroupMetaDataDTO;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class PrivateInfoDTO {

    @XmlElement(name="meta")
    private FocusgroupMetaDataDTO metaDataDTO;

    @XmlElement(name="pic")
    private PicDTO pic;

}
