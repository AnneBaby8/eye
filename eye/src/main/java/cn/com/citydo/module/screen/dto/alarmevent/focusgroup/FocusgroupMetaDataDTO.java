package cn.com.citydo.module.screen.dto.alarmevent.focusgroup;

import cn.com.citydo.module.screen.dto.alarmevent.MetaDataDTO;
import cn.com.citydo.module.screen.dto.alarmevent.PosDTO;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;

@Data
public class FocusgroupMetaDataDTO extends MetaDataDTO {

    /** 与黑名单图片的相似度 */
    @XmlElement(name="scr")
    private int scr;
    /** 算法编码 */
    @XmlElement(name="algorithm-code")
    private String algorithmCode;
    /** 算法名称 */
    @XmlElement(name="algorithm-name")
    private String algorithmName;
    /** 告警模式 */
    @XmlElement(name="alarm-mold")
    private int alarmMold;
    /** 告警命中 */
    @XmlElement(name="alarm-match")
    private int alarmMatch;
    /** 年龄 */
    @XmlElement(name="age")
    private int age;
    /** 性别 */
    @XmlElement(name="gender")
    private int gender;
    /** 眼睛 */
    @XmlElement(name="eyelass")
    private int eyelass;
    /** 民族 */
    @XmlElement(name="nation")
    private int nation;
    /** 表情 */
    @XmlElement(name="expression")
    private int expression;
    /** 年龄值 */
    @XmlElement(name="ageValue")
    private String ageValue;
    /** 口罩 */
    @XmlElement(name="mouthmask")
    private int mouthmask;
    /** 年龄下限 */
    @XmlElement(name="ageLower")
    private String ageLower;
    /** 肤色 */
    @XmlElement(name="skinColor")
    private String skinColor;
    /** 头发 */
    @XmlElement(name="hair")
    private String hair;
    /** 发色 */
    @XmlElement(name="hairColor")
    private String hairColor;
    /** 脸型 */
    @XmlElement(name="faceStyle")
    private String faceStyle;
    /** 脸部特征 */
    @XmlElement(name="facialFeature")
    private String facialFeature;
    /** 体貌特征 */
    @XmlElement(name="phyFeature")
    private String phyFeature;
    /** 口罩颜色 */
    @XmlElement(name="respiratorColor")
    private String respiratorColor;
    /** 帽子款式 */
    @XmlElement(name="capStyle")
    private String capStyle;
    /** 帽子颜色 */
    @XmlElement(name="capcolor")
    private String capcolor;
    /** 眼镜款式 */
    @XmlElement(name="glassStyle")
    private String glassStyle;
    /** 眼镜颜色 */
    @XmlElement(name="glassColor")
    private String glassColor;
    /** 眉形 */
    @XmlElement(name="eyebrowStyle")
    private String eyebrowStyle;
    /** 鼻型 */
    @XmlElement(name="noseStyle")
    private String noseStyle;
    /** 胡型 */
    @XmlElement(name="mustacheStyle")
    private String mustacheStyle;
    /** 嘴唇 */
    @XmlElement(name="lipStyle")
    private String lipStyle;
    /** 帽子 */
    @XmlElement(name="hat")
    private String hat;
    /** 水平转动的偏航角 */
    @XmlElement(name="yaw")
    private String yaw;
    /** 垂直转动仰俯角 */
    @XmlElement(name="pitch")
    private String pitch;
    /** 左右转动的旋转角 */
    @XmlElement(name="roll")
    private String roll;
    /** 左眼状态 */
    @XmlElement(name="liftEye")
    private int liftEye;
    /** 右眼状态 */
    @XmlElement(name="rightEye")
    private int rightEye;
    /** 是否有胡须 */
    @XmlElement(name="mustache")
    private int mustache;
    /** 位置 */
    @XmlElement(name="pos")
    private PosDTO pos;

}
