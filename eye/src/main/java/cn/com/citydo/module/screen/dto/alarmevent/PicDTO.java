package cn.com.citydo.module.screen.dto.alarmevent;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class PicDTO {

    @XmlElement(name="casefile-id")
    private String casefileId;
    @XmlElement(name="_fileId")
    private String file;
    @XmlElement(name="fileIdEx")
    private String fileIdEx;
    @XmlElement(name="_startPos")
    private int startPos;
    @XmlElement(name="_thumbLen")
    private int thumbLen;
    @XmlElement(name="_len")
    private int len;
    @XmlElement(name="_m_id")
    private String mId;
    @XmlElement(name="_s_inx")
    private String sInx;
    @XmlElement(name="_imageUrl")
    private String imageUrl;
    @XmlElement(name="_thumImageUrl")
    private String thumImageUrl;
    @XmlElement(name="feature-value")
    private String featureValue;
    @XmlElement(name="feature-length")
    private int featureLength;
    @XmlElement(name="feature-id")
    private long featureId;
    @XmlElement(name="feature-index")
    private String featureIndex;
    @XmlElement(name="is-url")
    private String isUrl;
    @XmlElement(name="picUniqueId")
    private String picUniqueId;
    @XmlElement(name="thirdUrl")
    private String thirdUrl;
    @XmlElement(name="thirdThumbUrl")
    private String thirdThumbUrl;
    @XmlElement(name="originalField")
    private String originalField;
    @XmlElement(name="originalFieldEx")
    private String originalFieldEx;
    @XmlElement(name="longtitude")
    private double longtitude;
    @XmlElement(name="latitude")
    private double latitude;
    @XmlElement(name="quality")
    private int quality;
    @XmlElement(name="userData")
    private String userData;
    @XmlElement(name="savedExtensionField")
    private SavedExtensionFieldDTO savedExtensionField;
    @XmlElement(name="extensionField")
    private ExtensionFieldDTO extensionField;

}
