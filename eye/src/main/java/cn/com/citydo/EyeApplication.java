package cn.com.citydo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("cn.com.citydo.module.*.mapper")
public class EyeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EyeApplication.class, args);
    }

}
