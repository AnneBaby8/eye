package cn.com.citydo.module.screen.vo;

import lombok.Data;

/**
 * @Author blackcat
 * @create 2021/5/12 10:21
 * @version: 1.0
 * @description:
 */
@Data
public class SnapshotInfoVO {

    private String cameracode;

    private String snapTime;

    private Integer snapType;

    private Integer pictureID;

    private String pictureName;

    private Integer pictureSize;

    private String previewUrl;

    private String pictureUrl;

    private String reserve;
}
