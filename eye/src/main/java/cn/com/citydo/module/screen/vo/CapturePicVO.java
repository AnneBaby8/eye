package cn.com.citydo.module.screen.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 视频流截图操作返回VO
 * </p>
 *
 * @author shaodl
 * @Date 2021/6/7 15:51
 * @Version 1.0
 */
@Data
public class CapturePicVO {

    @ApiModelProperty(value = "app视频截图展示图片地址")
    private String appCapturePicUrl;

    @ApiModelProperty(value = "web视频截图展示图片地址")
    private String capturePicUrl;

    @ApiModelProperty(value = "视频截图保存图片地址")
    private String image;

    @ApiModelProperty(value = "视频流截图时间")
    private String captureTime;

}
