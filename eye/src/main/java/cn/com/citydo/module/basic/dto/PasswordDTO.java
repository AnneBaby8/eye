package cn.com.citydo.module.basic.dto;

import lombok.Data;

/**
 * @Author blackcat
 * @create 2021/6/3 16:54
 * @version: 1.0
 * @description:
 */
@Data
public class PasswordDTO {

    private Long id;

    private String oldPassword;

    private String password;
}
