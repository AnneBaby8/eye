package cn.com.citydo.module.screen.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 业务输出内容
 *
 * @Author blackcat
 * @create 2021/5/12 13:51
 * @version: 1.0
 * @description:
 */
@Data
public class DataDTO {

    /** 输出事件列表 */
    @JsonProperty("event_set")
    private List<EventSet> eventSet;

}
