package cn.com.citydo.module.screen.dto;


import cn.com.citydo.module.screen.entity.EventWarningDetail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 园区智能体边缘服务，非人脸预警事件推送内容结构体
 * 事件类型：占道经营、出点经营、垃圾识别
 *
 * @Author blackcat
 * @create 2021/5/12 13:50
 * @version: 1.0
 * @description:
 */
@Data
public class NoticeDTO {

    /** 摄像头编号 */
    @JsonProperty("stream_id")
    private String streamId;
    /** 事件类型（占道经营：1114112，出店经营：1245184，垃圾检测：1310720） */
    @JsonProperty("event_type")
    private String eventType;
    /** 作业ID */
    @JsonProperty("task_id")
    private String taskId;
    /** 触发告警时间点的时间戳 */
    private long timestamp;
    /** 唯一标识本次输出消息UUID */
    @JsonProperty("message_id")
    private String messageId;
    /** 告警时刻输入的视频图像的Base64编码结果，可转换成图片 */
    @JsonProperty("image_base64")
    private String imageBase64;

    /** 业务输出内容，注：目前对业务没有用到，没有落库 */
    private DataDTO data;

    @JsonIgnore
    private EventWarningDetail eventWarningDetail;

}
