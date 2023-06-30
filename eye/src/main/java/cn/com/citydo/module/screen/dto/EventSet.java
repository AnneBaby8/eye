package cn.com.citydo.module.screen.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 告警事件内容
 *
 * @Author blackcat
 * @create 2021/5/12 14:34
 * @version: 1.0
 * @description:
 */
@Data
public class EventSet {

    /** 告警目标对应UUID */
    @JsonProperty("detection_id")
    private String detectionId;
    /** 告警输出对象的矩形框 */
    @JsonProperty("bounding_box")
    private BoxDTO boundingBox;
    /** 告警输出疑似丢垃圾的人的矩形框，仅党"垃圾检测开关"trash_detect_sw=2时有该字段 */
    @JsonProperty("person_box")
    private BoxDTO personBox;

}
