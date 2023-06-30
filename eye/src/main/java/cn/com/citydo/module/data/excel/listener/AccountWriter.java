package cn.com.citydo.module.data.excel.listener;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author blackcat
 * @create 2021/5/31 14:06
 * @version: 1.0
 * @description:
 */
@Data
public class AccountWriter {


    @ApiModelProperty(value = "姓名")
    @ExcelProperty(index = 0)
    private String username;

    @ApiModelProperty(value = "昵称")
    @ExcelProperty(index = 1)
    private String nikename;

    @ExcelProperty(index = 2)
    private String rolename;

    @ApiModelProperty(value = "所属网格编码")
    @ExcelProperty(index = 3)
    private String departmentCode;


}
