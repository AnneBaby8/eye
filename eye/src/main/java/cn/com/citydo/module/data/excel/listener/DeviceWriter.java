package cn.com.citydo.module.data.excel.listener;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author blackcat
 * @create 2021/6/1 9:36
 * @version: 1.0
 * @description:
 */
@Data
public class DeviceWriter {


    @ApiModelProperty(value = "名称")
    @ExcelProperty(index = 1)
    private String name;

    @ApiModelProperty(value = "地址")
    @ExcelProperty(index = 2)
    private String address;

    @ApiModelProperty(value = "经纬度")
    @ExcelProperty(index = 5)
    private String de;

    @ApiModelProperty(value = "华为摄像头唯一编码")
    @ExcelProperty(index = 6)
    private String streamId;

    @ApiModelProperty(value = "接口调用平台")
    @ExcelProperty(index = 9)
    private String  platform;


    @ApiModelProperty(value = "预警推送平台")
    @ExcelProperty(index = 10)
    private String warnPlatform;

    @ExcelProperty(index = 12)
    @ApiModelProperty(value = "区")
    private String area;

    @ExcelProperty(index = 13)
    @ApiModelProperty(value = "街道")
    private String street;

    @ExcelProperty(index = 14)
    @ApiModelProperty(value = "社区")
    private String social;

    @ExcelProperty(index = 15)
    @ApiModelProperty(value = "归属网格")
    private String grid;


    @ExcelProperty(index = 8)
    @ApiModelProperty(value = "算法类型")
    private String algorithm;

    @ExcelProperty(index = 19)
    @ApiModelProperty(value = "网格员账号")
    private String username;
}
