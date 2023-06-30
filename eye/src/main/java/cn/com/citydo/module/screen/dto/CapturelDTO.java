package cn.com.citydo.module.screen.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author blackcat
 * @create 2021/5/12 10:14
 * @version: 1.0
 * @description:
 */
@Data
@ApiModel(value = "抓拍图片实体")
public class CapturelDTO {

    @ApiModelProperty(value = "摄像机编码#号之前数据")
    private String cameracode;

    @ApiModelProperty(value = "设备所属域编码(摄像机编码#号之后数据)")
    private String domainCode;

    @ApiModelProperty(value = "开始时间，格式如yyyyMMddHHmmss")
    private String startTime;
    //结束时间，格式如yyyyMMddHHmmss
    @ApiModelProperty(value = "结束时间，格式如yyyyMMddHHmmss")
    private String endTime;
    //开始索引
    @ApiModelProperty(value = "开始索引")
    private Integer fromIndex;
    //结束索引(结束索引必须大于或者等于开始索引)
    @ApiModelProperty(value = "结束索引(结束索引必须大于或者等于开始索引)")
    private Integer toIndex;
    //  抓拍类型：1：智能分析抓拍
    //2：告警抓拍
    //4：手动抓拍(包括定时抓拍)
    @ApiModelProperty(value = "抓拍类型：1：智能分析抓拍;2：告警抓拍；3：1和2；4：手动抓拍(包括定时抓拍);")
    private Integer snaptype;

}
