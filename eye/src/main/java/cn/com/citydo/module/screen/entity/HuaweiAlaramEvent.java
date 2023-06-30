package cn.com.citydo.module.screen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 华为预警事件
 *
 * @author
 * @since 2021-06-08
 */
@Data
public class HuaweiAlaramEvent {

    @TableId(value = "id")
    private String id;

}
