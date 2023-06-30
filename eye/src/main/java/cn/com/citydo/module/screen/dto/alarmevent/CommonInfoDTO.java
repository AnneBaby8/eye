package cn.com.citydo.module.screen.dto.alarmevent;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @Author shaodl
 * @create 2021/6/6 14:35
 * @version: 1.0
 * @description: 预警事件通用信息结构体
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CommonInfoDTO {

    /** 智能分析任务ID */
    @XmlElement(name="task-id")
    private String taskId;
    /** 摄像机ID */
    @XmlElement(name="camera-id")
    private String cameraId;
    /** 互联编码 */
    @XmlElement(name="cameraCNCode")
    private String cameraCNCode;
    /** 摄像机名称 */
    @XmlElement(name="camera-name")
    private String cameraName;
    /** 摄像机索引 */
    @XmlElement(name="camera-index")
    private String cameraIndex;
    /** 虚拟卡口序列号 */
    @XmlElement(name="vstation-sn")
    private String vstationSn;
    /** 虚拟卡口名称 */
    @XmlElement(name="vstation-name")
    private String vstationName;
    /** 虚拟卡口索引 */
    @XmlElement(name="vstation-index")
    private String vstationIndex;
    /** 案件ID */
    @XmlElement(name="case-id")
    private String caseId;
    /** 资料ID */
    @XmlElement(name="case-file-id")
    private String caseFileId;
    /** 轨迹分片数量 */
    @XmlElement(name="slice-num")
    private int sliceNum;
    /** 告警来源（0：视频资料，1：摄像机） */
    @XmlElement(name="source")
    private String source;
    /** 源业务ID（0：手工上传，1：IVS视频采集，2：虚拟卡口，4：行为分析） */
    @XmlElement(name="source-system-id")
    private int sourceSystemId;
    /** 分辨率 */
    @XmlElement(name="resolution")
    private String resolution;
    /** 告警ID */
    @XmlElement(name="alarm-id")
    private String alarmId;
    /** 告警级别（1：紧急，2：严重，3：一般，4：提示） */
    @XmlElement(name="alarm-level")
    private String alarmLevel;
    /** 告警时间 */
    @XmlElement(name="alarm-time")
    private String alarmTime;
    /** 告警类型（0：代表行为分析，1：普通车牌识别，4：代表人脸识别，5：人体，8：车辆GPU识别，9：人车混合结构体） */
    @XmlElement(name="alarm-type")
    private int alarmType;
    /** 告警图片名称 */
    @XmlElement(name="alarm-pic-name")
    private String alarmPicName;
    /** 创建时间 */
    @XmlElement(name="ctime")
    private String ctime;
    /** 目标关联ID */
    @XmlElement(name="relationId")
    private String relationId;
    /** 目标类型（-1：不限，0：未知，1：机动车，2：非机动车，3：人脸，4：人体，5：骑行人，6：其他。多种类型以英文逗号隔开） */
    @XmlElement(name="relationType")
    private String relationType;
    /** 告警类型 */
    @XmlElement(name="rule-type")
    private int ruleType;
    /** 区分实时智能分析元数据和布控产生的告警数据 */
    @XmlElement(name="confirm")
    private int confirm;
    /** 是否关闭 */
    @XmlElement(name="closed")
    private int closed;
    /** 当前目标ID */
    @XmlElement(name="object-id")
    private String objectId;
    /** 人脸ID */
    @XmlElement(name="face-id")
    private String faceId;
    /** 视频类型 */
    @XmlElement(name="video-type")
    private int videoType;
    /** 对象类型 */
    @XmlElement(name="object-type")
    private int objectType;
    /** 告警图片标签 */
    @XmlElement(name="alarm-pic-tag")
    private String alarmPicTag;
    /** 告警图片组名称 */
    @XmlElement(name="alarm-pic-groupname")
    private String alarmPicGroupName;
    /** 告警图片组ID */
    @XmlElement(name="alarm-pic-groupid")
    private String alarmPicGroupid;
    /** 告警图片VIP标签 */
    @XmlElement(name="alarm-pic-viptag")
    private String alarmPicViptag;
    /** 红名单告警ID */
    @XmlElement(name="red-alarm-id")
    private String redAlarmId;
    /** 布控ID */
    @XmlElement(name="suspect-id")
    private String suspectId;
    /** 黑名单组ID */
    @XmlElement(name="blkgrp-id")
    private String blkgrpId;
    /** 黑名单ID */
    @XmlElement(name="blacklist-id")
    private String blacklistId;
    /** 人脸布控底库图片质量 */
    @XmlElement(name="blkQuality")
    private int blkQuality;
    /** 人脸布控图片文件ID */
    @XmlElement(name="blkFileId")
    private String blkFileId;
    /** 人脸布控图片URL */
    @XmlElement(name="blkUrl")
    private String blkUrl;
    /** 域编码 */
    @XmlElement(name="domain-code")
    private String domainCode;
    /** 1400对接缩略图ID */
    @XmlElement(name="thumbImageId1400")
    private String thumbImageId1400;
    /** 1400对接原始图ID */
    @XmlElement(name="imageId1400")
    private String imageId1400;
    /** 1400人员标识 */
    @XmlElement(name="glmgCntId")
    private String glmgCntId;

}
